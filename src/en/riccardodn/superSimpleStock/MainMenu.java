package en.riccardodn.superSimpleStock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import en.riccardodn.superSimpleStock.StockBean.StockType;
import en.riccardodn.superSimpleStock.TradeBean.BuyOrSell;

/**
 * Main class containing the main method and managing the flow of the application
 * @author riccardo
 *
 */
public class MainMenu {

	private static final String ERROR_INPUT_NOT_VALID_TRY_AGAIN = "Error: Input not valid, try again.";
	private static final int DEFAULT_GET_INT_ERROR = -999;
	private static final String OPTION_NOT_AVAILABLE_TRY_AGAIN = "\nOption not available, try again.\n";

	public static void main(String[] args) {
		int choice = 0;
		Map<String, StockBean> stocksMap = new LinkedHashMap<String, StockBean>();
		
		createInitialObjects(stocksMap);
		
		System.out.println("***** Stocks actual state *****");
		
		printStocksTable(new ArrayList<StockBean>(stocksMap.values()));

		System.out.println("\n***** Financial manager menu *****");

		// CICLE OVER THE FIRST LEVEL MENU
		do {
			System.out.println("\nOperations available:\n"
					+ "1) Single stock operation.\n"
					+ "2) Calculate the GBCE all share index.\n"
					+ "3) Exit from menu.");

			choice = getInputInt();
			
			switch (choice) {
				// single stock operation
				case 1:
					selectOrCreateStock(stocksMap);
					break;
					
				// all share index calculus
				case 2:
					BigDecimal allShareIndex = FinancialManager.calculateGBCEAllShareIndex(new ArrayList<StockBean>(stocksMap.values()));
					System.out.println("\nThe GBCE all share index is: " + allShareIndex.setScale(3, RoundingMode.HALF_EVEN).toString() + " (rounded to the third decimal)");
					break;
					
				// exit from menu
				case 3:
					break;
	
				default:
					System.out.println(OPTION_NOT_AVAILABLE_TRY_AGAIN);
					break;
			}

		} while (choice != 3);

	}

	/**
	 * Method that manage the second level menu related to a single stock operation (the stock has to be chosen)
	 * @param stocksMap
	 */
	private static void selectOrCreateStock(Map<String, StockBean> stocksMap) {
		int choice = -1;
		List<StockBean> stockList = new ArrayList<StockBean>(stocksMap.values());

		// CICLE OVER THE SECOND LEVEL MENU
		do{
			stockList = new ArrayList<StockBean>(stocksMap.values());
			printStocksTable(stockList);
			System.out.println("\nInsert the number related to the stock you want to work with (0 to create a new stock, -1 to go back):");
			choice = getInputInt();
			
			// create new stock
			if(choice == 0){
				createNewStock(stocksMap);
			} 
			// stock selected
			else if(choice > 0 && choice <= stocksMap.values().size()){
				manageSingleStockOperation(stockList.get(choice-1));
			} 
			// option not available
			else if(choice != -1){
				System.out.println(OPTION_NOT_AVAILABLE_TRY_AGAIN);
			}
			
		} while(choice != -1);
	}

	/**
	 * Method that manage the third level menu related to a single stock operation (stock already chosen)
	 * @param stocksMap
	 */
	private static void manageSingleStockOperation(StockBean stock) {
		int choice = 0;

		// CICLE OVER THE THIRD LEVEL MENU
		do {
			System.out.println("\nStock selected:\n" + stock.toString());
			System.out.println("\n\nOperations available on the selected stock (insert -1 to go back):"
					+ "\n1) Calculate the dividend yield."
					+ "\n2) Calculate the P/E Ratio."
					+ "\n3) Record a trade, with timestamp, quantity of shares, buy or sell indicator and price."
					+ "\n4) Calculate Stock Price based on trades recorded in past 15 minutes.");
			
			choice = getInputInt();
			
			switch (choice) {
			case 1:
				System.out.println("\nThe dividend yeld is: " + FinancialManager.calculateDividendYeld(stock));
				break;
				
			case 2:
				System.out.println("\nThe P/E Ratio is: " + FinancialManager.calculatePERatio(stock));
				break;
				
			case 3:
				createNewTrade(stock);
				break;
				
			case 4:
				System.out.println("\nThe stock Price is (1 if there are no trades in the last 15 minutes): " + FinancialManager.calculateStockPrice(stock));
				break;

			default:
				System.out.println(OPTION_NOT_AVAILABLE_TRY_AGAIN);
				break;
			}
			
		} while (choice != -1);
	}
	
	private static void createNewTrade(StockBean stock) {
		boolean inputIsValid = true;
		TradeBean newtrade = new TradeBean();
		BuyOrSell buyOrSellIndicator = BuyOrSell.buy;
		String stringIndicator = "";
		int sharesQuantity = 0;
		int price = 0;
		
		do{
			inputIsValid = true;
		
			// TRADE BUY OR SELL INDICATOR
			System.out.println("\nInsert buy or sell indicator (buy/sell):\n");
			stringIndicator = getInputString();
			if(!stringIndicator.equalsIgnoreCase(BuyOrSell.buy.toString()) && !stringIndicator.equalsIgnoreCase(BuyOrSell.sell.toString())){
				System.out.println(ERROR_INPUT_NOT_VALID_TRY_AGAIN);
				inputIsValid = false;
				continue;
			}
			
			// TRADE SHARES QUANTITY
			System.out.println("\nInsert trade shares quantity:\n");
			sharesQuantity = getInputInt();
			
			if(sharesQuantity == DEFAULT_GET_INT_ERROR){
				System.out.println(ERROR_INPUT_NOT_VALID_TRY_AGAIN);
				inputIsValid = false;
				continue;
			}
			
			// TRADE PRICE
			System.out.println("\nInsert trade price:\n");
			price = getInputInt();
			
			if(price == DEFAULT_GET_INT_ERROR){
				System.out.println(ERROR_INPUT_NOT_VALID_TRY_AGAIN);
				inputIsValid = false;
				continue;
			}

		} while(!inputIsValid);
		
		newtrade.setBuyOrSellIndicator(buyOrSellIndicator);
		newtrade.setSharesQuantity(new BigDecimal(sharesQuantity));
		newtrade.setPrice(new BigDecimal(price));
		newtrade.setDate(GregorianCalendar.getInstance());
		
		// save the new trade in the stock's tradeList
		stock.getTradeList().add(newtrade);
		
		System.out.println("\nThe following trade was saved:\n" + newtrade.toString());
	}

	private static void createNewStock(Map<String, StockBean> stocksMap) {
		boolean inputIsValid = true;
		StockBean newStock = new StockBean();
		StockType stockType = StockType.Common;
		String symbol = "";
		String stringType = "";
		int lastDividend = 0;
		int fixedDividend = 0;
		int parValue = 0;
		int tickerPrice = 0;
		
		do{
			inputIsValid = true;
			
			// STOCK SYMBOL
			System.out.println("\nInsert stock symbol:\n");
			symbol = getInputString().toUpperCase();
			
			// the stock symbol must be unique
			if(stocksMap.containsKey(symbol)){
				System.out.println("\nWarning: symbol already present in the stock list, choose another one.");
				inputIsValid = false;
				continue;
			}
		
			// STOCK TYPE
			System.out.println("\nInsert stock type (common/preferred):\n");
			stringType = getInputString();
			if(!stringType.equalsIgnoreCase(StockType.Common.toString()) && !stringType.equalsIgnoreCase(StockType.Preferred.toString())){
				System.out.println(ERROR_INPUT_NOT_VALID_TRY_AGAIN);
				inputIsValid = false;
				continue;
			}
			
			// STOCK LAST DIVIDEND
			System.out.println("\nInsert stock last dividend:\n");
			lastDividend = getInputInt();
			
			if(lastDividend == DEFAULT_GET_INT_ERROR){
				System.out.println("ERROR_INPUT_NOT_VALID_TRY_AGAIN");
				inputIsValid = false;
				continue;
			}
			
			// STOCK FIXED DIVIDEND
			if(stringType.equalsIgnoreCase(StockType.Preferred.toString())){
				System.out.println("\nInsert stock fixed dividend:\n");
				fixedDividend = getInputInt();
				stockType = StockType.Preferred;

				if(fixedDividend == DEFAULT_GET_INT_ERROR){
					System.out.println("ERROR_INPUT_NOT_VALID_TRY_AGAIN");
					inputIsValid = false;
					continue;
				}
			}
			
			// STOCK PAR VALUE
			System.out.println("\nInsert stock par value:\n");
			parValue = getInputInt();

			if(parValue == DEFAULT_GET_INT_ERROR){
				System.out.println("ERROR_INPUT_NOT_VALID_TRY_AGAIN");
				inputIsValid = false;
				continue;
			}
			
			// STOCK TICKER PRICE
			System.out.println("\nInsert stock ticker price:\n");
			tickerPrice = getInputInt();

			if(tickerPrice == DEFAULT_GET_INT_ERROR){
				System.out.println("ERROR_INPUT_NOT_VALID_TRY_AGAIN");
				inputIsValid = false;
				continue;
			}

		} while(!inputIsValid);
		
		newStock.setStockSymbol(symbol);
		newStock.setType(stockType);
		newStock.setLastDividend(new BigDecimal(lastDividend));
		newStock.setFixedDividend(new BigDecimal(fixedDividend));
		newStock.setParValue(new BigDecimal(parValue));
		newStock.setTickerPrice(new BigDecimal(tickerPrice));
		
		// save the new stock in the stocksMap
		stocksMap.put(newStock.getStockSymbol(), newStock);
		
		System.out.println("\nThe following stock was saved:\n" + newStock.toString());
	}

	private static void createInitialObjects(Map<String, StockBean> stocksMap) {
		Calendar fiveMinutesAgo = new GregorianCalendar();
		Calendar sevenMinutesAgo = new GregorianCalendar();
		Calendar tenMinutesAgo = new GregorianCalendar();
		Calendar fifteenMinutesAgo = new GregorianCalendar();
		
		fiveMinutesAgo.add(Calendar.MINUTE, -5);
		sevenMinutesAgo.add(Calendar.MINUTE, -7);
		tenMinutesAgo.add(Calendar.MINUTE, -10);
		fifteenMinutesAgo.add(Calendar.MINUTE, -15);
		
		StockBean stockTEA = new StockBean("TEA", StockType.Common, new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("100"), new BigDecimal("10"));
		StockBean stockPOP = new StockBean("POP", StockType.Common, new BigDecimal("8"), new BigDecimal("0"), new BigDecimal("100"), new BigDecimal("15"));
		StockBean stockALE = new StockBean("ALE", StockType.Common, new BigDecimal("23"), new BigDecimal("0"), new BigDecimal("60"), new BigDecimal("8"));
		StockBean stockGIN = new StockBean("GIN", StockType.Preferred, new BigDecimal("8"), new BigDecimal("2"), new BigDecimal("100"), new BigDecimal("20"));
		StockBean stockJOE = new StockBean("JOE", StockType.Common, new BigDecimal("13"), new BigDecimal("0"), new BigDecimal("250"), new BigDecimal("18"));
		
		stockTEA.getTradeList().add(new TradeBean(new BigDecimal("2"), TradeBean.BuyOrSell.buy, new BigDecimal("12"), fifteenMinutesAgo));
		stockTEA.getTradeList().add(new TradeBean(new BigDecimal("4"), TradeBean.BuyOrSell.buy, new BigDecimal("11"), tenMinutesAgo));
		stockTEA.getTradeList().add(new TradeBean(new BigDecimal("3"), TradeBean.BuyOrSell.sell, new BigDecimal("13"), fiveMinutesAgo));
		stockPOP.getTradeList().add(new TradeBean(new BigDecimal("5"), TradeBean.BuyOrSell.buy, new BigDecimal("10"), sevenMinutesAgo));
		stockPOP.getTradeList().add(new TradeBean(new BigDecimal("2"), TradeBean.BuyOrSell.sell, new BigDecimal("14"), fiveMinutesAgo));
		stockGIN.getTradeList().add(new TradeBean(new BigDecimal("6"), TradeBean.BuyOrSell.buy, new BigDecimal("6"), fifteenMinutesAgo));
		stockGIN.getTradeList().add(new TradeBean(new BigDecimal("2"), TradeBean.BuyOrSell.sell, new BigDecimal("7"), tenMinutesAgo));
		stockGIN.getTradeList().add(new TradeBean(new BigDecimal("2"), TradeBean.BuyOrSell.sell, new BigDecimal("8"), fiveMinutesAgo));
		stockJOE.getTradeList().add(new TradeBean(new BigDecimal("4"), TradeBean.BuyOrSell.buy, new BigDecimal("16"), fifteenMinutesAgo));
		stockJOE.getTradeList().add(new TradeBean(new BigDecimal("3"), TradeBean.BuyOrSell.sell, new BigDecimal("17"), tenMinutesAgo));
		stockJOE.getTradeList().add(new TradeBean(new BigDecimal("5"), TradeBean.BuyOrSell.buy, new BigDecimal("13"), sevenMinutesAgo));
		stockJOE.getTradeList().add(new TradeBean(new BigDecimal("2"), TradeBean.BuyOrSell.buy, new BigDecimal("15"), fiveMinutesAgo));
		
		// The stock symbol is used as unique key in the GBCE stock sety
		stocksMap.put("TEA", stockTEA);
		stocksMap.put("POP", stockPOP);
		stocksMap.put("ALE", stockALE);
		stocksMap.put("GIN", stockGIN);
		stocksMap.put("JOE", stockJOE);
	}


	private static void printStocksTable(List<StockBean> arrayList) {
		int counter = 1;
		
		System.out.println("\n* Stock Symbol *    Type    * Last Dividend * Fixed Dividend * Par Value * Ticker Price *");
		
		for (StockBean stock : arrayList) {
			System.out.println(counter + ") " 
					+ StringUtils.rightPad(stock.getStockSymbol(), 11) + " | " 
					+ StringUtils.rightPad(stock.getType().toString(),10) + " | " 
					+ StringUtils.leftPad(stock.getLastDividend().toString(),13) + " | " 
					+ StringUtils.leftPad(stock.getFixedDividend().toString().concat("%"),14) + " | " 
					+ StringUtils.leftPad(stock.getParValue().toString(),9) + " | " 
					+ StringUtils.leftPad(stock.getTickerPrice().toString(),12));
			counter ++;
		}
	}

	public static String getInputString() {
		@SuppressWarnings("resource")
		Scanner myInput = new Scanner(System.in);
		String input = null;

		try {
			input = myInput.nextLine();
		} catch (NoSuchElementException e) {
			System.out.println("Warning: input not read correctly, saving empty string.\n");
			input = "";
		}

		return input;
	}	

	public static int getInputInt() {
		@SuppressWarnings("resource")
		Scanner myInput = new Scanner(System.in);
		int i = 0;

		try {
			i = myInput.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Warning: input not read correctly.\n");
			i = DEFAULT_GET_INT_ERROR;
		}

		return i;
	}

}
