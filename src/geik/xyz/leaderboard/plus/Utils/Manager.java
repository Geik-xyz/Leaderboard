package geik.xyz.leaderboard.plus.Utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.json.JSONException;
import org.json.JSONObject;

import geik.xyz.leaderboard.plus.Main;
import geik.xyz.leaderboard.plus.Utils.Cache.TopTen;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import net.citizensnpcs.trait.Gravity;
import net.citizensnpcs.trait.LookClose;

public class Manager {
	
	/**
	 * @author Geik
	 * @param ROW
	 * @param line
	 * @return
	 * @since 1.0.0
	 */
	public static TopTen getTopTenLine(String ROW, int line)
	{
		
		return Main.topTen.get(ROW).get(line);
		
	}
	
	/**
	 * @author Geik
	 * @return
	 * @since 1.1.0
	 */
	public static String getNMSVersion()
	{
		
        String v = Bukkit.getServer().getClass().getPackage().getName();
        
        return v.substring(v.lastIndexOf('.') + 1);
        
    }
	
	/**
	 * @author Geik
	 * @since 1.2.0
	 * @param loc
	 * @param uuid
	 * @param name
	 * @param skin
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int createToptenNPC(Location loc, String topTenName, int line, boolean firstSpawn)
	{
		
		NPC npc = null;
		
		TopTen value = Main.topTen.get(topTenName).get(line-1);
		
		String name = Main.instance.getConfig().getString("Setters." + topTenName + ".topTenName")
				.replace("{line}", String.valueOf(line))
				.replace("{player}", value.getPlayerName())
				.replace("{value}", String.valueOf(value.getValue()));
		
		npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
		
		((NPC) npc).data().set(NPC.PLAYER_SKIN_UUID_METADATA, value.getPlayerName());
		
		((NPC) npc).data().set(NPC.PLAYER_SKIN_USE_LATEST, false);
		
        ((NPC) npc).spawn(loc);
        
        try
        {
        	
        	SkinnableEntity entity = (SkinnableEntity) npc.getEntity();
        	
        	entity.setSkinName(value.getPlayerName());
        	
        }
        
        finally {}
        
        npc.getTrait(Gravity.class).gravitate(true);
        
        npc.getTrait(LookClose.class).lookClose(false);
        
        if (firstSpawn)
        {
        	
        	File dataFile = new File("plugins/" + Main.instance.getDescription().getName() + "/data.yml");
    		
    		FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
    		
    		String location = getStringFromLocation(loc);
        	
        	data.set("data." + topTenName + line + npc.getId() + ".id", npc.getId());
        	
        	data.set("data." + topTenName + line + npc.getId() + ".data", topTenName);
        	
        	data.set("data." + topTenName + line + npc.getId() + ".line", line);
        	
        	data.set("data." + topTenName + line + npc.getId() + ".location", location);
        	
        	try
        	{
        		
				data.save(dataFile);
				
			} catch (IOException e) {}
        	
    		
        }
		
        return npc.getId();
    
    }
	
	/**
	 * @author Geik
	 * @since 1.2.0
	 * @param strNum
	 * @return
	 */
	public static boolean isNumeric(String strNum)
	{
		
	    try
	    {
	    	
	        Double.parseDouble(strNum);
	        
	    }
	    catch (NumberFormatException nfe)
	    {
	    	
	        return false;
	        
	    }
	    
	    return true;
	    
	}
	
	/**
	 * @author Geik
	 * @since 1.2.0
	 * @param fileName
	 * @apiNote File Injector
	 */
	public static void FileChecker(String fileName)
	{
		
		File c = new File("plugins/" + Main.instance.getDescription().getName() + "/" + fileName + ".yml");
		
		if (!c.exists())
		{
			
			Main.instance.saveResource(fileName + ".yml", false);
			
		}
		
	}
	
	/**
	 * @author Geik
	 * @since 1.2.0
	 * @param path
	 * @return
	 */
	public static String getData(String path)
	{
		
		File langFile = new File("plugins/" + Main.instance.getDescription().getName() + "/data.yml");
		
		FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);
		
		return Main.color(lang.getString("data." + path));
		
	}
	
	/**
	 * @author Geik
	 * @since 1.2.0
	 * @param path
	 * @return
	 */
	public static String getStringFromLocation(Location loc)
	{
		
	    return loc.getWorld().getName() + "-" + loc.getX() + "-" + loc.getY() + "-" + loc.getZ() + "-" + loc.getYaw() + "-" + loc.getPitch() ;
	    
	}
	
	/**
	 * @author Geik
	 * @since 1.2.0
	 * @param s
	 * @return
	 */
	public static Location getLocationFromString(String s)
	{
		
		  if (s == null || s.trim() == "")
		  {
			  
			  return null;
			  
		  }
		  
		  final String[] parts = s.split("-");
		  
		  if (parts.length == 6)
		  {
			  
			  World w = Bukkit.getServer().getWorld(parts[0]);
			  
			  double x = Double.parseDouble(parts[1]);
			  
			  double y = Double.parseDouble(parts[2]);
			  
			  double z = Double.parseDouble(parts[3]);
			  
			  float yaw = Float.parseFloat(parts[4]);
			  
			  float pitch = Float.parseFloat(parts[5]);
			  
			  return new Location(w, x, y, z, yaw, pitch);
			  
		  }
		  return null;
		  
	}
	

}
