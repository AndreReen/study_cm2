package com.coffeemachine;

import com.coffeemachine.core.NonExistentBeverageException;
import com.coffeemachine.core.PostgreSqlDao;
import com.coffeemachine.dao.Dao;
import com.coffeemachine.model.Beverage;
import com.coffeemachine.model.NonExistentEntityException;

import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

        private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
        private static final Dao<Beverage, Integer> Beverage_DAO = new PostgreSqlDao();

        public static void main(String[] args) {

            int userChoice;

            do {
                getAllBeverages().forEach(System.out::println);
                userChoice = menu();

                try {
                    Beverage beverage1 = getBeverage(userChoice);
                    beverage1.setQuantity(beverage1.getQuantity() - 1);
                    updateBeverage(beverage1);
                } catch (NonExistentEntityException ex) {
                    LOGGER.log(Level.WARNING, ex.getMessage());
                }

            }while (userChoice != 0);

        }

    public static int menu() {

        int selection;
        Scanner input = new Scanner(System.in);

        System.out.print("Enter drink id to buy or 0 to exit  ");

        selection = input.nextInt();
        return selection;
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
    }

