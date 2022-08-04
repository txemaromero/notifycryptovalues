package cryptovalues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Change {
	
	public static final String CHANGE_LOWER= "eur";
	public static final String CHANGE_UPPER= "EUR";
	
	public static double getChange(String pChangeFile)
	{
		Properties properties = new Properties();
		
		try
		{
		   	properties.load(new FileInputStream(new File(pChangeFile)));
		   	if (properties.isEmpty())
		   	{
		   		throw new CryptocurrenciesException("EMPTY_FILE");
		   	}
		     
		}
		catch (FileNotFoundException e)
		{
		  	e.printStackTrace();
		}
		catch (CryptocurrenciesException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
		  	e.printStackTrace();
		}
		
		return Double.parseDouble(properties.get("changevalue").toString());
	}	
	
}
