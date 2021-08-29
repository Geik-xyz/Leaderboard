package geik.xyz.leaderboard.plus.Utils.Cache;

public class TableValues {
	
	public String RowName;
	
	public String ValuePlaceholder;
	
	/**
	 * @author Geik
	 * @since 1.0.0
	 * @param Key
	 * @param RowName
	 * @param ValuePlaceholder
	 */
	public TableValues(String RowName, String ValuePlaceholder) 
	{
		
		this.RowName = RowName;
		
		this.ValuePlaceholder = ValuePlaceholder;
		
	}
	
	/**
	 * @apiNote RowName Getter
	 * @return
	 */
	public String getRowName() 
	{
		return RowName;
	}
	
	/**
	 * @apiNote ValuePlaceholder Getter
	 * @return
	 */
	public String getValuePlaceholder()
	{
		return ValuePlaceholder;
	}

}
