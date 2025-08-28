package org.example.models;

/**
 * Гость.
 */
public class Guest extends User {

    public Guest(String name, String userId, String email) {
        super(name, userId, email, UserType.GUEST);
    }

    @Override
    public int getMaxBooks() {
        return 1;
    }

    @Override
    public int getBorrowDays() {
        return 7;
    }

    @Override
    public double getFinePerDay() {
        return 0;
    }
}
