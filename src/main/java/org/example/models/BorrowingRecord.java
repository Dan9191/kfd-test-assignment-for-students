package org.example.models;

import java.time.LocalDate;

/**
 * Запись о взятии книги.
 */
public class BorrowingRecord {

    private Book book;
    private User user;
    private LocalDate borrowDate;

    public BorrowingRecord(User user, Book book, LocalDate borrowDate) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public boolean isOverdue() {
        int days = user.getBorrowDays();
        return borrowDate.plusDays(days).isAfter(LocalDate.now());
    }

}
