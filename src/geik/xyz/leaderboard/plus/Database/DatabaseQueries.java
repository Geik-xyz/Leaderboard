package geik.xyz.leaderboard.plus.Database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import geik.xyz.leaderboard.plus.Main;
import geik.xyz.leaderboard.plus.Utils.Cache.TopTen;

public class DatabaseQueries {
	
	/**
	 * @apiNote Default Table Name Catcher
	 */
	public static String TABLE_NAME = Main.instance.getConfig().getString("TableName");
	
	/**
	 * @author Geik
	 * @apiNote Default MySQL Table Creator
	 * @since 1.0.0
	 */
	public static void createTable()
	{
	    try (Connection connection = ConnectionPool.getConnection();
	         Statement statement = connection.createStatement())
	    {
	        statement.addBatch("CREATE TABLE IF NOT EXISTS `" + TABLE_NAME + "`\n" +
	                "(\n" +
	                " `username`         varchar(20) NOT NULL UNIQUE\n" +
	                ");");
	        statement.executeBatch();
	    }
	    
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @param column
	 * @return
	 */
	private static boolean hasColumn(String column)
	{
		
        try (Connection con = ConnectionPool.getConnection())
        {
        	
        	DatabaseMetaData md = con.getMetaData();
        	
        	ResultSet rs = md.getColumns(null, null, TABLE_NAME, column);
        	
        	if (rs.next()) return true;
        	
        	else return false;
        	
        }
        
        catch (SQLException e) 
        {
        	return false;
        }
        
	}
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @param Row
	 */
	public static void registerProduct(String Row)
	{
		if (!hasColumn(Row)) {
			
			String SQL_QUERY = "ALTER TABLE " + TABLE_NAME + " ADD " + Row.toLowerCase() + " BIGINT DEFAULT 0";
			
			try (Connection connection = ConnectionPool.getConnection();
		             Statement statement = connection.createStatement())
			{
				
	        	statement.addBatch(SQL_QUERY);
	        	
	        	statement.executeBatch();
	        	
	        } 
			
			catch (SQLException e) {}
			
		} else return;
	}
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @param player
	 * @param Query
	 * @param updatedValue
	 * @return
	 */
	public static void updateValue(String player, String Query, String UpdatedValue)
	{
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () ->
		{
				
			String SQL_QUERY = "UPDATE " + TABLE_NAME + " SET " + Query + " = ? WHERE username = ?";
			
	        try (Connection con = ConnectionPool.getConnection())
	        {
	            PreparedStatement pst = con.prepareStatement(SQL_QUERY);
	
	            pst.setString(1, UpdatedValue);
	            
	            pst.setString(2, player);
	            
	            pst.executeUpdate();
	        	
	            pst.close();
	        }
	        
	        catch (SQLException e) {}
				
		});

	}
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @apiNote Create player to MySQL
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public static void createPlayer(String name)
	{
        String SQL_QUERY = "INSERT IGNORE INTO " + TABLE_NAME + " (username) VALUES (?)";
        try (Connection con = ConnectionPool.getConnection())
        {
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            pst.setString(1, name);

            pst.executeUpdate();

            pst.close();
        }
        
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        
    }
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @param column
	 * @return
	 */
	public static void getTopTen(String KEY)
	{
		
		String SQL_QUERY = "SELECT username, " + KEY + " FROM " + TABLE_NAME + " ORDER BY " + KEY + " DESC LIMIT 10";
		
		List<TopTen> TOP = new ArrayList<TopTen>();
		
        try (Connection con = ConnectionPool.getConnection())
        {
        	
            PreparedStatement pst = con.prepareStatement(SQL_QUERY);

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next())
            {
            	
            	String USER_NAME = resultSet.getString("username");
            	
            	String ROW_NAME = KEY;
            	
            	BigDecimal VALUE = resultSet.getBigDecimal(KEY);
            	
            	TopTen TOPTEN_DATA = new TopTen(ROW_NAME, USER_NAME, VALUE);
            	
                TOP.add(TOPTEN_DATA);
                
            }
            
            resultSet.close();
            
            pst.close();
            
            if (TOP.isEmpty()) return;
            
            else Main.topTen.put(KEY, TOP);
        	
        }
        
        catch (SQLException e) 
        {
        	return;
        }

	}
	
	

}
