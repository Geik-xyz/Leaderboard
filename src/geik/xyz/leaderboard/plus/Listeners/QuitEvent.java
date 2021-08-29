package geik.xyz.leaderboard.plus.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import geik.xyz.leaderboard.plus.Main;
import geik.xyz.leaderboard.plus.Database.DatabaseQueries;
import geik.xyz.leaderboard.plus.Utils.Skyblock;
import geik.xyz.leaderboard.plus.Utils.Cache.TableValues;
import me.clip.placeholderapi.PlaceholderAPI;

public class QuitEvent implements Listener {
	
	/**
	 * @author Geik
	 * @param instance
	 * @since 1.0.0
	 * @apiNote Call instance constructor
	 */
	public Main instance;

	public QuitEvent(Main instance)
	{
		this.instance = instance;
	}
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @QuitEvent
	 * @param e
	 */
	@EventHandler
	public void onLeaveEvent(PlayerQuitEvent e)
	{
			
		try
		{
			
			Player player = e.getPlayer();
			
			List<TableValues> TABLE = new ArrayList<TableValues>(Main.configValues.values());
			
			for (TableValues TABLE_VALUES : TABLE)
			{
				String TABLE_NAME = TABLE_VALUES.RowName;
				
				String toConvert = TABLE_VALUES.getValuePlaceholder();
				
				boolean isSkyblock = false;
				if (toConvert.contains("askyblock")
						|| toConvert.contains("island_level")
						|| toConvert.contains("superior")
						|| toConvert.contains("fabledskyblock")
						|| toConvert.contains("iridiumskyblock"))
					isSkyblock = true;
				
				String Value = PlaceholderAPI.setPlaceholders(player, TABLE_VALUES.getValuePlaceholder());
				
				if (isSkyblock
						&& !new Skyblock().isLeader(player))
					Value = "0";
				
				DatabaseQueries.updateValue(e.getPlayer().getName(), TABLE_NAME, Value);
				
			}
			
		}
		
		catch (NullPointerException e1) {}
		
	}

}
