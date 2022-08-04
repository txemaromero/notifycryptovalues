package cryptovalues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CryptocurrenciesCall {
	
	private static Properties loadCryptocurrenciesFile(String pCryptocurrenciesFile)
	{
		Properties properties = new Properties();
		
		try
		{
		   	properties.load(new FileInputStream(new File(pCryptocurrenciesFile)));
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
		
	   	return properties;
	}
	
	public static double getCryptocurrencies(String pCryptocurrenciesFile, String pChange, String pApiCall) throws CryptocurrenciesException
	{		
		Properties properties= new Properties();
		properties = loadCryptocurrenciesFile(pCryptocurrenciesFile);
		
		StringBuilder urlToGetTokens = new StringBuilder();
		urlToGetTokens = new StringBuilder(pApiCall);
		HashMap<String, String> hMapQuantities = new HashMap<String, String>();
		String formatTokenKey;
		String name;
		String quantity;
		int numToken=1;
		double balance=-1.00;
        
        for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();)
        {
            final String tokenKey = (String)e.nextElement();
            if (tokenKey.contains("name"))
            {
            	formatTokenKey="cryptocurrency"+Integer.toString(numToken);
            	name = properties.get(formatTokenKey+"name").toString().toUpperCase();
            	quantity = properties.get(formatTokenKey+"quantity").toString();
            	       	
            	hMapQuantities.put(name, quantity);
            	urlToGetTokens.append(name);
    		    urlToGetTokens.append("%2C");
    		    numToken++;
            }	
        }
        
        urlToGetTokens.setLength(urlToGetTokens.length() - 3);
        
		urlToGetTokens.append("&vs_currencies=");
		urlToGetTokens.append(pChange);
			
		try
		{
		   	InputStream is = new URL(urlToGetTokens.toString()).openStream();
		   	String contents = new String(is.readAllBytes());
			
		  	//Using the JSON simple library parse the string into a JSON object
		   	JSONParser parse = new JSONParser();
		   	JSONObject dataObj = (JSONObject) parse.parse(contents);
		   	
		   	balance = Values.extractValuesFromJSON(dataObj, null, hMapQuantities);
		   	
		   	if (Double.compare(balance, -1.00) == 0)
		   	{
		   		throw new CryptocurrenciesException("ERROR_BALANCE");
		   	}
		}
		catch (CryptocurrenciesException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new CryptocurrenciesException("NO_CONNECTION");
		}
		
		return balance;
	}

}
