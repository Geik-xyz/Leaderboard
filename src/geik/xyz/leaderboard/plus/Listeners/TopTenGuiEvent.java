package geik.xyz.leaderboard.plus.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import geik.xyz.leaderboard.plus.Main;

public class TopTenGuiEvent implements Listener
{
	
	/**
	 * @author Geik
	 * @param instance
	 * @since 1.1.0
	 * @apiNote Call instance constructor
	 */
	public Main instance;

	public TopTenGuiEvent(Main instance)
	{
		this.instance = instance;
	}
	
	/**
	 * @author Geik
	 * @param e
	 * @since 1.1.0
	 */
	@EventHandler
	public void onGuiClickEvent(InventoryClickEvent e)
	{
		
		if (Main.guiNames.contains(e.getView().getTitle()))
		{
			
			if (e.getClickedInventory() != null && e.getClickedInventory().getType().equals(InventoryType.CHEST))
			{
				
				e.setCancelled(true);
				
			}
			
		}
		
	}

}
