package bookstore;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class customerController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private TableColumn<Book, Boolean> bookSelect;
    @FXML private TableColumn<Book, Double> bookPrice;
    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String> bookTitle;
    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;    
    @FXML private Label pointsLabel;
    @FXML private Label totalBooks;

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
    
    public void initialize() {
        // Initialize book-related table if present
        if (bookTable != null && bookTitle != null && bookPrice != null) {
            initializeBooks();
        }
    pointsLabel.setText(BookStore.getInstance().getCurrentCustomerPoints() + "");
    usernameLabel.setText(BookStore.getInstance().getCurrentCustomerUsername());
    totalBooks.setText("");
    

        int pts = BookStore.getInstance().getCurrentCustomerPoints() ; 
        statusLabel.setText(BookStore.getInstance().getStatus());
    }

    @FXML
    public void buySelectedBooks(ActionEvent event) throws IOException {
    ObservableList<Book> allBooks = bookTable.getItems();
    ArrayList<Book> selectedBooks = new ArrayList<>();

    double totalPrice = 0;

    for (Book book : allBooks) {
        if (book.isSelected()) {
            selectedBooks.add(book);
            totalPrice += book.getBookPrice();
        }
    }
    System.out.println(BookStore.getInstance().getCurrentCustomerPoints());
        if (selectedBooks.isEmpty()) {
        totalBooks.setText("No books selected");
        return;
    }   


    for (Book selected : selectedBooks) {
        BookStore.getInstance().removeBook(selected.getBookName(), selected.getBookPrice());
    }

            updateTotalBooksLabel(totalPrice);


            BookStore.getInstance().updateCurrentCustomerPoints(totalPrice);
            int pts = BookStore.getInstance().getCurrentCustomerPoints() ; 
            pointsLabel.setText(pts+"");
        
    statusLabel.setText(BookStore.getInstance().getStatus());
    initializeBooks();

    
    
    switchTorecieptView(event, totalPrice);


    
}
    
    public void updateTotalBooksLabel(double totalPrice) {
        if (totalBooks != null) {
            totalBooks.setText(String.format("%.2f", totalPrice));
        } else {
            System.out.println("totalBooks label is null!");
        }
    }

    
    @FXML
    public void redeemAndBuyBooks(ActionEvent event) throws IOException {
    ObservableList<Book> allBooks = bookTable.getItems();
    ArrayList<Book> selectedBooks = new ArrayList<>();

    double totalPrice = 0;

    for (Book book : allBooks) {
        if (book.isSelected()) {
            selectedBooks.add(book);
            totalPrice += book.getBookPrice();
        }
    }

        if (selectedBooks.isEmpty()) {
        totalBooks.setText("No books selected");
        return;
    }
                if (BookStore.getInstance().getCurrentCustomerPoints()==0) {
        totalBooks.setText("You Have Nothing To Redeem");
                   System.out.println(BookStore.getInstance().getCurrentCustomerPoints());
        return;
        }

    // Redeem points
    int availablePoints = BookStore.getInstance().getCurrentCustomerPoints();
    int redeemableCents = availablePoints; // 100 points = $1
    double discount = redeemableCents / 100.0;

    double finalPrice = Math.max(0, totalPrice - discount);
    int pointsUsed = (int) Math.min(availablePoints, totalPrice * 100);

    // Update points after redemption
    BookStore.getInstance().setCurrentCustomerPoints(availablePoints - pointsUsed);

    // Remove books from inventory
    for (Book selected : selectedBooks) {
        BookStore.getInstance().removeBook(selected.getBookName(), selected.getBookPrice());
    }

    // Add new points for the final price paid
    BookStore.getInstance().updateCurrentCustomerPoints(finalPrice);

    int pts = BookStore.getInstance().getCurrentCustomerPoints();
    pointsLabel.setText(pts + "");

    statusLabel.setText(BookStore.getInstance().getStatus());

    initializeBooks();

    // Transition to receipt with final price shown
    switchTorecieptView(event, finalPrice);
}
    
    @FXML
    protected void initializeBooks() {
        bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        bookPrice.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));

        // Set up checkbox column
        if (bookSelect != null) {
            bookSelect.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
            bookSelect.setCellFactory(tc -> new javafx.scene.control.cell.CheckBoxTableCell<>());
        }

        ObservableList<Book> observableList = FXCollections.observableArrayList(BookStore.getInstance().getBookList());
        bookTable.setItems(observableList);
    }
   
    public void switchTorecieptView(ActionEvent event, double totalPrice) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recieptView.fxml"));
        Parent root = loader.load();

        customerController controller = loader.getController();
        controller.updateTotalBooksLabel(totalPrice); // set the total in the new view

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();

        fadeOutAndSlideIn(scene, root, stage);
    }
    
    private void fadeOutAndSlideIn(Scene currentScene, Parent nextRoot, Stage stage) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), currentScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            Scene newScene = new Scene(nextRoot);
            stage.setScene(newScene);

            nextRoot.setTranslateX(50);  // start to the right
            nextRoot.setOpacity(0);      // invisible

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
