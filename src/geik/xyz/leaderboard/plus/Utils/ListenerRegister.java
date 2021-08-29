package geik.xyz.leaderboard.plus.Utils;

import org.bukkit.Bukkit;

import geik.xyz.leaderboard.plus.Main;
import geik.xyz.leaderboard.plus.Listeners.JoinEvent;
import geik.xyz.leaderboard.plus.Listeners.NpcRemoveEvent;
import geik.xyz.leaderboard.plus.Listeners.NpcRightClickEvent;
import geik.xyz.leaderboard.plus.Listeners.QuitEvent;
import geik.xyz.leaderboard.plus.Listeners.TopTenGuiEvent;

public class ListenerRegister {
	
	public QuitEvent quitEvent;
	
	public JoinEvent joinEvent;
	
	public TopTenGuiEvent topTenGuiEvent;
	
	public NpcRemoveEvent npcRemovEvent;
	
	public NpcRightClickEvent npcRightClickEvent;
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @apiNote Listener Register
	 */
	public ListenerRegister()
	{
		
		// QuitEvent register
		this.quitEvent = new QuitEvent(Main.instance);
		Bukkit.getPluginManager().registerEvents(this.quitEvent, Main.instance);
		
		// JoinEvent register
		this.joinEvent = new JoinEvent(Main.instance);
		Bukkit.getPluginManager().registerEvents(this.joinEvent, Main.instance);
		
		// TopTenGuiEvent register
		this.topTenGuiEvent = new TopTenGuiEvent(Main.instance);
		Bukkit.getPluginManager().registerEvents(this.topTenGuiEvent, Main.instance);
		
		if (Bukkit.getPluginManager().isPluginEnabled("Citizens"))
		{
			
			// NPCRemoveEvent register
			this.npcRemovEvent = new NpcRemoveEvent(Main.instance);
			Bukkit.getPluginManager().registerEvents(this.npcRemovEvent, Main.instance);
			
			// NPCRemoveEvent register
			this.npcRightClickEvent = new NpcRightClickEvent(Main.instance);
			Bukkit.getPluginManager().registerEvents(this.npcRightClickEvent, Main.instance);
			
		}
		
	}

}
