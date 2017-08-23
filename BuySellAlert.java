import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ${Shreyas Mahanthappa Nagaraj}
 * 
 */
public class BuySellAlert {
	
	// Assuming userids, stock trade data are available already
	static List<String> userids;                       // userids
	static String rawTradeData;                        // Raw trade data/ Input trade data
	static List<String> transactionDataforUser;        // Transaction data of all users
	static List<String> userTradeData;                 // Trade data of one user
	static Date transactionDate;

	public static void main(String[] args) throws ParseException {
		
		List<String> friends_trades = new ArrayList<>();
		Map<String, Integer> buySellData = new HashMap<>();
		
		BuySellAlert buysellalert = new BuySellAlert();
		// Generate alerts for each user
		for(String userid : userids){
		  friends_trades = buysellalert.getWeektradesData(userid, rawTradeData);
		  if(friends_trades != null){
		      buySellData= buysellalert.processBuySellData(friends_trades);
		  }
		  if(buySellData != null){
			  buysellalert.generateRankedAlert(buySellData);
		  }
		}
	}

	/**
	 * This method takes the buysellData in the format 'BUY,GOOG'
	 * and generates final output, i.e, generates ranked alerts(high to low)
	 * 
	 * @param buySellData
	 * @return none
	 */
	public void generateRankedAlert(Map<String, Integer> buySellDataIn) {
	   if(buySellDataIn.size() > 0){
		 for(Map.Entry<String, Integer> entry : buySellDataIn.entrySet()){
			if(entry.getValue() > 0)
				System.out.println(entry.getValue() +", "+entry.getKey() +"," +"BUY");
			else if(entry.getValue() < 0)
				System.out.println(entry.getValue() +", "+entry.getKey() +"," +"SELL");
		}
	  }
	}

	/**
	 * This method takes the buysellData in the format '2014-01-01,BUY,GOOG'
	 * and creates BUY/SELL map where BUY/SELL gets cancelled each other in case.
	 * LinkedHashMap preserves the insertion order, hence helps generate
	 * ranked alerts
	 * 
	 * @param buySellData
	 * @return Map<String, Integer>, filtered/processed trade data
	 */
	public Map<String, Integer> processBuySellData(List<String> buyselldataIn) {
	  if(buyselldataIn.size() > 0){
		Map<String, Integer> alertData = new LinkedHashMap<>();
		for(String dataIn : buyselldataIn){
			String[] data = dataIn.split(",");
			String BuySell = data[1];
			String ticker = data[2];
			
			// if the trade for a particular ticket has been already processed, update values
			// otherwise, insert it into the map, based on whether the ticket has been BOUGHT or SOLD
			if(alertData.containsKey(ticker)){
				if(BuySell.equalsIgnoreCase("BUY"))
					alertData.put(ticker, alertData.get(ticker)+1);
				else if(BuySell.equals("SELL"))
					alertData.put(ticker, alertData.get(ticker)-1);
			}
			else{
				if(BuySell.equalsIgnoreCase("BUY"))
					alertData.put(ticker, 1);
				else if(BuySell.equals("SELL"))
					alertData.put(ticker, -1);
			}
		}
		return alertData;
	  }
	  return null;
	}


	/**
	 * This method extracts, trade data for each friend of an user
	 * and filters it to consider only the last past week Trade data
	 * of friends and returns the same
	 * 
	 * @param String (user id)
	 * @param String (stock trade data)
	 * @return Map<String, Integer>
	 */
	public List<String> getWeektradesData(String userid, String data) throws ParseException {
     if(!userid.equalsIgnoreCase(null) && (!data.equals(null))){		
		List<String> friends = getFriendsListForUser(userid);
		Date today = new Date();
		List<String> userTradeList = new ArrayList<>();
		for(String friend : friends){
			userTradeData = getTradeTransactionsForUser(friend, data);
			 for(String oneTradeData : userTradeData){
			   transactionDate = new SimpleDateFormat("yyyy-MM-dd").parse(oneTradeData.substring(0, userTradeData.indexOf(",")));
			     if(transactionDate.compareTo(today) <= 7 ){
				  userTradeList.add(oneTradeData);
			  }
		    }
		 }
		return userTradeList;
      }
      return null;
	}

	/*
	 * Returns a list of trades represented by a string “<date>,<BUY|SELL>,<ticker>”,
	 * ordered by trade date with the most recent trade first in the list.
	 *  
	 */
	public static List<String> getTradeTransactionsForUser(String friend, String rawTradeData) {
		return transactionDataforUser;
	}
	
	/*
	 * Returns a list of user IDs (strings that uniquely identify a user)
	 * representing the friends of a user.
	 */
	public List<String> getFriendsListForUser(String user) {
		return userids;
	}

}
