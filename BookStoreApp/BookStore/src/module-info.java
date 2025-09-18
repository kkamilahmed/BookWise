module BookStore {
    requires javafx.controls;
    requires javafx.fxml;

    opens bookstore to javafx.fxml;
    exports bookstore;
}
