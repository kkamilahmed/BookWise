package bookstore;

import java.util.ArrayList;

public class Owner {
    private static Owner instance = null;

    private Owner() {

    }

    protected static Owner getInstance() {
        if (instance == null) {
            instance = new Owner();
        }
        return instance;
    }
    
    public void addBook(String title, double price) {
        Book tempBook = new Book(title, price); 
        BookStore.getInstance().addBook(tempBook);
    }

    public void removeBook(String title, double price) {
        BookStore.getInstance().removeBook(title, price);
    }

    public void addCustomer(String username, String password){
        Customer tempCustomer = new Customer(username, password);
        BookStore.getInstance().addCustomer(tempCustomer);
    }

    public void removeCustomer(String username, String password) {
        BookStore.getInstance().removeCustomer(username);
    }





}