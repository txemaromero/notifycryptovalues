package execution;

import java.io.IOException;
import java.util.function.BooleanSupplier;

import cryptovalues.Balance;
import cryptovalues.Change;
import sendmessage.TelegramEncode;
import sendmessage.WaitUtils;
import sendmessage.WhatsApp;

public class Main {

	public static void main(String[] args) {
		
		String chatNameWhatsApp = "to complete";
		String chatIdTelegram = "to complete";
		
		final int MAXTIMEOUTMS = 5000;
		
		double balanceTotal = Balance.getBalanceTotal();
		Balance.showConsole();
		Balance.showUI();
        
    	System.out.println("Press enter key to not send to WhatsApp and Telegram ...");
    	
    	BooleanSupplier anyKeyPressed = () -> {
			try {
				return (System.in.available() > 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		};
    	boolean pressed = WaitUtils.waitUntil(anyKeyPressed, MAXTIMEOUTMS);
    	
    	if (!pressed)
    	{
    		sendViaWhatsApp(chatNameWhatsApp, balanceTotal);
    		sendViaTelegram(chatIdTelegram, balanceTotal);
    	}
    	else	System.out.print("\nApplication execution ended\n");
		
	}
	
	public static void sendViaWhatsApp(String chatName, double balanceTotal)
	{
		if (Double.compare(balanceTotal, -1.00) != 0)
		{
			String text = "BALANCE (TOTAL=TOKENS+CRYPTOCURRENCIES+CHANGE) IN " + Change.CHANGE_UPPER + ": " + String.format("%.2f", balanceTotal).replace(",", ".");
			WhatsApp.send(chatName, text);
		}	
	}
	
	public static void sendViaTelegram(String chatId, double balanceTotal)
	{
		if (Double.compare(balanceTotal, -1.00) != 0)
		{
			String text = "BALANCE (TOTAL=TOKENS+CRYPTOCURRENCIES+CHANGE) IN " + Change.CHANGE_UPPER + ": " + String.format("%.2f", balanceTotal).replace(",", ".");
			TelegramEncode.send(chatId, text);
		}	
	}	

}