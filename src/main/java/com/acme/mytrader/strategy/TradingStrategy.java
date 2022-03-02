package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy {
    private AtomicReference<Boolean> isBuying = new AtomicReference<>(false);
    private final ExecutionService executionService;
    private final String expectedSecurity;
    private final double expectedPrice;
    private final int count;

    public TradingStrategy(ExecutionService executionService, String expectedSecurity, double expectedPrice, int count) throws Exception {
        checkExpectedData(executionService, expectedSecurity, expectedPrice, count);
        this.executionService = executionService;
        this.expectedSecurity = expectedSecurity;
        this.expectedPrice = expectedPrice;
        this.count = count;
    }

    private void checkExpectedData(ExecutionService executionService, String security, double price, int count ) throws Exception {
        String errors = "";
        if(StringUtils.isBlank(security)){
            errors = "Security cannot be empty";
        }
        if(price < 0 ){
            errors += StringUtils.isBlank(errors)?"":"; " + "Price cannot be less than 0";
        }
        if(count < 0 ){
            errors += StringUtils.isBlank(errors)?"":"; " + "Count cannot be less than 0";
        }
        if(executionService == null ){
            errors += StringUtils.isBlank(errors)?"":"; " + "ExecutionService cannot be empty";
        }
        if(StringUtils.isBlank(errors)){
            return;
        }
        throw new Exception(errors);
    }

    public boolean executeStrategy(String security, double price) {
        if(StringUtils.isBlank(security) || price < 0){
            return false;
        }
        if(security.equals(expectedSecurity) && price < expectedPrice) {
            if (isBuying.compareAndSet(false, true)) {
                buySecurity(security, price, count);
                return true;
            }
        }
        return false;
    }

    private void buySecurity(String security, double price, int count) {
        executionService.buy(security, price, count);
    }


}
