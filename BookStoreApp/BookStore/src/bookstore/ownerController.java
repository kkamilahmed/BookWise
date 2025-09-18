package bookstore;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ownerController  {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML private TableColumn<Book, Double> bookPrice;
    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String> bookTitle;  
    @FXML private TextField titleInput;
    @FXML private TextField priceInput;  
    @FXML private Label bookLabel;       
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> usernameColumn;
    @FXML private TableColumn<Customer, String> passwordColumn;
    @FXML private TableColumn<Customer, Integer> pointsColumn;
    @FXML private TextField usernameInput;
    @FXML private TextField passwordInput;
    @FXML private Label customerLabel;
    

    @FXML
    public void logout(ActionEvent event) throws IOException {
        BookStore.getInstance().logout();

        // Load login.fxml
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene newScene = new Scene(root);

        // Fade out current scene
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), ((Node)event.getSource()).getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            stage.setScene(newScene);

            // Optional: fade in the login screen
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }


    @FXML
    public void switchToOwnerView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ownerView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        fadeOutAndSlideIn(scene, root, stage);
    }

    @FXML
    public void switchToOwnerBookView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ownerBookView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        fadeOutAndSlideIn(scene, root, stage);
    }

    @FXML
    public void switchToOwnerCustomerView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ownerCustomerView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        fadeOutAndSlideIn(scene, root, stage);
    }
    
        

    
    public void initialize() {
        // Initialize book-related table if present
        if (bookTable != null && bookTitle != null && bookPrice != null) {
            initializeBooks();
        }

        // Initialize customer-related table if present
        if (customerTable != null && usernameColumn != null && passwordColumn != null && pointsColumn != null) {
            initializeCustomers();
        }
    }


    
    @FXML
    protected void initializeBooks() {
    bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookName"));
    bookPrice.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
    ObservableList<Book> observableList = FXCollections.observableArrayList(BookStore.getInstance().getBookList());
    bookTable.setItems(observableList);
    }
    
    @FXML
    private void removeSelectedBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            BookStore.getInstance().removeBook(selectedBook.getBookName(), selectedBook.getBookPrice());
            bookTable.getItems().remove(selectedBook);
        }
    }
    


    
    @FXML
    private void addBook() {
        String title = titleInput.getText().trim();
        String priceText = priceInput.getText().trim();

       
        bookLabel.setVisible(false);

        if (title.isEmpty() || priceText.isEmpty()) {
            bookLabel.setText("Please enter both title and price.");
            bookLabel.setVisible(true);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);


            for (Book b : BookStore.getInstance().getBookList()) {
                if (b.getBookName().equalsIgnoreCase(title)) {
                    bookLabel.setText("This book already exists.");
                    bookLabel.setVisible(true);
                    return;
                }
            }


            Book newBook = new Book(title, price);
            BookStore.getInstance().addBook(newBook);

            titleInput.clear();
            priceInput.clear();
            bookLabel.setText("");
            bookLabel.setVisible(false);
            initializeBooks();

        } catch (NumberFormatException e) {
            bookLabel.setText("Invalid price entered.");
            bookLabel.setVisible(true);
        }
    }


    
    private void initializeCustomers() {



    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
    pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

    ObservableList<Customer> customers = FXCollections.observableArrayList(BookStore.getInstance().getCustomerList());
    customerTable.setItems(customers);

    }
    
    @FXML
    private void addCustomer() {
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText().trim();

        customerLabel.setText("");
        customerLabel.setVisible(false);

        if (username.isEmpty() || password.isEmpty()) {
            customerLabel.setText("Please enter both username and password.");
            customerLabel.setVisible(true);
            return;
        }


        for (Customer c : BookStore.getInstance().getCustomerList()) {
            if (c.getUsername().equalsIgnoreCase(username)) {
                customerLabel.setText("Customer already exists.");
                customerLabel.setVisible(true);
                return;
            }
        }


        Customer newCustomer = new Customer(username, password);
        BookStore.getInstance().addCustomer(newCustomer);

        usernameInput.clear();
        passwordInput.clear();
        customerLabel.setText("");
        customerLabel.setVisible(false);
        initializeCustomers();
    }

    @FXML
    private void removeSelectedCustomer() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            BookStore.getInstance().removeCustomer(selected.getUsername());
            customerTable.getItems().remove(selected);
        }
    }
    
    private void fadeOutAndSlideIn(Scene currentScene, Parent nextRoot, Stage stage) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), currentScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            Scene newScene = new Scene(nextRoot);
            stage.setScene(newScene);

            // Slide in from the right
            nextRoot.setTranslateX(50);  // start 50px to the right
            nextRoot.setOpacity(0);      // and invisible

            TranslateTransition slide = new TranslateTransition(Duration.millis(300), nextRoot);
            slide.setFromX(50);
            slide.setToX(0);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), nextRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            slide.play();
            fadeIn.play();
        });

        fadeOut.play();
    }
}
