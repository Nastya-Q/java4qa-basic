package com.db;

import com.db.dal.TickerDAO;
import com.db.domain.StockDAO;
import com.db.domain.Ticker;
import com.db.ClientUI;

import com.db.service.BuyOperationException;
import com.db.service.Controller;
import com.db.service.SellOperationException;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class ControllerSystemTest {
    private Controller controller = new Controller();
    private StockDAO stockDAO = new StockDAO();
    private ClientUI clientUI = new ClientUI();
    private TickerDAO tickerDAO = new TickerDAO();
    private String[] args = {""};

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
        double testResult = controller.sell("TKR", 1);
        assertEquals(1.0, testResult, 0.0001);
    }
    @Test
    public void buyOperationCalculation() throws SQLException, BuyOperationException, SellOperationException {
        double testResult = controller.buy("TKR", 2);
        assertEquals(.5, testResult, .0001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void buyOperation() throws SQLException, BuyOperationException, SellOperationException {
        controller.buy("TKR", -1);
    }

    @Test(expected = com.db.service.BuyOperationException.class)
    public void tickerNotFoundBuy() throws SQLException, BuyOperationException, SellOperationException {
        controller.buy("TKN", 0);
    }

    @Test(expected = com.db.service.SellOperationException.class)
    public void tickerNotFoundSell() throws SQLException, BuyOperationException, SellOperationException {
        controller.sell("TKN", 0);
    }

    @Test
    public void invalidOperationText() throws SQLException, BuyOperationException, SellOperationException {
        try {
        stockDAO.placeOrder("TKR", 1, 3);
            fail();
        }catch (IllegalArgumentException e) {
            assertEquals("Unsupported operation 3",e.getMessage());
        }
    }

    @Test(expected = com.db.service.BuyOperationException.class)
    public void clientUI() throws SQLException, BuyOperationException, SellOperationException {
        clientUI.main(args);
    }

    @Test
    public void getTicker() throws SQLException, BuyOperationException, SellOperationException {
        Ticker ticker = new Ticker("TK1");
 //       ticker.getTickerId();
        String ticker1 = ticker.getTickerId();
        assertEquals("TK1",ticker1);
    }

    @Test(expected = SQLException.class)
    public void noJdbcConnection() throws SQLException, BuyOperationException, SellOperationException {
        tickerDAO.openConnection("Test");
    }

}
