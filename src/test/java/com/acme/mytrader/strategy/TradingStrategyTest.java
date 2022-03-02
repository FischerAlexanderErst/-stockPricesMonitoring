package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TradingStrategyTest {
    @Mock
    private ExecutionService executionService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNothing() {
    }

    @Test
    public void testBuyIfThresholdIsReached() throws Exception {
        double requiredPrice = 55.0;
        double price = 50.0;
        String security = "IBM";
        int count = 100;
        TradingStrategy tradingStrategy = new TradingStrategy(executionService, security, requiredPrice, count);
        tradingStrategy.executeStrategy(security, price);
        Mockito.verify(executionService,Mockito.times(1)).buy(security,price,100);
    }

    @Test
    public void testBuyTwoTimeIfThresholdIsReached() throws Exception {
        double requiredPrice = 55.0;
        double price = 50.0;
        String security = "IBM";
        int count = 100;
        TradingStrategy tradingStrategy = new TradingStrategy(executionService, security, requiredPrice, count);
        tradingStrategy.executeStrategy(security, price);
        tradingStrategy.executeStrategy(security, price);
        Mockito.verify(executionService,Mockito.times(1)).buy(security,price,100);
    }

    @Test
    public void testBuyIfThresholdIsNotReached() throws Exception {
        double requiredPrice = 55.0;
        double price = 55.0;
        String security = "IBM";
        int count = 100;
        TradingStrategy tradingStrategy = new TradingStrategy(executionService, security, requiredPrice, count);
        tradingStrategy.executeStrategy(security, price);
        Mockito.verify(executionService,Mockito.times(0)).buy(security,price,100);
    }

    @Test
    public void testBuyWithEmptySecurity() throws Exception {
        double requiredPrice = 55.0;
        double price = 54.0;
        String security = "IBM";
        String securityEmpty = "";
        int count = 100;
        TradingStrategy tradingStrategy = new TradingStrategy(executionService, security, requiredPrice, count);
        tradingStrategy.executeStrategy(security, price);
        Mockito.verify(executionService,Mockito.times(0)).buy(securityEmpty,price,100);
    }

    @Test(expected = Exception.class)
    public void testCreatePriceListenerWithNotSuitableFields() throws Exception {
        double requiredPrice = -1.0;
        String security = "";
        int count = -1;
        new TradingStrategy(null, security, requiredPrice, count);
    }
}
