package com.db;

import com.db.domain.StockDAO;
import com.db.domain.Ticker;
import com.db.ClientUI;

import com.db.service.BuyOperationException;
import com.db.service.Controller;
import com.db.service.SellOperationException;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ControllerSystemTest {
    private Controller controller = new Controller();
    private StockDAO stockDAO = new StockDAO();
/*    private ClientUI clientUI = new ClientUI();
    private String[] args = {""};*/

    @Test
    public void shouldDoBuyForOneLotOfExistTicker() throws SQLException, BuyOperationException {
        assertEquals(1.0, controller.buy("TKRGZP", 1), 0.01);
    }

    @Test(expected = java.lang.ArithmeticException.class)
    public void shouldGetErrorWhenSellZeroLot() throws SQLException, BuyOperationException, SellOperationException {
        controller.sell("TKR", 0);
    }

    @Test
    public void sellOperation() throws SQLException, BuyOperationException, SellOperationException {
        controller.sell("TKR", 1);
    }
/*
    @Test
    public void buyOperation() throws SQLException, BuyOperationException, SellOperationException {
        controller.buy("TKR", 0);
    }
*/
    @Test(expected = com.db.service.BuyOperationException.class)
    public void tickerNotFoundBuy() throws SQLException, BuyOperationException, SellOperationException {
        controller.buy("TKN", 0);
    }

    @Test(expected = com.db.service.SellOperationException.class)
    public void tickerNotFoundSell() throws SQLException, BuyOperationException, SellOperationException {
        controller.sell("TKN", 0);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidOperation() throws SQLException, BuyOperationException, SellOperationException {
        stockDAO.placeOrder("TKR", 1, 3);
    }
/*
    @Test(expected = com.db.service.BuyOperationException.class)
    public void clientUI() throws SQLException, BuyOperationException, SellOperationException {
        clientUI.main(args);
    }

    @Test
    public void getTicker() throws SQLException, BuyOperationException, SellOperationException {
        Ticker ticker = new Ticker("TK1");
        ticker.getTickerId();
    }
*/
}
