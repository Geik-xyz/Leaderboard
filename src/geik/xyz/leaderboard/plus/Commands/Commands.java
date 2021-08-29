package geik.xyz.leaderboard.plus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import geik.xyz.leaderboard.plus.Main;
import geik.xyz.leaderboard.plus.Utils.Manager;
import geik.xyz.leaderboard.plus.Utils.onEnableShortcut;
import geik.xyz.leaderboard.plus.Utils.Gui.TopTenGui;

public class Commands implements CommandExecutor {
	
	/**
	 * @author Geik
	 * @param instance
	 * @since 1.0.0
	 * @apiNote Call instance constructor
	 */
	public Main instance;
	
	public Commands(Main plugin)
	{
		this.instance = plugin;
	}

	/**
	 * @author Geik
	 * @since 1.0.0
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase("leaderboard"))
		{
			if (sender instanceof Player)
			{
				
				Player player = (Player) sender;
				
				if (player.hasPermission("leaderboard.admin"))
				{
					
					if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
					{
						
						reloadCommand(sender);
						
					}
					
					else if (args.length > 1 && args[0].equalsIgnoreCase("reload") && args[1].equalsIgnoreCase("topten"))
					{
						
						restartTopten(sender);
						
					}
					
					else if (args.length > 0 && Main.configValues.keySet().contains(args[0]))
					{
						
						if (Main.instance.getConfig().getBoolean("TopTen") && Main.instance.getConfig().getBoolean("Setters." + args[0] + ".topTen"))
							TopTenGui.create(player, args[0]);
						
						else wrongCommand(sender);
						
					}
					
					/**
					 * @since 1.2.0
					 */
					else if (args.length == 3 && Main.configValues.keySet().contains(args[1]) && args[0].equalsIgnoreCase("create"))
					{
						
						if (Main.instance.getConfig().getBoolean("TopTen") 
								&& Main.instance.getConfig().getBoolean("Setters." + args[1] + ".topTen")
								&& Manager.isNumeric(args[2])
								&& Main.instance.getConfig().getBoolean("npcModifier"))
						{
							
							Manager.createToptenNPC(player.getLocation(), args[1], Integer.valueOf(args[2]), true);
							
						}
						
						else wrongCommand(sender);
						
					}
						
					else wrongCommand(sender);
	
				}
				else
				{
					/**
					 * @since 1.1.0
					 */
					if (args.length > 0 && Main.configValues.keySet().contains(args[0]))
					{
						
						if (Main.instance.getConfig().getBoolean("TopTen") && Main.instance.getConfig().getBoolean("Setters." + args[0] + ".topTen"))
							TopTenGui.create(player, args[0]);
						
						else wrongCommand(sender);
						
					}
					else player.sendMessage(Main.color(Main.instance.getConfig().getString("lang.noPerm")));
					
				}
				
			}
			
			/**
			 * @ConsoleZone
			 */
			else
			{
				
				if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
				{
					
					reloadCommand(sender);
					
				}
				else if (args.length > 1 && args[0].equalsIgnoreCase("reload") && args[1].equalsIgnoreCase("topten"))
				{
					
					restartTopten(sender);
					
				}
				/**
				 * @since 1.1.0
				 */
				else if (args.length > 1 && Main.configValues.keySet().contains(args[0]))
				{
					
					Player target = Bukkit.getPlayer(args[1]);
					
					if (target != null && target.isOnline())

						if (Main.instance.getConfig().getBoolean("TopTen") && Main.instance.getConfig().getBoolean("Setters." + args[0] + ".topTen"))
							TopTenGui.create(target, args[0]);
					
						else wrongCommand(sender);
					
					else sender.sendMessage(Main.color("&cBöyle bir oyuncu bulunamadı!"));
					
				}
				
				else wrongCommand(sender);
				
			}
		}
		return false;
	}
	
	/**
	 * @author Geik
	 * @param sender
	 * @since 1.0.0
	 */
	public void reloadCommand(CommandSender sender)
	{
		
		new onEnableShortcut(false);
		
		sender.sendMessage(Main.color(Main.instance.getConfig().getString("lang.reload")));
		
	}
	
	/**
	 * @author Geik
	 * @param sender
	 * @since 1.0.0
	 */
	public void restartTopten(CommandSender sender)
	{
		
		if (!Main.topTen.isEmpty()) Main.topTen.clear();
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, new Runnable()
		{
			
			public void run()
			{
				
				for (String Key : Main.instance.getConfig().getConfigurationSection("Setters").getKeys(false))
				{
					
					onEnableShortcut.setTopTen(Key);
					
				}
				
				onEnableShortcut.updateNPCs();
				
			}
			
		});
		
		sender.sendMessage(Main.color(Main.instance.getConfig().getString("lang.topTenReload")));
		
	}
	
	/**
	 * @author Geik
	 * @param sender
	 * @since 1.0.0
	 */
	public void wrongCommand(CommandSender sender)
	{
		
		sender.sendMessage(Main.color(Main.instance.getConfig().getString("lang.wrongCommand")));
		
	}
	
}
