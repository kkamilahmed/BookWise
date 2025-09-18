 package bookstore;

import java.util.ArrayList;

public class BookStore  {
    private static BookStore instance = null;
    private String booksFile = "src/bookstore/data/bookData.txt";
    private String customersFile = "src/bookstore/data/customerData.txt";
    private ArrayList<Customer> customerList;
    private ArrayList<Book> bookList;
    private static Customer currentCustomer ;
    
    // AF(c) = an abstract bookstore where:
    //   - d.books = c.bookList 
    //   - d.customers = c.customerList 
    //   - d.currentCustomer = c.currentCustomer 
    //   - d.bookdata = c.booksFile 
    //   - d.customerdata = c.customersFile 
    //   - d.instance = c.instance 

    // RI(c) = 
    //  - c.instance is either null or a valid BookStore object
    //  - c.bookList is not null and contains only valid Book objects
    //  - c.customerList is not null and contains only valid Customer objects
    //  - c.currentCustomer is either null or a valid Customer object from c.customerList
    //  - c.booksFile is a valid file path 
    //  - c.customersFile is a valid file path 

    
    
    // Requires: booksFile and customersFile needs to be a valid file path
    // Modifies: this.bookList, this.customerList
    // Effects: Initializes the BookStore object
    
    private BookStore() {
        bookList = dataManager.readBooksFromFile(booksFile);
        customerList = dataManager.readCustomersFromFile(customersFile);
    }
    
   //Requires: None
   //Modifies: BookStore.instance (only if it is null).
   //Effects: Returns the singleton instance of BookStore.Initializes the instance if it hasn't been created yet.
           

    public static BookStore getInstance() {
        if (instance == null) {
            instance = new BookStore();
        }
        return instance;
    }

     // Requires: None
     // Modifies: None
     // Effects: Returns a cloned list of all books currently in the bookstore.

    
    public ArrayList<Book> getBookList(){
        ArrayList<Book> clonedList = new ArrayList<>();
        for (Book book : this.bookList) {
        clonedList.add(new Book(book.getBookName(), book.getBookPrice()));}   
        return clonedList;
    }
    

     // Requires: None
     // Modifies: None
     // Effects: Returns a cloned list of all registered customers.

    
    public ArrayList<Customer> getCustomerList(){
        ArrayList<Customer> clonedList = new ArrayList<>();
        for (Customer x : this.customerList) {
        clonedList.add(new Customer(x.getUsername(), x.getPassword(),x.getPoints()));}   
        return clonedList;
    }
    

     // Requires: username and password are non-null strings
     // Modifies: currentCustomer
     // Effects: If login credentials match, sets the currentCustomer and returns true.
     //          If username/password is "admin", returns true and loads Owner instance.
     //          Otherwise, returns false.


    public boolean login(String username, String password) {
        currentCustomer = dataManager.loginManager(customerList, username, password);
        if (username.equals("admin") && password.equals("admin")) {
            Owner.getInstance();
            return true;
        } else if ( currentCustomer!= null) {
            return true;
        } else {
            return false;
        }
    }
    

     // Requires: currentCustomer is not null
     // Modifies: currentCustomer.points
     // Effects: Sets the points of the current customer to the given total cost.

    public void setCurrentCustomerPoints(int totalCost) {
    currentCustomer.setPoints(totalCost);
    }
    

     // Requires: currentCustomer is not null
     // Modifies: currentCustomer.points
     // Effects: Updates the current customer's points based on total cost of purchase.

     
    public void updateCurrentCustomerPoints(double totalCost) {
    currentCustomer.updatePoints(totalCost);
    }
    

     // Requires: currentCustomer is not null
     // Modifies: None
     // Effects: Returns the points of the current customer.


    public int getCurrentCustomerPoints() {
        return currentCustomer.getPoints();
    }
    

     // Requires: currentCustomer is not null
     // Modifies: None
     // Effects: Returns the username of the current customer.


    public String getCurrentCustomerUsername() {
        return currentCustomer.getUsername();
    }
    

     // Requires: None
     // Modifies: book file, customer file (persistent storage)
     // Effects: Writes the current state of bookList and customerList to the respective files.


    public void logout() {
        dataManager.updateBooksFile(booksFile, bookList);
        dataManager.updateCustomerFile(customersFile, customerList);
    }
    

     // Requires: book is a non-null Book object
     // Modifies: bookList
     // Effects: Adds the given book to the bookList.


    public void addBook(Book book) {
        bookList.add(book);

    }
    

     // Requires: title is non-null, price is a valid double
     // Modifies: bookList
     // Effects: Removes the first book from the bookList matching the given title and price.


    public void removeBook(String title, double price) {
        bookList.removeIf(book -> book.getBookName().equals(title) && book.getBookPrice() == price);
  
    }
    

     // Requires: customer is a non-null Customer object
     // Modifies: customerList
     // Effects: Adds the given customer to the customerList.


    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }
    
    

     // Requires: username is a non-null string
     // Modifies: customerList
     // Effects: Removes all customers from the list whose username matches the given string.


    public void removeCustomer(String username) {
        customerList.removeIf(customer -> customer.getUsername().equals(username));
    }
    
        
     //Requires: currentCustomer is not null
     //Modifies: None
     //Effects: Returns the type/status of the current customer (e.g., Regular, VIP).
     

    public String getStatus(){
        return currentCustomer.getCustomerType();
    }

}
