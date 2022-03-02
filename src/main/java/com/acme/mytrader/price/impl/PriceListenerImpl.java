package com.acme.mytrader.price.impl;

import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import com.acme.mytrader.strategy.TradingStrategy;
import org.apache.commons.lang3.StringUtils;

public class PriceListenerImpl implements PriceListener {
    private final TradingStrategy tradingStrategy;
    private final PriceSource priceSource;

    public PriceListenerImpl(TradingStrategy tradingStrategy, PriceSource priceSource) throws Exception {
        checkExpectedData(tradingStrategy, priceSource);
        this.tradingStrategy = tradingStrategy;
        this.priceSource = priceSource;
        this.priceSource.addPriceListener(this);
    }

    private void checkExpectedData(TradingStrategy tradingStrategy, PriceSource priceSource) throws Exception {
        String errors = "";
        if(tradingStrategy == null){
            errors = "TradingStrategy cannot be empty";
        }
        if(priceSource == null){
            errors += StringUtils.isBlank(errors)?"":"; " + "PriceSource cannot be empty";
        }
        if(StringUtils.isBlank(errors)){
            return;
        }
        throw new Exception(errors);
    }

    @Override
    public void priceUpdate(String security, double price) {
       if (tradingStrategy.executeStrategy(security, price)) {
           priceSource.removePriceListener(this);
       }
    }
}
