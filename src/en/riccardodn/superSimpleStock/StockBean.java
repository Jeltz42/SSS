package en.riccardodn.superSimpleStock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean that represent a single stock (with the related trades) in the GBCE stock set
 * @author riccardo
 *
 */
public class StockBean {
	public static enum StockType {Common,Preferred}

	/**
	 * The stock symbol is used as unique key in the GBCE stock set.
	 */
	private String stockSymbol;
	private StockType type; 
	private BigDecimal lastDividend;
	private BigDecimal parValue;
	private BigDecimal fixedDividend;
	private BigDecimal tickerPrice;
	private List<TradeBean> tradeList = new ArrayList<TradeBean>();
	
	public StockBean(){}
	
	public StockBean(String stockSymbol, StockType type, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue, BigDecimal tickerPrice){
		this.stockSymbol = stockSymbol;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
		this.fixedDividend = fixedDividend;
		this.type = type;
		this.tickerPrice = tickerPrice;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}
	
	public StockType getType() {
		return type;
	}
	
	public void setType(StockType type) {
		this.type = type;
	}
	
	public List<TradeBean> getTradeList() {
		return tradeList;
	}
	
	public void setTradeList(List<TradeBean> tradeList) {
		this.tradeList = tradeList;
	}

	public BigDecimal getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(BigDecimal tickerPrice) {
		this.tickerPrice = tickerPrice;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("\nStock Symbol: " + stockSymbol);
		sb.append("\nType: " + type);
		sb.append("\nLast Dividend: " + lastDividend);
		sb.append("\nFixed Dividend: " + fixedDividend);
		sb.append("\nPar Value: " + parValue);
		sb.append("\nTicker Price: " + tickerPrice);
		sb.append("\nTrades: " + tradeList.toString());

		return sb.toString();
	}
}
