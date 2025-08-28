package org.example.models;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String name;
    protected String userId;
    protected String email;
    protected List<Book> borrowedBooks;
    protected UserType userType;

    public User(String name, String userId, String email, UserType userType) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
        this.userType = userType;
    }

    // Each user type implements these differently
    public abstract int getMaxBooks();
    public abstract int getBorrowDays();
    public abstract double getFinePerDay();

    public boolean canBorrow() {
        return borrowedBooks.size() < getMaxBooks();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}
