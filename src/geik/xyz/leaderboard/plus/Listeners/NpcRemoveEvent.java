package geik.xyz.leaderboard.plus.Listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import geik.xyz.leaderboard.plus.Main;
import net.citizensnpcs.api.event.NPCRemoveEvent;

public class NpcRemoveEvent implements Listener {
	
	public Main plugin;
	
	public NpcRemoveEvent(Main plugin)
	{
		
		this.plugin = plugin;
		
	}
	
	/**
	 * @author Geik
	 * @since 1.2.0
	 * @apiNote Data cleaner when npc remove
	 * @param e
	 */
	@EventHandler
	public void onNpcRemove(NPCRemoveEvent e)
	{
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, new Runnable() {
			
			public void run()
			{
				
				int npcID = e.getNPC().getId();
				
				File dataFile = new File("plugins/" + Main.instance.getDescription().getName() + "/data.yml");
				
				FileConfiguration datas = YamlConfiguration.loadConfiguration(dataFile);
				
				for (String data : datas.getConfigurationSection("data").getKeys(false))
				{
					
					if (datas.getInt("data." + data + ".id") == npcID)
					{
						
						datas.set("data." + data, null);
						
						try {datas.save(dataFile);} catch (IOException e1) {}
						
					}
					
					else continue;
					
				}
				
			}
			
		});
		
	}

}
