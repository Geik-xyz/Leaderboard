package geik.xyz.leaderboard.plus.Utils;

import org.bukkit.scheduler.BukkitRunnable;

import geik.xyz.leaderboard.plus.Main;

public class AutoToptenUpdater {
	
	public static BukkitRunnable topTenUpdater;
	
	public static void startToptenAsyncUpdater()
	{
		
		if (topTenUpdater != null)
		{
			 
			topTenUpdater.cancel();
			 
			topTenUpdater = null;
			 
	    }
		topTenUpdater = new BukkitRunnable()
		{
	        @Override
	        
	        public synchronized void cancel() throws IllegalStateException
	        {
	        	
	            super.cancel();
	            
	        }
	        
	        public void run()
	        {
	        	
        		if (!Main.topTen.isEmpty()) Main.topTen.clear();
        		
				for (String Key : Main.instance.getConfig().getConfigurationSection("Setters").getKeys(false))
				{
					
					onEnableShortcut.setTopTen(Key);
					
				}
				
				onEnableShortcut.updateNPCs();
				
	        }
		};
		
		topTenUpdater.runTaskTimerAsynchronously(Main.instance, Main.instance.getConfig().getInt("topTenUpdater")*20*60, Main.instance.getConfig().getInt("topTenUpdater")*20*60);}

}
