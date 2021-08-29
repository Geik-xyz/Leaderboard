package geik.xyz.leaderboard.plus.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import geik.xyz.leaderboard.plus.Main;
import geik.xyz.leaderboard.plus.Commands.Commands;
import geik.xyz.leaderboard.plus.Database.ConnectionPool;
import geik.xyz.leaderboard.plus.Database.DatabaseQueries;
import geik.xyz.leaderboard.plus.Utils.Cache.TableValues;
import geik.xyz.leaderboard.plus.Utils.Cache.TopTen;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;

public class onEnableShortcut {
	
	/**
	 * @author Geik
	 * @apiNote Do everything about caching and function for onEnable
	 */
	public onEnableShortcut(boolean firstOpen) 
	{
		// Remove Hooks
		ConnectionPool.closePool();
		
		if (!Main.configValues.isEmpty())
			Main.configValues.clear();
		
		if (!Main.topTen.isEmpty())
			Main.topTen.clear();
		
		if (firstOpen)
			Main.instance.getCommand("leaderboard").setExecutor(new Commands(Main.instance));
		
		if (!firstOpen && Main.instance.getConfig().getInt("topTenUpdater") > 0)
			AutoToptenUpdater.topTenUpdater.cancel();
		
		// MySQL Values
		if (firstOpen)
		{
			String MYSQL_HOST = Main.instance.getConfig().getString("Database.Host");
			String PORT = Main.instance.getConfig().getString("Database.Port");
			String DATABASE_NAME = Main.instance.getConfig().getString("Database.DBName");
			String USER_NAME = Main.instance.getConfig().getString("Database.Username");
			String PASSWORD = Main.instance.getConfig().getString("Database.Password");
			
			ConnectionPool.init(MYSQL_HOST, PORT, DATABASE_NAME, USER_NAME, PASSWORD); // initiate MySQL connection
			
			DatabaseQueries.createTable(); // Create table if not exists	
		}
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () ->
		{
				
				for (String Key : Main.instance.getConfig().getConfigurationSection("Setters").getKeys(false))
				{
					
					String ValuePlaceholder = Main.instance.getConfig().getString("Setters." + Key + ".value");
					
					TableValues Value = new TableValues(Key, ValuePlaceholder);
					
					Main.configValues.put(Key, Value);
						
					DatabaseQueries.registerProduct(Key);
							
					setTopTen(Key);
					
					if (Main.instance.getConfig().isSet("Setters." + Key + ".guiName"))
						Main.guiNames.add(Main.color(Main.instance.getConfig().getString("Setters." + Key + ".guiName")));
					
				}
				
				updateNPCs();
			
		});
		
		getWorkingPlugin();
		
		// Placeholder register system
		if (firstOpen)
			if( Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
				new Placeholders().register();
		
		if (Main.instance.getConfig().getInt("topTenUpdater") > 0)
			AutoToptenUpdater.startToptenAsyncUpdater();
		
	}
	
	/**
	 * @author Geik
	 * @param Key
	 * @param RowName
	 * @since 1.0.0
	 */
	public static void setTopTen(String Key)
	{
		
		boolean TOPTEN_BASE = Main.instance.getConfig().getBoolean("TopTen");
		
		boolean TOPTEN_ROW = false;
				
		if (Main.instance.getConfig().isSet("Setters." + Key + ".topTen"))
			TOPTEN_ROW = Main.instance.getConfig().getBoolean("Setters." + Key + ".topTen");
				
		if (TOPTEN_BASE && TOPTEN_ROW)
		{
			
			try 
			{
			
				DatabaseQueries.getTopTen(Key);
				
			}
			
			catch (NullPointerException e1) {}
					
		}
		
	}
	
	/**
	 * @author Geik
	 * @since 1.2.0
	 */
	@SuppressWarnings("deprecation")
	public static void updateNPCs()
	{
		
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.instance, new Runnable() {
			
			public void run()
			{
				
				if (Main.instance.getConfig().getBoolean("npcModifier"))
				{
					
					File langFile = new File("plugins/" + Main.instance.getDescription().getName() + "/data.yml");
					
					FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);
					
					if (lang.isSet("data")) {
						
						for (String hashName : lang.getConfigurationSection("data").getKeys(false))
						{
							
							int npcID = Integer.valueOf(Manager.getData(hashName + ".id"));
							
							int line = Integer.valueOf(Manager.getData(hashName + ".line"));
							
							String data = Manager.getData(hashName + ".data");
							
							TopTen topTen = Main.topTen.get(data).get(line-1);
							
							NPC npc = CitizensAPI.getNPCRegistry().getById(npcID);
							
							String name = Main.instance.getConfig().getString("Setters." + data + ".topTenName")
									.replace("{line}", String.valueOf(line))
									.replace("{player}", topTen.getPlayerName())
									.replace("{value}", String.valueOf(topTen.getValue()));
							
							try
							{
								
								if (npc.isSpawned())
								{
									
									SkinnableEntity entity = (SkinnableEntity) npc.getEntity();
						        	
						        	entity.setSkinName(topTen.getPlayerName());
						        	
						        	npc.setName(name);
									
								}
								
							}
							
							catch(NullPointerException e)
							{

								Bukkit.getScheduler().runTask(Main.instance, new Runnable()
								{
									
									public void run()
									{
										
										String location = Manager.getData(hashName + ".location");
										
										int newID = Manager.createToptenNPC(Manager.getLocationFromString(location), data, line, false);
										
										lang.set("data." + data + line + npcID + ".id", newID);
										
										try {lang.save(langFile);} catch (IOException e) {}
										
									}
									
								});
								
							}
							
						}
						
					}
					
				}
				
			}
			
			
		}, 100L);
		
	}
	
	public static void getWorkingPlugin()
	{
		
		Bukkit.getScheduler().runTask(Main.instance, () ->
		{
				
				if (Bukkit.getPluginManager().getPlugin("ASkyBlock") != null) Main.type = SkyblockType.Askyblock;
				else if (Bukkit.getPluginManager().getPlugin("FabledSkyBlock") != null) Main.type = SkyblockType.Fabled;
				else if (Bukkit.getPluginManager().getPlugin("SuperiorSkyblock2") != null) Main.type = SkyblockType.Superior;
				else if (Bukkit.getPluginManager().getPlugin("IridiumSkyblock") != null) Main.type = SkyblockType.Iridium;
				else if (Bukkit.getPluginManager().getPlugin("BentoBox") != null) Main.type = SkyblockType.Bentobox;
				else Main.type = SkyblockType.NULL;
			
		});
		
	}

}
