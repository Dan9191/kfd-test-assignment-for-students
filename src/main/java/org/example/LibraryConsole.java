
package org.example;

import org.example.models.Book;
import org.example.models.BorrowingRecord;
import org.example.models.Library;
import org.example.models.User;
import org.example.models.UserType;

import java.util.List;
import java.util.Scanner;

public class LibraryConsole {

    private final Library library;
    private final Scanner scanner;


    public LibraryConsole() {
        this.library = new Library();
        this.scanner = new Scanner(System.in);

        // Добавим немного тестовых данных для демонстрации
        initializeSampleData();
    }

    public static void main(String[] args) {
        LibraryConsole app = new LibraryConsole();
        app.run();
    }

    public void run() {
        System.out.println("=== Welcome to Library Management System ===");

        while (true) {
            showMainMenu();
            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: handleBookManagement(); break;
                case 2: handleUserManagement(); break;
                case 3: handleBorrowingOperations(); break;
                case 4: showOverdueBooks(); break;
                case 5: searchBooks(); break;
                case 0:
                    System.out.println("Goodbye!");
                    return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Book Management");
        System.out.println("2. User Management");
        System.out.println("3. Borrowing Operations");
        System.out.println("4. View Overdue Books");
        System.out.println("5. Search Books");
        System.out.println("0. Exit");
    }

    private void handleBookManagement() {
        while (true) {
            System.out.println("\n=== Book Management ===");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. View All Books");
            System.out.println("0. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: addBook(); break;
                case 2: removeBook(); break;
                case 3: viewAllBooks(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void addBook() {
        System.out.println("\n--- Add New Book ---");
        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine().trim();

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            System.out.println("Title, author and ISBN are required!");
            return;
        }

        library.addBook(title, author, isbn, genre);
        System.out.println("Book added successfully!");
    }

    private void removeBook() {
        System.out.println("\n--- Remove Book ---");
        System.out.print("Enter ISBN to remove: ");
        String isbn = scanner.nextLine().trim();

        if (library.removeBook(isbn)) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Book not found or could not be removed.");
        }
    }

    private void viewAllBooks() {
        System.out.println("\n--- All Books ---");
        List<Book> books = library.showAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%d. %s by %s (ISBN: %s, Genre: %s) - %s%n",
                    i + 1,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getGenre(),
                    book.isAvailable() ? "Available" : "Borrowed"
            );
        }
    }

    private void handleUserManagement() {
        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. Register User");
            System.out.println("2. View All Users");
            System.out.println("3. View User Details");
            System.out.println("0. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: registerUser(); break;
                case 2: viewAllUsers(); break;
                case 3: viewUserDetails(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void registerUser() {
        System.out.println("\n--- Register New User ---");
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.println("Select user type:");
        System.out.println("1. Student");
        System.out.println("2. Faculty");
        System.out.println("3. Guest");

        int typeChoice = getIntInput("Enter choice (1-3): ");
        UserType userType = switch (typeChoice) {
            case 1 -> UserType.STUDENT;
            case 2 -> UserType.FACULTY;
            case 3 -> UserType.GUEST;
            default -> {
                System.out.println("Invalid choice. Defaulting to Student.");
                yield UserType.STUDENT;
            }
        };

        if (name.isEmpty() || userId.isEmpty()) {
            System.out.println("Name and user ID are required!");
            return;
        }

        library.registerUser(name, userId, email, userType);
        System.out.println("User registered successfully!");
    }

    private void viewAllUsers() {
        System.out.println("\n--- All Users ---");
        // В текущей реализации нет метода для получения всех пользователей
        // Для демонстрации просто покажем, что эта функция доступна
        System.out.println("User list functionality would be implemented here.");
        System.out.println("(Currently users can be searched by ID in View User Details)");
    }

    private void viewUserDetails() {
        System.out.println("\n--- User Details ---");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine().trim();

        User user = library.findUser(userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.printf("Name: %s%n", user.getName());
        System.out.printf("User ID: %s%n", user.getUserId());
        System.out.printf("Email: %s%n", user.getEmail());
        System.out.printf("Type: %s%n", user.getUserType());
        System.out.printf("Max books allowed: %d%n", user.getMaxBooks());
        System.out.printf("Borrow days: %d%n", user.getBorrowDays());
        System.out.printf("Fine per day: $%.2f%n", user.getFinePerDay());

        System.out.println("\nBorrowed books:");
        if (user.getBorrowedBooks().isEmpty()) {
            System.out.println("No books borrowed.");
        } else {
            for (Book book : user.getBorrowedBooks()) {
                System.out.printf("- %s by %s (ISBN: %s)%n",
                        book.getTitle(), book.getAuthor(), book.getIsbn());
            }
        }
    }

    // === BORROWING OPERATIONS ===
    private void handleBorrowingOperations() {
        while (true) {
            System.out.println("\n=== Borrowing Operations ===");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("0. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: borrowBook(); break;
                case 2: returnBook(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void borrowBook() {
        System.out.println("\n--- Borrow Book ---");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine().trim();

        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine().trim();

        if (library.borrowBook(userId, isbn)) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Failed to borrow book. Possible reasons:");
            System.out.println("- User or book not found");
            System.out.println("- Book already borrowed");
            System.out.println("- User has reached maximum borrowing limit");
        }
    }

    private void returnBook() {
        System.out.println("\n--- Return Book ---");
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine().trim();

        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine().trim();

        if (library.returnBook(userId, isbn)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Failed to return book. Possible reasons:");
            System.out.println("- User or book not found");
            System.out.println("- Book was not borrowed by this user");
            System.out.println("- Book is already available");
        }
    }

    private void showOverdueBooks() {
        System.out.println("\n--- Overdue Books ---");
        List<BorrowingRecord> overdueBooks = library.getOverdueBooks();

        if (overdueBooks.isEmpty()) {
            System.out.println("No overdue books.");
            return;
        }

        for (BorrowingRecord record : overdueBooks) {
            System.out.printf("Book: %s by %s%n",
                    record.getBook().getTitle(), record.getBook().getAuthor());
            System.out.printf("Borrowed by: %s (%s)%n",
                    record.getUser().getName(), record.getUser().getUserId());
            System.out.printf("Borrow date: %s%n", record.getBorrowDate());
            System.out.printf("Due date: %s%n",
                    record.getBorrowDate().plusDays(record.getUser().getBorrowDays()));
            System.out.println("---");
        }
    }

    private void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.print("Enter search query (title, author, or genre): ");
        String query = scanner.nextLine().trim();

        List<Book> results = library.searchBooks(query);

        if (results.isEmpty()) {
            System.out.println("No books found matching your search.");
            return;
        }

        System.out.printf("Found %d book(s):%n", results.size());
        for (Book book : results) {
            System.out.printf("- %s by %s (ISBN: %s, Genre: %s) - %s%n",
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getGenre(),
                    book.isAvailable() ? "Available" : "Borrowed"
            );
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void initializeSampleData() {
        // Добавляем тестовые книги
        library.addBook("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", "Classic");
        library.addBook("To Kill a Mockingbird", "Harper Lee", "9780061120084", "Fiction");
        library.addBook("1984", "George Orwell", "9780451524935", "Dystopian");
        library.addBook("Pride and Prejudice", "Jane Austen", "9780141439518", "Romance");
        library.addBook("The Hobbit", "J.R.R. Tolkien", "9780547928227", "Fantasy");

        // Регистрируем тестовых пользователей
        library.registerUser("Alice Johnson", "alice123", "alice@email.com", UserType.STUDENT);
        library.registerUser("Professor Smith", "smith_prof", "smith@university.edu", UserType.FACULTY);
        library.registerUser("Visitor Guest", "guest001", "guest@visitor.com", UserType.GUEST);

        System.out.println("Sample data loaded successfully!");
    }

}
