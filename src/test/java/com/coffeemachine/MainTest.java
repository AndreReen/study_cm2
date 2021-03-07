package com.coffeemachine;

import com.coffeemachine.core.BeverageDao;
import com.coffeemachine.dao.Dao;
import com.coffeemachine.model.Beverage;
import com.coffeemachine.model.Customer;
import com.coffeemachine.model.NonExistentEntityException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;

import static org.mockito.Mockito.*;


public class MainTest {


    Main exp = new Main();

    @Test
    public void test_expBev() {
        Beverage testbev = new Beverage();
        testbev.setId(1);
        testbev.setName("cappucino");
        testbev.setQuantity(2);
        int testexp = exp.expBev(testbev);
        Assert.assertEquals(testexp, 1);
    }

    @Test
    public void test_getBev() {
        Beverage testbev2 = new Beverage();
        try {

            testbev2 = exp.getBeverage(1);
        } catch (NonExistentEntityException ex) {
        }

        Assert.assertNotNull(testbev2);
    }

    @Test
    public void test_getAllBev() {
        Collection<Beverage> Beverages = new ArrayList<>();

            Beverages = exp.getAllBeverages();

        Assert.assertNotNull(Beverages);
    }


    @Test
    public void test_updateBev() {
        Main expdao = new Main();

        Beverage testbev = new Beverage();
        testbev.setId(23);
        testbev.setName("Macchiato");
        testbev.setQuantity(3);

        expdao.updateBeverage(testbev);
        Beverage testbev2 = new Beverage();

        try {testbev2 = expdao.getBeverage(23);}
        catch (NonExistentEntityException ex){}

        Assert.assertEquals(testbev,testbev2);

    }

    @Test
    public void test_addBev() {
        Main expdao = new Main();

        Beverage testbev = new Beverage();
        testbev.setId(5);
        testbev.setName("Macchiato");
        testbev.setQuantity(10);

        expdao.addBeverage(testbev);

        Beverage testbev2 = new Beverage();
        try {
            testbev2 = exp.getBeverage(5);
        } catch (NonExistentEntityException ex) { }

        Assert.assertEquals(testbev2,testbev);
    }


    @Test
    public void test_getCust() {
        Customer testcustomer = new Customer();
        try {

            testcustomer = exp.getCustomer(1);
        } catch (NonExistentEntityException ex) {
        }

        Assert.assertNotNull(testcustomer);
    }

    @Test
    public void test_GetAllCust() {
        Collection<Customer> Customers = new ArrayList<>();

        Customers = exp.getAllCustomers();

        Assert.assertNotNull(Customers);
    }
}
