package org.example.models;

import java.util.List;

/**
 * Интерфейс с операциями библиотеки.
 */
public interface LibraryOperations {
    // Book management

    /**
     * Добавление книги в библиотеку.
     * @param title  Название.
     * @param author Автор.
     * @param isbn   Международный стандартный книжный номер.
     * @param genre  Жанр.
     */
    void addBook(String title, String author, String isbn, String genre);

    /**
     * Удаление книги.
     * @param isbn Международный стандартный книжный номер.
     * @return удалось ли удалить книгу.
     */
    boolean removeBook(String isbn);

    /**
     * Поиск книги.
     * @param isbn Международный стандартный книжный номер
     * @return удалось ли найти.
     */
    Book findBook(String isbn);

    /**
     * Фильтрация по книгам.
     * @param query Параметры поиска.
     * @return результат поиска.
     */
    List<Book> searchBooks(String query);

    // User management

    /**
     * Регистрация пользователя.
     * @param name   Имя.
     * @param userId Id.
     * @param email  Почта.
     * @param type   Тип пользователя
     */
    void registerUser(String name, String userId, String email, UserType type);

    /**
     * Поиск пользователя.
     * @param userId Id пользователя.
     * @return Найденный пользователь
     */
    User findUser(String userId);

    // Borrowing operations

    /**
     * Взять книгу.
     * @param userId Id пользователя.
     * @param isbn Международный стандартный книжный номер.
     * @return результат взятия книги.
     */
    boolean borrowBook(String userId, String isbn);

    /**
     * Возврат книги.
     * @param userId Id пользователя.
     * @param isbn Международный стандартный книжный номер.
     * @return результат возврата книги.
     */
    boolean returnBook(String userId, String isbn);

    /**
     * Какие книги просрочены.
     *
     * @return список просроченных книг.
     */
    List<BorrowingRecord> getOverdueBooks();
}
