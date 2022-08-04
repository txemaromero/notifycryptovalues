package cryptovalues;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;

public class Values {
	
	public static double extractValuesFromJSON(JSONObject pDataObj, HashMap<String, String> pHMapContracts, HashMap<String, String> pHMapQuantities)
	{		
		Set<?> keys = pDataObj.keySet();
	    Iterator<?> i = keys.iterator();
	    
	    double balance = 0.00;
	    double value;
	    double quantity;
	    String kNested;
	    
	    do
	    {
	    	String k = i.next().toString();
	    	if (pHMapContracts == null)
	    	{
	    		System.out.println("Name: " + k.toUpperCase());
	    	}
	    	else
	    	{
	    		System.out.println("Contract address: " + k);
	    		System.out.println("Name: " + pHMapContracts.get(k).toUpperCase());
	    	}
	    	
	        //Get the required object from the above created object
	        JSONObject pDataObjNested = (JSONObject) pDataObj.get(k);
	        Set<?> keysNested = pDataObjNested.keySet();
	        Iterator<?> iNested = keysNested.iterator();
	        
	        do
	        {
	        	kNested = iNested.next().toString();
	        	System.out.print("Value in " + kNested + ": ");
	        	//Get the required data using its key
	        	value = Double.parseDouble(pDataObjNested.get(kNested).toString());
	        	System.out.println(value);
	        	
	        } while (iNested.hasNext());
	        
	        
	        System.out.print("Quantity: ");
	        if (pHMapContracts == null)
	        {
	        	quantity = Double.parseDouble(pHMapQuantities.get(k.toUpperCase()));
	        	System.out.println(quantity);
	        }
	        else
	        {
	        	quantity = Double.parseDouble(pHMapQuantities.get(k.toLowerCase()));
	        	System.out.println(quantity);
	        }
	        
	        balance = balance + (value * quantity);
	        System.out.println();

	    } while (i.hasNext());
	    
	    if (pHMapContracts == null)
	    {
	    	System.out.print("BALANCE (CRYPTOCURRENCIES) IN " + kNested.toUpperCase() + ": ");
	    }
	    else
	    {
	    	System.out.print("BALANCE (TOKENS) IN " + kNested.toUpperCase() + ": ");
	    }
	    
	    System.out.println(String.format("%.2f", balance).replace(",", "."));
	    System.out.println();
	    
	    return balance; 
	}

}
