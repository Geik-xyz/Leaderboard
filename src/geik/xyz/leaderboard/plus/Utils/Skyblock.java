package geik.xyz.leaderboard.plus.Utils;

import org.bukkit.entity.Player;
import org.eclipse.jdt.annotation.Nullable;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.songoda.skyblock.api.SkyBlockAPI;
import com.songoda.skyblock.api.island.Island;
import com.songoda.skyblock.playerdata.PlayerData;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import geik.xyz.leaderboard.plus.Main;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.GameModeAddon;

public class Skyblock {
	
	public Skyblock() {}
	
	public boolean isLeader(Player player)
	{
		
		try {
			
			if (Main.type.equals(SkyblockType.Askyblock))
			{
				
				if (ASkyBlockAPI.getInstance().getIslandAt(ASkyBlockAPI.getInstance().getIslandLocation(player.getUniqueId()))
						.getOwner().toString().equalsIgnoreCase(player.getUniqueId().toString()))
					return true;
				
				else
					return false;
				
			}
			
			else if (Main.type.equals(SkyblockType.Bentobox))
			{
				
				BentoBox plugin = BentoBox.getInstance();
				
				world.bentobox.bentobox.api.user.User user = plugin.getPlayers().getUser(player.getUniqueId());
				
		        GameModeAddon gameModeAddon = plugin.getAddonsManager().getGameModeAddons().get(0);
		        
		        if(gameModeAddon != null)
		        {
		        	
		        	world.bentobox.bentobox.database.objects.@Nullable Island island = plugin.getIslands().getIsland(gameModeAddon.getOverWorld(), user);
		        	
			        if (island == null)
			            return false;
			        
			        if (island.getOwner().toString().equalsIgnoreCase(player.getUniqueId().toString()))
			        	return true;
			        
			        else return false;
			        
		        }
		        
		        else return false;
				
			}
			
			else if (Main.type.equals(SkyblockType.Fabled))
			{
				
				Island island = null;
				
				if (SkyBlockAPI.getImplementation().getPlayerDataManager().hasPlayerData(player))
				{
					
					PlayerData playerData = SkyBlockAPI.getImplementation().getPlayerDataManager().getPlayerData(player);
					
					if (playerData.getOwner() != null)
					{
						
						island = SkyBlockAPI.getIslandManager().getIsland(player);
						
					}
					
					else return false;
					
				}
				
				else return false;
				
				if (island == null)
					return false;
				
				if (SkyBlockAPI.getIslandManager().getIsland(player).getOwnerUUID().toString().equalsIgnoreCase(player.getUniqueId().toString()))
					return true;
				
				else return false;
				
			}
			
			else if (Main.type.equals(SkyblockType.Iridium))
			{
				
				if (IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getOwner().getUuid().toString().equalsIgnoreCase(player.getUniqueId().toString()))
					return true;
				
				else return false;
				
			}
			
			else if (Main.type.equals(SkyblockType.Superior))
			{
				
				if (SuperiorSkyblockAPI.getPlayer(player).getIslandLeader().getUniqueId().toString().equals(player.getUniqueId().toString()))
					return true;
				
				else return false;
				
			}
			
			else
				return false;
			
		}
		
		catch (NullPointerException e1) { return false; }
		
	}

}
