import static org.junit.Assert.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Test;
/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class JUnitTestCases {

	BuySellAlert buysellalert = new BuySellAlert();
	List<String> dataIn = Arrays.asList("2017-05-16,BUY,GOOG", 
										"2017-05-14,SELL,AMZN",
										"2017-05-12,BUY,GOOG",
										"2017-05-16,SELL,AMZN",
										"2017-05-17,SELL,GOOG", 
										"2017-05-13,SELL,AMZN",
										"2017-05-14,BUY,SIGFIG",
										"2017-05-14,SELL,SIGFIG");
	/*
	 * To check whether a user has Zero friends
	 */
	@Test
	public void userHasFriends() {
		List<String> friendslist = buysellalert.getFriendsListForUser("user10");
		assertEquals(friendslist.size(), 0);
	}
	
	/*
	 * To check whether a particular user friends trade data for
	 * past week is null
	 */
	@Test
	public void tradeDataPastWeek() throws ParseException {
		List<String> pastWeekData = buysellalert.getWeektradesData("user12", "data");
		assertEquals(pastWeekData.size(), 0);
	}
	
	// To check whether net number of BUY/SELL updating correctly
	@Test
	public void processTradeData(){
		Map<String, Integer> processedData = buysellalert.processBuySellData(dataIn);
		assertEquals(processedData.entrySet().size(), 3);
		assertEquals(processedData.get("GOOG").intValue(), 1);
		assertEquals(processedData.get("AMZN").intValue(), 3);
		assertEquals(processedData.get("SIGFIG").intValue(), 0);
	}
}
