package execution;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cryptovalues.Balance;
import cryptovalues.Change;
import sendmessage.TelegramEncode;
import sendmessage.WaitUtils;
import sendmessage.WhatsApp;
import videorecording.VideoRecord;

class TestCase1 {
	
	private static double balanceTotal;
	private static VideoRecord videoRecord;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		videoRecord = new VideoRecord();
		videoRecord.startRecording();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		videoRecord.stopRecording();
	}
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}
	
	@DisplayName("Testing cryptovalues")
	@Test
	void test1() {
		balanceTotal = Balance.getBalanceTotal();
		Balance.showConsole();
		Balance.showUI();
	}
	
	@DisplayName("Testing sendmessage::WhatsApp")
	@Test
	void test2() {
		String chatNameWhatsApp = "to complete";
		String text = "BALANCE (TOTAL=TOKENS+CRYPTOCURRENCIES+CHANGE) IN " + Change.CHANGE_UPPER + ": " + String.format("%.2f", balanceTotal).replace(",", ".");
		WhatsApp.send(chatNameWhatsApp, text);
	}

	@DisplayName("Testing sendmessage::Telegram")
	@Test
	void test3() {
		String chatIdTelegram = "to complete";
		String text = "BALANCE (TOTAL=TOKENS+CRYPTOCURRENCIES+CHANGE) IN " + Change.CHANGE_UPPER + ": " + String.format("%.2f", balanceTotal).replace(",", ".");
		String message = TelegramEncode.send(chatIdTelegram, text);
		assertTrue(message.contains("ok"));
		WaitUtils.sleep(10000);
	}

}

