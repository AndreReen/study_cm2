package com.coffeemachine;

import com.coffeemachine.model.Beverage;
import com.coffeemachine.model.Customer;
import com.coffeemachine.model.NonExistentEntityException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

public class MainTest {

    Beverage testbev = new Beverage();
    Main exp = new Main();

    @Test
    public void test1() {
        testbev.setId(1);
        testbev.setName("cappucino");
        testbev.setQuantity(2);
        int testexp = exp.expBev(testbev);
        Assert.assertEquals(testexp, 1);
    }

    @Test
    public void test2() {
        Beverage testbev2 = new Beverage();
        try {

            testbev2 = exp.getBeverage(1);
        } catch (NonExistentEntityException ex) {
        }

        Assert.assertNotNull(testbev2);
    }

    @Test
    public void test3() {
        Collection<Beverage> Beverages = new ArrayList<>();

            Beverages = exp.getAllBeverages();

        Assert.assertNotNull(Beverages);
    }

    @Test
    public void test4() {
        Customer testcustomer = new Customer();
        try {

            testcustomer = exp.getCustomer(1);
        } catch (NonExistentEntityException ex) {
        }

        Assert.assertNotNull(testcustomer);
    }

    @Test
    public void test5() {
        Collection<Customer> Customers = new ArrayList<>();

        Customers = exp.getAllCustomers();

        Assert.assertNotNull(Customers);
    }
}
