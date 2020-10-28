package com.example.my2cents;

import org.junit.Test;

import static org.junit.Assert.*;

public class NewBalanceTest {

    @Test
    public void getBalance(){
            double balance = 1500;
            double amount = 300;
            String type = "Deduction";
            double expected = 1200;

            AnalyticsLog balanceTest = new AnalyticsLog();
            double output = balanceTest.getBalance(balance, amount, type);

            assertEquals(expected, output, 0);
    }

}
