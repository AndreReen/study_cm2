package com.coffeemachine;

import com.coffeemachine.core.CustomerDao;
import com.coffeemachine.core.NonExistentBeverageException;
import com.coffeemachine.core.BeverageDao;
import com.coffeemachine.core.SaleDao;
import com.coffeemachine.dao.Dao;
import com.coffeemachine.dao.DaoforSale;
import com.coffeemachine.model.Beverage;
import com.coffeemachine.model.Customer;
import com.coffeemachine.model.NonExistentEntityException;
import com.coffeemachine.model.Sale;

import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final Dao<Beverage, Integer> Beverage_DAO = new BeverageDao();
    private static final Dao<Customer, Integer> Customer_DAO = new CustomerDao();
    private static final DaoforSale<Sale, Integer> Sale_DAO = new SaleDao();

    public static void main(String[] args) {

        int userChoice;

        //log in to coffee machine
        String customer_name = user_menu();
        Customer customer1 = new Customer();
        customer1.setId(0);
        customer1.setName(customer_name);
        addCustomer(customer1);


        do {
            //print out beverage options to choose
            getAllBeverages().forEach(System.out::println);
            System.out.println("\n");
            //get input from user
            userChoice = menu();


            try {
                //expend beverage stock
                Beverage beverage1 = getBeverage(userChoice);
                beverage1.setQuantity(expBev(beverage1));
                updateBeverage(beverage1);

                //record sale to DB
                Sale sale1 = new Sale();
                sale1.setBeverage(beverage1.getName());
                sale1.setCustomer(customer_name);
                addSale(sale1);

            } catch (NonExistentEntityException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage());
            }

        } while (userChoice != 0);

    }

    public static String user_menu() {

        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name to continue ");
        String user_selection = input.nextLine();
        return user_selection;
    }

    public static int menu() {

        int selection;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter drink id to buy or 0 to exit  ");
        selection = input.nextInt();
        return selection;
    }

    /* beverage methods*/

    public static int expBev(Beverage beverage){
        return beverage.getQuantity() - 1;

    }

    public static Beverage getBeverage(int id) throws NonExistentEntityException {
        Optional<Beverage> Beverage = Beverage_DAO.get(id);
        return Beverage.orElseThrow(NonExistentBeverageException::new);
    }

    public static Collection<Beverage> getAllBeverages() {
        return Beverage_DAO.getAll();
    }

    public static void updateBeverage(Beverage beverage) {
         Beverage_DAO.update(beverage);
    }

    public static Optional<Integer> addBeverage(Beverage beverage) {
        return Beverage_DAO.save(beverage);
    }

    public static void deleteBeverage(Beverage beverage) {
        Beverage_DAO.delete(beverage);
    }

    /* customer methods*/
    public static Customer getCustomer(int id) throws NonExistentEntityException {
        Optional<Customer> Customer = Customer_DAO.get(id);
        return Customer.orElseThrow(NonExistentBeverageException::new);
    }

    public static Collection<Customer> getAllCustomers() {
        return Customer_DAO.getAll();
    }

    public static void updateCustomer(Customer customer) {
        Customer_DAO.update(customer);
    }

    public static Optional<Integer> addCustomer(Customer customer) {
        return Customer_DAO.save(customer);
    }

    public static void deleteCustomer(Customer customer) {
        Customer_DAO.delete(customer);
    }

    /* sale methods*/
    public static Optional<Integer> addSale(Sale sale) {
        return Sale_DAO.save(sale);

    }
}

