package geik.xyz.leaderboard.plus.Utils.Gui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import geik.xyz.leaderboard.plus.Main;
import geik.xyz.leaderboard.plus.Utils.Manager;
import geik.xyz.leaderboard.plus.Utils.Cache.TopTen;

@SuppressWarnings("deprecation")
public class TopTenGui {
	
	/**
	 * @author Geik
	 * @param player
	 * @param menu
	 * @since 1.1.0
	 */
	public static void create(Player player, String menu)
	{
		
		Inventory gui = Bukkit.getServer().createInventory(player, 27, (Main.color( Main.instance.getConfig().getString("Setters." + menu + ".guiName"))));
		
		for (int line = 0; line <= 9; line++)
		{
			
			if (Main.topTen.get(menu).size()-1 >= line && Manager.getTopTenLine(menu, line) != null)
			{
				try {
					
					TopTen topTen = Manager.getTopTenLine(menu, line);
					
					int slot = getSlot(line);
					
					gui.setItem(slot, playerHead(topTen.getPlayerName(), menu, line+1, topTen.getValue() ));
					
				} catch (NullPointerException e1) {}
				
			} else continue;
			
		}
		
		player.openInventory(gui);
		
	}
	
	/**
	 * @author Geik
	 * @param line
	 * @return
	 * @since 1.1.0
	 */
	private static int getSlot(int line)
	{
		
		int slot = 4;
		
		if (line == 0) slot = 4;
		
		else if (line > 0 && line < 3)
		{
			
			if (line == 1) slot = 12;
			
			else slot = 14;
			
		}
		else slot = line+16;
		
		return slot;
		
	}
	
	/**
	 * @author Geik
	 * @param playerName
	 * @param menu
	 * @param line
	 * @param value
	 * @return
	 * @since 1.1.0
	 */
	private static ItemStack playerHead(String playerName, String menu, int line, BigDecimal value)
	{
		
		ItemStack head = null;
		
		String nms = Manager.getNMSVersion();
		
		if (nms.contains("1_16") || nms.contains("1_15") || nms.contains("1_14") || nms.contains("1_13"))
			head = new ItemStack(Material.PLAYER_HEAD, 1);
			
		else
			head = new ItemStack(Material.getMaterial("SKULL_ITEM"), 1, (short) SkullType.PLAYER.ordinal());
		
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		
		meta.setOwner(playerName);
		
		meta.setDisplayName(Main.color(Main.instance.getConfig().getString("Setters." + menu + ".topTenName")
				.replace("{line}", String.valueOf(line))
				.replace("{player}", playerName) 
				.replace("{value}", String.valueOf(value))  ));
		
		List<String> lore = new ArrayList<String>();
		for (String loreString : Main.instance.getConfig().getStringList("Setters." + menu + ".topTenLore"))
			
			lore.add(Main.color(loreString.replace("{line}", String.valueOf(line)).replace("{player}", playerName).replace("{value}", String.valueOf(value)) ));
		
		meta.setLore(lore);
		
		head.setItemMeta(meta);
			
		return head;
		
	}

}
