package geik.xyz.leaderboard.plus.Database;


import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import geik.xyz.leaderboard.plus.Main;
import net.md_5.bungee.api.ChatColor;

public class ConnectionPool {

	/**
	 * @apiNote dataSource
	 * @since 1.0.0
	 */
    private static HikariDataSource dataSource;

    /**
     * @author Geik
     * @param hostname
     * @param port
     * @param database
     * @param username
     * @param password
     * 
     * @since 1.0.0
     */
    public static void init(String hostname, String port, String database, String username, String password)
    {
        HikariConfig config = new HikariConfig();
        
        config.setDriverClassName("com.mysql.jdbc.Driver");
        
        config.setJdbcUrl( "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false" );
        
        config.setUsername(username);
        
        config.setPassword(password);
        
        config.setMinimumIdle(Main.instance.getConfig().getInt("Database-Connection-Settings.minimumConnections"));
        
        config.setMaximumPoolSize(Main.instance.getConfig().getInt("Database-Connection-Settings.maximumConnections"));
        
        config.setConnectionTimeout(Main.instance.getConfig().getInt("Database-Connection-Settings.connectionTimeout"));
        
        config.setMaxLifetime(Main.instance.getConfig().getInt("Database-Connection-Settings.maxLifetime")*1000);
        
        
        config.addDataSourceProperty("useUnicode", Main.instance.getConfig().getString("Database-Connection-Settings.useUnicode"));
        
        config.addDataSourceProperty("characterEncoding", Main.instance.getConfig().getString("Database-Connection-Settings.characterEncoding"));
        
        config.addDataSourceProperty("cachePrepStmts", Main.instance.getConfig().getString("Database-Connection-Settings.cachePrepStmts"));
        
        config.addDataSourceProperty("prepStmtCacheSize", Main.instance.getConfig().getString("Database-Connection-Settings.prepStmtCacheSize"));
        
        config.addDataSourceProperty("prepStmtCacheSqlLimit", Main.instance.getConfig().getString("Database-Connection-Settings.prepStmtCacheSqlLimit"));
        
        config.setPoolName(ChatColor.WHITE + "Leader" + ChatColor.AQUA + "Board - Database Query");

        dataSource = new HikariDataSource(config);
    }

    /**
     * @author Geik
     * @since 1.0.0
     * 
     * @return
     */
    public static Connection getConnection()
    {
    	try
    	{
    		return dataSource.getConnection();
    	}
    	catch (SQLException e)
    	{
    		return null;
    	}
    }

    /**
     * @author Geik
     * @since 1.0.0
     */
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed())
        {
            dataSource.close();
        }
    }
}