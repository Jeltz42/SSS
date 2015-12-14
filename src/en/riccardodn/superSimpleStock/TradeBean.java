package en.riccardodn.superSimpleStock;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Bean that represent the trade of a single stock
 * @author riccardo
 *
 */
public class TradeBean {
	public static enum BuyOrSell {buy,sell}

	private BigDecimal sharesQuantity;
	private BuyOrSell buyOrSellIndicator;
	private BigDecimal price;
	private Calendar date;
	
	public TradeBean(){}
	
	public TradeBean(BigDecimal sharesQuantity, BuyOrSell buyOrSellIndicator, BigDecimal price, Calendar date){
		this.sharesQuantity = sharesQuantity;
		this.buyOrSellIndicator = buyOrSellIndicator;
		this.price = price;
		this.date = date;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public BigDecimal getSharesQuantity() {
		return sharesQuantity;
	}

	public void setSharesQuantity(BigDecimal sharesQuantity) {
		this.sharesQuantity = sharesQuantity;
	}

	public BuyOrSell getBuyOrSellIndicator() {
		return buyOrSellIndicator;
	}

	public void setBuyOrSellIndicator(BuyOrSell buyOrSellIndicator) {
		this.buyOrSellIndicator = buyOrSellIndicator;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat();
		StringBuffer sb = new StringBuffer();

		sb.append("\nQuantity of share: " + sharesQuantity);
		sb.append("\nBuy or sell indicator: " + buyOrSellIndicator);
		sb.append("\nPrice: " + price);
		sb.append("\nDate: " + df.format(date.getTime()));

		return sb.toString();
	}

}
