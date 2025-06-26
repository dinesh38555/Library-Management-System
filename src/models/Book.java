package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String author;
    private final String title;
    private boolean isBorrowed;
    private LocalDate borrowedDate;

    public Book(String id, String author, String title) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.isBorrowed = false;
        this.borrowedDate = null;
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %s | Title: \"%s\" | Author: %s | Borrowed Status: %s",
            id, title, author, isBorrowed ? "Borrowed" : "Available"
        );
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowStatus(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }
}
