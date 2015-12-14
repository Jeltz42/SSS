package en.riccardodn.superSimpleStock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import en.riccardodn.superSimpleStock.StockBean.StockType;

/**
 * Class with static methods that give the results of an operation over a single stock or all the stocks available.
 * @author riccardo
 * 
 *
 */
public class FinancialManager {

	/**
	 * @param stock
	 * @return the dividend yeld of the given stock
	 */
	public static BigDecimal calculateDividendYeld(StockBean stock){
		BigDecimal result = new BigDecimal("0");
		
		if(StockType.Preferred.equals(stock.getType())){
			result = (stock.getFixedDividend().multiply(stock.getParValue())).divide(stock.getTickerPrice(), 10, RoundingMode.HALF_EVEN);
		} else {
			result = stock.getLastDividend().divide(stock.getTickerPrice(), 10, RoundingMode.HALF_EVEN);
		}
		
		return result;
	}

	/**
	 * @param stock
	 * @return the P/E Ratio of the given stock
	 */
	public static BigDecimal calculatePERatio(StockBean stock) {
		return stock.getTickerPrice().divide(stock.getLastDividend(), 10, RoundingMode.HALF_EVEN);
	}

	/**
	 * @param stock
	 * @return the stock price based on the stock's trades of the last 15 minutes
	 */
	public static BigDecimal calculateStockPrice(StockBean stock) {
		BigDecimal result = new BigDecimal("1");
		Calendar fifteenMinutesAgo = GregorianCalendar.getInstance();
		BigDecimal dividend = new BigDecimal("1");
		BigDecimal divisor = new BigDecimal("1");
		
		fifteenMinutesAgo.add(Calendar.MINUTE, -15);
		
		for (TradeBean trade : stock.getTradeList()) {
			if(trade.getDate().after(fifteenMinutesAgo)){
				dividend = dividend.add(trade.getPrice().multiply(trade.getSharesQuantity()));
				divisor = divisor.add(trade.getSharesQuantity());
			}
		}
		
		result = dividend.divide(divisor, 10, RoundingMode.HALF_EVEN);

		return result;
	}

	/**
	 * @param stockList
	 * @return the GBCE all share index using a geometric mean over the stocks prices
	 */
	public static BigDecimal calculateGBCEAllShareIndex(List<StockBean> stockList) {
		BigDecimal result = new BigDecimal("1");
		Double exponent = new Double("1");
		
		exponent = exponent/stockList.size();
		
		for (StockBean stockBean : stockList) {
			result = result.multiply(calculateStockPrice(stockBean));
		}
		
		//the BigDecimal variable "result" has a limited scale to prevent an infinite-value conversion to double
		result = new BigDecimal(Math.pow(result.doubleValue(), exponent));
		
		return result;
	}

}
