package bookstore;

import java.io.*;
import java.util.*;

public class dataManager {


    protected static ArrayList<Book> readBooksFromFile(String filePath) {
        ArrayList<Book> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length == 2) {
                    String bookName = parts[0].trim();
                    double bookPrice = Double.parseDouble(parts[1].trim());
                    books.add(new Book(bookName, bookPrice));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading books file: " + e.getMessage());
        }

        return books;
    }


    protected static void updateBooksFile(String filePath, ArrayList<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Book book : books) {
                bw.write(book.getBookName() + "-" + book.getBookPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating books file: " + e.getMessage());
        }
    }


    protected static ArrayList<Customer> readCustomersFromFile(String filePath) {
        ArrayList<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    int points = Integer.parseInt(parts[2].trim());

                    Customer customer = new Customer(username, password);
                    customer.setPoints(points);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customers file: " + e.getMessage());
        }

        return customers;
    }


    public static Customer loginManager(ArrayList<Customer> currentCustomers, String username, String password) {
        for (Customer customer : currentCustomers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }


    protected static void updateCustomerFile(String filePath, ArrayList<Customer> customers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Customer customer : customers) {
                bw.write(customer.getUsername() + "-" + customer.getPassword() + "-" + customer.getPoints());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating customer file: " + e.getMessage());
        }
    }

}
