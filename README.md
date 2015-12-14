

Requirements

    Provide working source code that will :

    a. For a given stock,

     i. calculate the dividend yield

     ii. calculate the P/E Ratio

     iii. record a trade, with timestamp, quantity of shares, buy or sell indicator and price

     iv. Calculate Stock Price based on trades recorded in past 15 minutes

    b. Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

Constraints & Notes:

    Written in one of these languages: Java, C#, C++, Python

    No database or GUI is required, all data need only be held in memory

    Formulas and data provided are simplified representations for the purpose of this exercise

Assumptions:

- In the formulas given there are references to a ticker price not present in the example data, I assumed it being a basic property of every stock.
- Regarding the GBCE all share index, I assumed the prices of the stocks in the geometric mean being the stock price calculated over the last 15 minutes trades (as in the other point of the exercise) for every stock.

Development

The project is developed as a SE Java application, with only the JRE library and another one I added for String utility methods (see note at the bottom). It consists in two bean classes representing the objects "Stock" and "Trade" (StockBean.java, TradeBean.java), a "service" class that calculates the values asked by the user (FinancialManager.java) and a main class that starts the program and manage the interactions with the user (MainMenu.java).

I didn't know if no user interface at all was required or a textual one was needed, therefore i developed the little java program with a textual interface (it was fun, although it would have required more time to really make it user-safe).

The program starts presenting an initial menu that ask for a first distinction about the operation the user want to perform (single stock operation or the GBCE all share index); if "single stock operation" is selected, a new menu appears asking the user to select a stock among the available ones (or to create a new one), then the possible operations on the selected stock are showed.

Note: to represent more easily the data, I added the apache commons-lang library to the project.
