package com.coffeemachine;

import com.coffeemachine.model.Beverage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainTest {

    Beverage testbev = new Beverage();
    Main exp = new Main();

    @Test
    public void test1(){
        testbev.setId(1);
        testbev.setName("americano");
        testbev.setQuantity(10);
        int testexp = exp.expBev(testbev);
        Assert.assertEquals(testexp, 9);
    }
}
