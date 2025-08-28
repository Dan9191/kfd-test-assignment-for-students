package org.example.models;

/**
 * Студент.
 */
public class Student extends User {
    public Student(String name, String userId, String email) {
        super(name, userId, email, UserType.STUDENT);
    }

    @Override
    public int getMaxBooks() { return 3; }

    @Override
    public int getBorrowDays() { return 14; }

    @Override
    public double getFinePerDay() { return 0.50; }
}

