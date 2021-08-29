package geik.xyz.leaderboard.plus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;
import com.zaxxer.hikari.HikariDataSource;

import geik.xyz.leaderboard.plus.Utils.ListenerRegister;
import geik.xyz.leaderboard.plus.Utils.Manager;
import geik.xyz.leaderboard.plus.Utils.SkyblockType;
import geik.xyz.leaderboard.plus.Utils.onEnableShortcut;
import geik.xyz.leaderboard.plus.Utils.Cache.TableValues;
import geik.xyz.leaderboard.plus.Utils.Cache.TopTen;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	/**
	 * @apiNote Call instance
	 */
	public static Main instance;
	
	/**
	 * @apiNote hikariDataSource
	 */
	private HikariDataSource hikari;
	
	/**
	 * @author Geik
	 * @apiNote Contains All Config Values
	 */
	public static HashMap<String, TableValues> configValues = new HashMap<String, TableValues>();
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @apiNote topTenValue
	 */
	public static HashMap<String, List<TopTen>> topTen = new HashMap<String, List<TopTen>>();
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @apiNote GuiNames for Listener
	 */
	public static List<String> guiNames = new ArrayList<String>();
	
	// Skyblock Calculator
	public static SkyblockType type;
	
	
	public void onEnable()
	{
		
		instance = this;
		
		saveDefaultConfig();
		
		Manager.FileChecker("data");
		
		new ListenerRegister();
		
		MetricLoader();
		
		new onEnableShortcut(true);
		
	}
	
	/**
	 * @author Geik
	 * @param text
	 * @return
	 * @since 1.0.0
	 */
	public static String color(String text) {return ChatColor.translateAlternateColorCodes('&', text);}
	
	/**
	 * @author Geik
	 * @return
	 * @since 1.0.0
	 */
	public HikariDataSource getHikari() { return hikari; }
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 */
	public void MetricLoader() {new Metrics(this, 10517);}
	

}
