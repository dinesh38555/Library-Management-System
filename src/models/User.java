package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String user_id;
    private String password;
    private List<Book> borrowed_books;
    private List<String> borrowHistory;
    private double totalFinePaid = 0.0;
    private double outstandingFine = 0.0;

    public User(String user_id,String password){
        this.user_id = user_id;
        this.password = password;
        this.borrowed_books = new ArrayList<>();
        this.borrowHistory = new ArrayList<>();
    }
    
    public String getUserId() {
        return user_id;
    }
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    public List<Book> getBorrowedBooks() {
        return borrowed_books;
    }
    public List<String> getBorrowHistory(){
        return borrowHistory;
    }
    public void addToBorrowHistory(String transaction){
        borrowHistory.add(transaction);
    }
    public void borrowBook(Book book) {
        borrowed_books.add(book);
    }
    public void returnBook(Book book) {
        borrowed_books.remove(book);
    }
    public double getTotalFinePaid(){
        return totalFinePaid;
    }
    public double getOutstandingFine(){
        return outstandingFine;
    }
    public void addToTotalFinePaid(double amount) {
        totalFinePaid += amount;
    }

    public void addToOutstandingFine(double amount) {
        outstandingFine += amount;
        if (outstandingFine < 0) outstandingFine = 0; 
    }

    public void clearOutstandingFine() {
        outstandingFine = 0.0;
    }
}