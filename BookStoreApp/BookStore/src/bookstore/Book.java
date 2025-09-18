package bookstore;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

// AF(b) = an abstract book where:
//   - d.name = b.bookName
//   - d.price = b.bookPrice 
//   - d.selected = b.selected 

// RI(b) = 
//   - b.bookName is not null and not an empty string
//   - b.bookPrice is non-negative
//   - b.selected is a valid BooleanProperty





public class Book {
    private String bookName;
    private double bookPrice;
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    
    

    public Book(String bookName, double bookPrice) {
        this.bookName = bookName;
        this.bookPrice = bookPrice;
    }

    public String getBookName() {
        return bookName;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
