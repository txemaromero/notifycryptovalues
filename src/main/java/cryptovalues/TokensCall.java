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

public class TokensCall {
	
	private static Properties loadTokensFile(String pTokensFile)
	{
		Properties properties = new Properties();
		
		try
		{
		   	properties.load(new FileInputStream(new File(pTokensFile)));
		   	if (properties.isEmpty())
		   	{
		   		throw new TokensException("EMPTY_FILE");
		   	}
		     
		}
		catch (FileNotFoundException e)
		{
		  	e.printStackTrace();
		}
		catch (TokensException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
		  	e.printStackTrace();
		}
		
	   	return properties;
	}
	
	public static double getTokens(String pTokensFile, String pChange, String pApiCall) throws TokensException 
	{		
		Properties properties= new Properties();
		properties = loadTokensFile(pTokensFile);
		
		StringBuilder urlToGetTokens = new StringBuilder();
		urlToGetTokens = new StringBuilder(pApiCall);
		HashMap<String, String> hMapContracts = new HashMap<String, String>();
		HashMap<String, String> hMapQuantities = new HashMap<String, String>();
		String formatTokenKey;
		String contract;
		String name;
		String quantity;
		int numToken=1;
		double balance=-1.00;
        
        for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();)
        {
            final String tokenKey = (String)e.nextElement();
            if (tokenKey.contains("contract"))
            {
            	formatTokenKey="token"+Integer.toString(numToken);
            	contract = properties.get(formatTokenKey+"contract").toString().toLowerCase();
            	name = properties.get(formatTokenKey+"name").toString().toUpperCase();
            	quantity = properties.get(formatTokenKey+"quantity").toString();
            	       	
            	hMapContracts.put(contract, name);
            	hMapQuantities.put(contract, quantity);
            	urlToGetTokens.append(contract);
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
		   	
		   	balance = Values.extractValuesFromJSON(dataObj, hMapContracts, hMapQuantities);
		   	
		   	if (Double.compare(balance, -1.00) == 0)
		   	{
		   		throw new TokensException("ERROR_BALANCE");
		   	}
		}
		catch (TokensException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new TokensException("NO_CONNECTION");
		}
		
		return balance;
	}

}
