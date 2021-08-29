package geik.xyz.leaderboard.plus.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import geik.xyz.leaderboard.plus.Main;
import geik.xyz.leaderboard.plus.Database.DatabaseQueries;

public class JoinEvent implements Listener {

	/**
	 * @author Geik
	 * @param instance
	 * @since 1.0.0
	 * @apiNote Call instance constructor
	 */
	public Main instance;

	public JoinEvent(Main instance)
	{
		this.instance = instance;
	}
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @JoinEvent
	 * @param e
	 */
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e)
	{
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () ->
		{
			try
			{
				DatabaseQueries.createPlayer(e.getPlayer().getName());	
			}
			catch(NullPointerException e1) {}
		});
		
	}

}
