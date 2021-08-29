package geik.xyz.leaderboard.plus.Utils;

import org.bukkit.entity.Player;

import geik.xyz.leaderboard.plus.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholders extends PlaceholderExpansion {
	
	public Main plugin;
    
	/**
	 * @apiNote Plugin identifier
	 */
    public String getIdentifier()
    {
        return "leaderboard";
    }

    /**
     * @apiNote Plugin Catcher
     */
    public String getPlugin()
    {
        return Main.instance.getDescription().getName();
    }

    /**
     * @author Geik
     */
    public String getAuthor()
    {
        return "Geik";
    }
    
    /**
     * @Version
     */
    public String getVersion()
    {
        return Main.instance.getDescription().getVersion();
    }
    
    /**
     * @Override
     * @author Geik
     * @apiNote Replace identifier to value
     * @apiNote %leaderboard_key_1%
     */
    @Override
    public String onPlaceholderRequest(Player p, String identifier)
    {
    	
    	if (Main.instance.getConfig().getBoolean("TopTen") 
    			&& Main.instance.getConfig().getConfigurationSection("Setters").getKeys(false).contains(identifier.split("_")[identifier.split("_").length-2]))
    	{
    		
        	int id = Integer.valueOf(identifier.split("_")[identifier.split("_").length-1])-1;
        	
        	String DATA_NAME = identifier.split("_")[identifier.split("_").length-2];
        	
        	String KEY = null;
        	
        	if (identifier.split("_").length > 2) KEY = identifier.split("_")[0];
        	
        	if (Main.topTen.get(DATA_NAME).size()-1 < id) return null;
        	
        	if (KEY == null)
        	{
        		
        		String MessageTemplate = Main.color( Main.instance.getConfig().getString("TopTen-Format")
                		.replace("{id}", String.valueOf(id+1))
                		.replace("{player}", Manager.getTopTenLine(DATA_NAME, id).getPlayerName())
                		.replace("{value}", String.valueOf(Manager.getTopTenLine(DATA_NAME, id).getValue())) );
        		
        		return MessageTemplate;
        		
        	}
        	
        	else
        	{

        		if (KEY.contains("name")) return Manager.getTopTenLine(DATA_NAME, id).getPlayerName();
        		
        		else return String.valueOf(Manager.getTopTenLine(DATA_NAME, id).getValue());
        		
        	}
        	
    	} else return null;
    	
    }
}

