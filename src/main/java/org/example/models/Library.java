package org.example.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Реализация операций с книгами.
 */
public class Library implements LibraryOperations {
    // Use HashMap for fast lookup by key
    private final Map<String, Book> books;      // ISBN -> Book
    private final Map<String, User> users;      // UserID -> User

    // Видимо список, для взятых книг
    private final List<BorrowingRecord> borrowingHistory;

    public Library() {
        books = new HashMap<>();           // O(1) book lookup
        users = new HashMap<>();           // O(1) user lookup
        borrowingHistory = new ArrayList<>(); // Chronological order
    }

    public List<Book> showAllBooks() {
        return new ArrayList<>(books.values());
    }

    public List<User> showAllUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Добавление книги в библиотеку.
     * @param title  Название.
     * @param author Автор.
     * @param isbn   Международный стандартный книжный номер.
     * @param genre  Жанр.
     */
    @Override
    public void addBook(String title, String author, String isbn, String genre) {

        // как я понимаю, не предусмотрена возможность наличия 2 одинаковых книг, так что будем игнорировать дубликаты
        if (!books.containsKey(isbn)) {
            Book book = new Book(title, author, isbn, genre);
            books.put(isbn, book);
        }
    }

    /**
     * Удаление книги.
     * @param isbn Международный стандартный книжный номер.
     * @return удалось ли удалить книгу.
     */
    @Override
    public boolean removeBook(String isbn) {
        return books.remove(isbn) != null;
    }

    /**
     * Поиск книги.
     * @param isbn Международный стандартный книжный номер
     * @return удалось ли найти.
     */
    @Override
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    /**
     * Фильтрация по книгам.
     * @param query Параметры поиска.
     * @return результат поиска.
     */
    @Override
    public List<Book> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String searchTerm = query.trim().toLowerCase();

        return books.values().stream()
                .filter(book ->
                        (book.getTitle() != null && book.getTitle().toLowerCase().contains(searchTerm)) ||
                                (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(searchTerm)) ||
                                (book.getGenre() != null && book.getGenre().toLowerCase().contains(searchTerm))
                )
                .collect(Collectors.toList());
    }

    /**
     * Регистрация пользователя.
     * @param name   Имя.
     * @param userId Id.
     * @param email  Почта.
     * @param type   Тип пользователя
     */
    @Override
    public void registerUser(String name, String userId, String email, UserType type) {
        // будем игнорировать дубликаты
        if (!users.containsKey(userId)) {
            switch (type) {
                case STUDENT -> users.put(userId, new Student(name, userId, email));
                case GUEST -> users.put(userId, new Guest(name, userId, email));
                case FACULTY -> users.put(userId, new Faculty(name, userId, email));
            }
        }
    }

    /**
     * Поиск пользователя.
     * @param userId Id пользователя.
     * @return Найденный пользователь
     */
    @Override
    public User findUser(String userId) {
        return users.get(userId);
    }

    /**
     * Взять книгу.
     * @param userId Id пользователя.
     * @param isbn Международный стандартный книжный номер.
     * @return результат взятия книги.
     */
    @Override
    public boolean borrowBook(String userId, String isbn) {
        User user = users.get(userId);     // Fast lookup
        Book book = books.get(isbn);       // Fast lookup

        // Validation logic
        if (user == null || book == null) return false;
        if (!book.isAvailable()) return false;
        if (!user.canBorrow()) return false;

        // Process borrowing
        book.setAvailable(false);
        user.getBorrowedBooks().add(book);
        borrowingHistory.add(new BorrowingRecord(user, book, LocalDate.now()));

        return true;
    }

    /**
     * Возврат книги.
     * @param userId Id пользователя.
     * @param isbn Международный стандартный книжный номер.
     * @return результат возврата книги.
     */
    @Override
    public boolean returnBook(String userId, String isbn) {
        User user = users.get(userId);
        Book book = books.get(isbn);

        // Validation logic
        if (user == null || book == null) return false;
        if (book.isAvailable()) return false;
        if (!user.getBorrowedBooks().contains(book)) return false;

        user.getBorrowedBooks().remove(book);
        book.setAvailable(true);
        borrowingHistory.removeIf(record -> record.getBook().getIsbn().equals(isbn));

        return true;
    }

    /**
     * Какие книги просрочены.
     *
     * @return список просроченных книг.
     */
    @Override
    public List<BorrowingRecord> getOverdueBooks() {
        return borrowingHistory.stream()
                .filter(BorrowingRecord::isOverdue)
                .collect(Collectors.toList());
    }
}
