package geik.xyz.leaderboard.plus.Utils.Cache;

import java.math.BigDecimal;

public class TopTen {
	
	String RowName;
	
	String PlayerName;
	
	BigDecimal Value;
	
	public TopTen(String RowName, String PlayerName, BigDecimal Value)
	{
		
		this.RowName = RowName;
		
		this.PlayerName = PlayerName;
		
		this.Value = Value;
		
	}
	
	public String getRowName()
	{
		return RowName;
	}

	public String getPlayerName()
	{
		return PlayerName;
	}
	
	public BigDecimal getValue()
	{
		return Value;
	}
}
