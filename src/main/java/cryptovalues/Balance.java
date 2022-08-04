package cryptovalues;

import java.io.File;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

public class Balance {
	
	private static double balanceTokens;
	private static double balanceCryptocurrencies;
	private static double balanceChange;
	private static double balanceTotal=-1.00;
	
	public static void getValues()
	{
		try {
			
			balanceTokens = TokensCall.getTokens(pathUsage("tokens.properties"), Change.CHANGE_LOWER, "https://api.coingecko.com/api/v3/simple/token_price/ethereum?contract_addresses=");
			balanceCryptocurrencies = CryptocurrenciesCall.getCryptocurrencies(pathUsage("cryptocurrencies.properties"), Change.CHANGE_LOWER, "https://api.coingecko.com/api/v3/simple/price?ids=");
			balanceChange = Change.getChange(pathUsage("change.properties"));
			balanceTotal = balanceTokens + balanceCryptocurrencies + balanceChange;

		}
		catch (TokensException | CryptocurrenciesException e)
		{
			System.err.println(e.getMessage());
		}
		
	}
	
	public static double getBalanceTotal()
	{
		getValues();
		return balanceTotal;
	}
	
	public static void showConsole()
	{
		if (Double.compare(balanceTotal, -1.00) != 0)
		{
			System.out.println("BALANCE (TOKENS) IN " + Change.CHANGE_UPPER + ": " + String.format("%.2f", balanceTokens).replace(",", "."));
			System.out.println();
			System.out.println("BALANCE (CHANGE) IN " + Change.CHANGE_UPPER + ": " + String.format("%.2f", balanceChange).replace(",", "."));
			System.out.println();
			System.out.println("BALANCE (TOTAL=TOKENS+CRYPTOCURRENCIES+CHANGE) IN " + Change.CHANGE_UPPER + ": " + String.format("%.2f", balanceTotal).replace(",", "."));
			System.out.println();
		
			sampleTime();
			
			System.out.println();
			System.out.println();
		}	
	}
	
	public static void showUI()
	{
		if (Double.compare(balanceTotal, -1.00) != 0)	
			JOptionPane.showMessageDialog(null, "BALANCE (TOTAL) IN " + Change.CHANGE_UPPER + ":\n" + String.format("%.2f", balanceTotal).replace(",", "."), "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private static void sampleTime()
	{
		LocalDateTime locaDate = LocalDateTime.now();
		int hours  = locaDate.getHour();
		int minutes = locaDate.getMinute();
		int seconds = locaDate.getSecond();
		
		System.out.print("CURRENT TIME: " + hours  + ":" + minutes + ":" + seconds);
	}
	
	private static String pathUsage(String pFileName)
	{		
		String p = "." + File.separator + "config" + File.separator + pFileName;
		
		return p;
	}

}
