package geik.xyz.leaderboard.plus.Listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import geik.xyz.leaderboard.plus.Main;
import geik.xyz.leaderboard.plus.Utils.Gui.TopTenGui;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class NpcRightClickEvent implements Listener {
	
	public Main plugin;
	
	public NpcRightClickEvent(Main plugin)
	{
		
		this.plugin = plugin;
		
	}
	
	/**
	 * @author Geik
	 * @since 1.2.0
	 * @apiNote Topten Gui Opener
	 * @param e
	 */
	@EventHandler
	public void onRightClickToNpcEvent(NPCRightClickEvent e)
	{
		try
		{
			
			int npcID = e.getNPC().getId();
			
			File dataFile = new File("plugins/" + Main.instance.getDescription().getName() + "/data.yml");
			
			FileConfiguration datas = YamlConfiguration.loadConfiguration(dataFile);
			
			Bukkit.getScheduler().runTaskAsynchronously(Main.instance, new Runnable() {
				
				public void run()
				{
					
					for (String data : datas.getConfigurationSection("data").getKeys(false))
					{
						
						if (datas.getInt("data." + data + ".id") == npcID)
						{
							
							Bukkit.getScheduler().runTask(Main.instance, new Runnable() {
								
								public void run()
								{
									
									String topTenName = datas.getString("data." + data + ".data");
									
									if (Main.instance.getConfig().getBoolean("TopTen") && Main.instance.getConfig().getBoolean("Setters." + topTenName + ".topTen"))
										TopTenGui.create(e.getClicker(), topTenName);
									
								}
							});
							
						}
						
						else continue;
						
					}
					
				}
				
			});
			
		}
		
		catch (NullPointerException e1) {}
		
	}

}
