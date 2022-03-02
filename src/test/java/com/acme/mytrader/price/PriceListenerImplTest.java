package com.acme.mytrader.price;

import com.acme.mytrader.price.impl.PriceListenerImpl;
import com.acme.mytrader.strategy.TradingStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PriceListenerImplTest {
    @Mock
    private PriceSource priceSource;

    @Mock
    private TradingStrategy tradingStrategy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuyIfThresholdIsReached() throws Exception {
        double price = 50.0;
        String security = "IBM";
        when(tradingStrategy.executeStrategy(anyString(),anyDouble())).thenReturn(true);
        PriceListener priceListener = new PriceListenerImpl(tradingStrategy, priceSource);
        priceListener.priceUpdate(security, price);
        Mockito.verify(tradingStrategy,Mockito.times(1)).executeStrategy(security,price);
        Mockito.verify(priceSource,Mockito.times(1)).removePriceListener(priceListener);
    }

    @Test(expected = Exception.class)
    public void testCreatePriceListenerWithNotSuitableFields() throws Exception {
        new PriceListenerImpl(null, null);
    }
}
