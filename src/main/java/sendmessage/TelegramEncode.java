package sendmessage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class TelegramEncode {

	public static String send(String chatId, String text) {
		
		String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
		//Add Telegram Bot token
	    String apiToken = "to complete";
	    String charset = "UTF-8";
	    String response = null;
	    
	    try {

	    	urlString = String.format(urlString, URLEncoder.encode(apiToken, charset), URLEncoder.encode(chatId, charset), URLEncoder.encode(text, charset));
	    
	    }
	    catch (UnsupportedEncodingException e)
	    {
	    	e.printStackTrace();
	    }
	    	
	    try {
	    	
	    	URL url = new URL(urlString);
	    	URLConnection conn = url.openConnection();
	    	conn.setRequestProperty("Accept-Charset", charset);
	    	InputStream is = new BufferedInputStream(conn.getInputStream());
	    	
	        //Getting text, we can set it to any TextView
	    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        String inputLine = "";
	        StringBuilder sb = new StringBuilder();
	        while ((inputLine = br.readLine()) != null) {
	        	sb.append(inputLine);
	        }
	        //You can set this String to any TextView
	        response = sb.toString();
	        System.out.println(response);

	    }
	    catch (IOException e) {
	    	
	    	e.printStackTrace();
	    	
	    }
	    
	    return response;
	}
}

