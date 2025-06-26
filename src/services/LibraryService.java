package services;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.Book;
import models.User;
import utils.TransactionLogger;

public class LibraryService{
    List<Book> libraryBooks;
    HashSet<String> usedBookIds = new HashSet<>();
    HashMap<String,User> allUsers;
    final double MAX_ALLOWED_FINE = 50;
    final int GRACE_DAYS = 7;
    final int FINE_PER_DAY = 10;
    @SuppressWarnings("unchecked")
    public LibraryService(){
        libraryBooks = FileHandler.loadFromFile("data/library_data.ser");
        if(libraryBooks == null){
            libraryBooks = new ArrayList<>();
            addBooks();;
        }
        for(Book book : libraryBooks){
            usedBookIds.add(book.getId());
        }

        allUsers = (HashMap<String, User>) FileHandler.loadFromFile("data/users_data.ser");
        if(allUsers == null){
            allUsers = new HashMap<>();
        }
    }

    private void addBooks() {
        String[][] bookData = {
            {"B001", "J.K. Rowling", "Harry Potter and the Sorcerer's Stone"},
            {"B002", "J.K. Rowling", "Harry Potter and the Chamber of Secrets"},
            {"B003", "J.R.R. Tolkien", "The Hobbit"},
            {"B004", "J.R.R. Tolkien", "The Fellowship of the Ring"},
            {"B005", "George Orwell", "1984"},
            {"B006", "George Orwell", "Animal Farm"},
            {"B007", "Dan Brown", "The Da Vinci Code"},
            {"B008", "Dan Brown", "Angels & Demons"},
            {"B009", "Paulo Coelho", "The Alchemist"},
            {"B010", "F. Scott Fitzgerald", "The Great Gatsby"},
            {"B011", "Harper Lee", "To Kill a Mockingbird"},
            {"B012", "Jane Austen", "Pride and Prejudice"},
            {"B013", "Markus Zusak", "The Book Thief"},
            {"B014", "Khaled Hosseini", "The Kite Runner"},
            {"B015", "Khaled Hosseini", "A Thousand Splendid Suns"},
            {"B016", "Stephen King", "The Shining"},
            {"B017", "Stephen King", "IT"},
            {"B018", "Agatha Christie", "Murder on the Orient Express"},
            {"B019", "Agatha Christie", "And Then There Were None"},
            {"B020", "Suzanne Collins", "The Hunger Games"},
            {"B021", "Rick Riordan", "The Lightning Thief"},
            {"B022", "Rick Riordan", "The Sea of Monsters"},
            {"B023", "John Green", "The Fault in Our Stars"},
            {"B024", "Veronica Roth", "Divergent"},
            {"B025", "Ernest Hemingway", "The Old Man and the Sea"},
            {"B026", "Arthur Conan Doyle", "The Adventures of Sherlock Holmes"},
            {"B027", "Victor Hugo", "Les Misérables"},
            {"B028", "Leo Tolstoy", "War and Peace"},
            {"B029", "Fyodor Dostoevsky", "Crime and Punishment"},
            {"B030", "Emily Brontë", "Wuthering Heights"}
        };

        for (String[] book : bookData) {
            Book b = new Book(book[0], book[1], book[2]);
            libraryBooks.add(b);
            usedBookIds.add(book[0]);
        }

        FileHandler.saveToFile("data/library_data.ser", libraryBooks);
        System.out.println("Mock books added to the library.");
    }

    public void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Unable to clear screen");
        }
    }


    public void addBook(Scanner scanner){
        System.out.println("The last id used is "+libraryBooks.size());
        System.out.println("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.println("Enter the name of the author: ");
        String author = scanner.nextLine();
        System.out.println("Enter the id of the book: ");
        String id = scanner.nextLine();
        if(usedBookIds.contains(id)){
            System.out.println("A book with this ID already exists. Please use a unique ID.");
            return;
        }
        else{
            usedBookIds.add(id);
            libraryBooks.add(new Book(id,author,title));
            System.out.println("Book \"" + title + "\" added successfully.");
            FileHandler.saveToFile("data/library_data.ser", libraryBooks);
        }
    }
    
    public void listAllBooks(){
        System.out.println("The list of books are: ");
        for(Book book : libraryBooks){
            System.out.println(book.toString());
        }
    }

    public void searchForBook(Scanner scanner){
        System.out.println("Search for a book by: \n 1. By Title \n 2. By ID");
        int input = scanner.nextInt();
        scanner.nextLine();
        switch (input) {
            case 1:
                System.out.println("Enter the title of book you want to search: ");
                String title = scanner.nextLine();
                boolean isMatchFound = false;
                System.out.println("Results for title search: \"" + title + "\"");
                for(Book book : libraryBooks){
                    String bookTitle = book.getTitle();
                    if (bookTitle.toLowerCase().contains(title.toLowerCase())) {
                        isMatchFound = true;
                        System.out.println(book.toString());
                    }
                }
                if(!isMatchFound){
                    System.out.print("No matches for the "+title+" are found");
                }
                break;
        
            case 2: 
                System.out.println("Enter the id of book you want to search: ");
                String id = scanner.nextLine();
                boolean isIDFound = false;
                for(Book book : libraryBooks){
                    if(book.getId().equals(id)){
                        isIDFound = true;
                        System.out.println("Book found: \n"+ book.toString());
                        return ;
                    }
                }
                if(!isIDFound){
                    System.out.println("The book with ID "+id +" is not found");
                }
                break;

            default:
                System.out.println("Invalid choice. Please enter 1 or 2");
        }
    }

    public void deleteBook(Scanner scanner){
        System.out.println("Enter the id of the book you want to delete");
        String inputId = scanner.nextLine();
        boolean didRemove = false;

        Iterator<Book> iterator = libraryBooks.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if(book.getId().equals(inputId)){
                iterator.remove();
                usedBookIds.remove(book.getId());
                didRemove = true;
                FileHandler.saveToFile("data/library_data.ser", libraryBooks);
                System.out.println("The book with ID "+inputId+" is removed");
                break;
            }
        }
        if(!didRemove){
            System.out.println("No book with ID "+inputId+" was found.");
        }
    }

    private void displayAllTransactions() {
        System.out.println("All Transactions Logs:");
        try (Scanner scanner = new Scanner(new File("data/transaction_log.txt"))) {
            boolean isEmpty = true;
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
                isEmpty = false;
            }
            if (isEmpty) {
                System.out.println("No transactions have been recorded yet.");
            }
        }catch (IOException e) {
            System.out.println("Error reading transaction log: " + e.getMessage());
        }
    }

    public void exportBooksToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("data/books.csv"))) {
            pw.println("ID,Title,Author,Borrowed");
            for (Book book : libraryBooks) {
                pw.printf("%s,%s,%s,%s\n", 
                    book.getId(), book.getTitle(), book.getAuthor(), 
                    book.isBorrowed() ? "Borrowed" : "Available");
            }
            System.out.println("Books exported successfully to books.csv");
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    public void calculateTotalFinesCollected() {
        double total = 0.0;
        for (User user : allUsers.values()) {
            total += user.getTotalFinePaid();
        }
        System.out.println("Total Fines Collected: "+total);
    }


    public boolean loginAdmin(Scanner scanner){
        String adminPassword = "admin123";
        int attempts = 4;
        while(attempts > 0){
            System.out.print("Enter admin password: ");
            String password = scanner.nextLine();
            if(password.equals(adminPassword)){
                System.out.println("Admin login successful.");
                return true;
            }
            else{
                attempts--;
                System.out.println("Incorrect password. " + attempts + " attempt(s) left.");
            }
        }
        System.out.println("Access denied. Returning to main menu.");
        return false;
    }

    public void adminMenu(Scanner scanner){
        if (!loginAdmin(scanner)){
            System.out.println("\nPress Enter to return to the menu...");
            scanner.nextLine();
            clearScreen();
            return ;
        } 
        while(true){
            System.out.println("Admin Menu: ");
            System.out.println("1. Add a book ");
            System.out.println("2. List all books");
            System.out.println("3. Search for a book by title or ID");
            System.out.println("4. Delete a book by it's ID");
            System.out.println("5. See all transaction logs");
            System.out.println("6. Export Book details to CSV");
            System.out.println("7. View total fines collected");
            System.out.println("8. Exit to main menu");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    searchForBook(scanner);
                    break;
                case 4:
                    deleteBook(scanner);
                    break;
                case 5:
                    displayAllTransactions();
                    break;
                case 6:
                    exportBooksToCSV();
                    break;
                case 7:
                    calculateTotalFinesCollected();
                    break;
                case 8:
                    return;
                default: 
                    System.out.println("Please choose a valid option");
            }
            System.out.println("\nPress Enter to return to the menu...");
            scanner.nextLine();
            clearScreen();
        }
    }

    public String registerNewUser(Scanner scanner){
        String username;
        while (true) {
            System.out.println("Enter a username: ");
            username = scanner.nextLine();
            if (allUsers.containsKey(username)) {
                System.out.println("Username already exists. Please choose another.");
            } else {
                break;
            }
        }
    
        System.out.println("Enter a strong password: ");
        String password = scanner.nextLine();
    
        User newUser = new User(username, password);
        allUsers.put(username, newUser);
        FileHandler.saveToFile("data/users_data.ser", allUsers);  // Save after registration
        System.out.println("User registration and login successful.");
        return username;           
    }
        
    public String loginUser(Scanner scanner, int mode) {
        if (mode != 1) {
            System.out.println("Are you a new user? ");
            System.out.println("Please enter Y (Yes) or N (No):");
            char userChoice = scanner.next().charAt(0);
            scanner.nextLine();

            if (userChoice == 'Y' || userChoice == 'y') {
                return registerNewUser(scanner);
            }
        }

        // Existing user login

        int usernameAttempts = 4;
        while (usernameAttempts > 0) {
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            if (allUsers.containsKey(username)) {
                User user = allUsers.get(username);
                int passwordAttempts = 4;
                while (passwordAttempts > 0) {
                    System.out.println("Enter your password: ");
                    String password = scanner.nextLine();
                    if (user.checkPassword(password)) {
                        System.out.println("User login successful.");
                        return username;
                    } else {
                        passwordAttempts--;
                        System.out.println("Incorrect password. " + passwordAttempts + " attempt(s) left.");
                    }
                }
                System.out.println("Login failed. Try again later.");
                return "";
            } else {
                usernameAttempts--;
                System.out.println("Username not found. Try again."+usernameAttempts+" attempt(s) left");
            }
        }

        System.out.println("Login failed. Would you like to register as a new user? (Y/N)");
        char choice = scanner.next().charAt(0);
        scanner.nextLine();
    
        if (choice == 'Y' || choice == 'y') {
            return registerNewUser(scanner);
        } else {
            return "";
        }
    }

    private String getBorrowInfo(Book book, String action) {
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String historyEntry = "[" + time + "] - "+action+" - Title: " + book.getTitle() +
                      ", Author: " + book.getAuthor() + ", ID: " + book.getId();
        return historyEntry;
    }

    public String borrowBook(Scanner scanner,boolean isUserLoggedIn,String currentUserId){
        if (!isUserLoggedIn) {
            currentUserId = loginUser(scanner,0);
        }
        if (currentUserId.equals("")) {
            return "";
        }
        User user = allUsers.get(currentUserId);
        System.out.println("Enter the id of book you want to borrow: ");
        String id = scanner.nextLine();
        boolean isBookFoundById = false;
        for(Book book : libraryBooks){
            if(book.getId().equals(id)){
                isBookFoundById = true;
                System.out.println("The book with ID "+id+" is found");
                if(!book.isBorrowed()){
                    if(user.getOutstandingFine() > 50){
                        System.out.println("You have an unpaid fine of ₹" + user.getOutstandingFine() + ". Please clear your dues (₹50 max allowed) before borrowing new books.");
                    }
                    else{
                        book.setBorrowStatus(true);
                        book.setBorrowedDate(LocalDate.now().minusDays(25));
                        // book.setBorrowedDate(LocalDate.now());
                        user.borrowBook(book);
                        user.addToBorrowHistory(getBorrowInfo(book,"borrowed"));
                        FileHandler.saveToFile("data/library_data.ser",libraryBooks);
                        FileHandler.saveToFile("data/users_data.ser", allUsers);
                        TransactionLogger.logTransaction(currentUserId, "borrowed", book);
                        System.out.println("Book borrowed successfully!");
                    }
                }
                else{
                    System.out.println("The book you requested with ID "+id+" is borrowed by someone else currently");
                }
                return currentUserId;
            }
        }
        if(!isBookFoundById){
            System.out.println("The book with ID "+id +" is not found");
            System.out.println("Request admin for the addition of the new book...");
        }
        return currentUserId;
    }

    
    public String returnBook(Scanner scanner, boolean isUserLoggedIn,String currentUserId){
        if (!isUserLoggedIn) {
            currentUserId = loginUser(scanner,1);
        }
        if (currentUserId.equals("")) {
            System.out.println("Please login first before returning");
            return "";
        }
        User user = allUsers.get(currentUserId);
        System.out.println("Enter the id of book you want to return: ");
        String bookId = scanner.nextLine().trim();
        boolean isBookFoundById = false;
        for(Book book : user.getBorrowedBooks()){
            if(book.getId().equals(bookId)){
                isBookFoundById = true;
                System.out.println("The book with ID "+bookId+" is returned");
                book.setBorrowStatus(false);
                user.returnBook(book);
                user.addToBorrowHistory(getBorrowInfo(book,"returned"));
                TransactionLogger.logTransaction(currentUserId, "returned", book);
                FileHandler.saveToFile("data/library_data.ser", libraryBooks);
                FileHandler.saveToFile("data/users_data.ser", allUsers);
                long days = LocalDate.now().toEpochDay() - book.getBorrowedDate().toEpochDay();
                if(days > GRACE_DAYS){
                    long fine = (days - GRACE_DAYS) * FINE_PER_DAY;
                    System.out.println("You have returned the book late by " + (days - GRACE_DAYS) + " days.");
                    System.out.println("Fine to be paid: ₹" + fine);
                    user.addToOutstandingFine(fine);
                    payFine(scanner, currentUserId);
                }
                return currentUserId;
            }
        }
        if(!isBookFoundById){
            System.out.println("The book with ID "+bookId+" was not found in your borrowed list. You can only return books that you have borrowed.");
        }
        return currentUserId;
    }

    private void showBorrowHistory(String currentUserId) {
        if (currentUserId.equals("")) {
            System.out.println("Please login first before accessing history of your borrowed books");
            return ;
        }
        User user = allUsers.get(currentUserId);
        List<String> history = user.getBorrowHistory();
        if (history.isEmpty()) {
            System.out.println("You haven't borrowed any books yet.");
        } 
        else {
            System.out.println("Your Borrow History:");
            for(String entry : history){
                System.out.println(entry);
            }
        }
    }

    public void recommendBooks(String currentUserId) {
        if (currentUserId.equals("")) {
            System.out.println("Please login first to get book recommendations");
            return ;
        }
        System.out.println("Book Recommendations:");

        Map<String, Integer> borrowCountMap = new HashMap<>();

        for (User user : allUsers.values()) {
            for (Book book : user.getBorrowedBooks()) {
                borrowCountMap.put(book.getId(), borrowCountMap.getOrDefault(book.getId(), 0) + 1);
            }
        }

        List<Book> globalTopBooks = new ArrayList<>();
        for (Book book : libraryBooks) {
            if (borrowCountMap.containsKey(book.getId())) {
                globalTopBooks.add(book);
            }
        }

        globalTopBooks.sort((a, b) ->
            borrowCountMap.get(b.getId()) - borrowCountMap.get(a.getId())
        );

        System.out.println("\n Top Borrowed Books (Global):");
        for (int i = 0; i < Math.min(5, globalTopBooks.size()); i++) {
            System.out.println((i + 1) + ". " + globalTopBooks.get(i));
        }

        if (!currentUserId.equals("") && allUsers.containsKey(currentUserId)) {
            User user = allUsers.get(currentUserId);
            Set<String> pastAuthors = new HashSet<>();

            for (Book book : user.getBorrowedBooks()) {
                pastAuthors.add(book.getAuthor().toLowerCase());
            }

            List<Book> recommended = new ArrayList<>();
            for (Book book : libraryBooks) {
                if (!book.isBorrowed() && pastAuthors.contains(book.getAuthor().toLowerCase())) {
                    recommended.add(book);
                }
            }

            if (!recommended.isEmpty()) {
                System.out.println("\n Personalized Recommendations (Based on Authors You Read):");
                int i = 1;
                for (Book book : recommended) {
                    System.out.println(i++ + ". " + book);
                    if (i > 5) break;
                }
            } else {
                System.out.println("\n No personalized recommendations found (yet).");
            }
        }
    }

    public void payFine(Scanner scanner, String userId) {
        User user = allUsers.get(userId);
        double outstanding = user.getOutstandingFine();

        if (outstanding <= 0) {
            System.out.println("You have no outstanding fine to pay. You're good to go!");
            return;
        }

        System.out.println("Your outstanding fine is ₹" + outstanding);
        System.out.print("Enter amount to pay: ");
        
        double amountToPay;
        try {
            amountToPay = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        if (amountToPay <= 0) {
            System.out.println("Amount must be greater than zero.");
        } else if (amountToPay >= outstanding) {
            user.addToTotalFinePaid(outstanding);
            user.clearOutstandingFine();
            System.out.println("Fine fully paid. Thank you!");
        } else {
            user.addToTotalFinePaid(amountToPay);
            user.addToOutstandingFine(-amountToPay);
            System.out.printf("₹%.2f paid. ₹%.2f remaining.%n", amountToPay, user.getOutstandingFine());
        }

        FileHandler.saveToFile("data/users_data.ser", allUsers);
    }

    public void userMenu(Scanner scanner){
        boolean isUserLoggedIn = false;
        String currentUserId = "";
        while(true){
            System.out.println("User Menu: ");
            System.out.println("Please make a choice from the below available options: ");
            System.out.println("1. User Login");
            System.out.println("2. View all books");
            System.out.println("3. Search for book");
            System.out.println("4. Borrow a book");
            System.out.println("5. Return a book");
            System.out.println("6. View Borrow History of books");
            System.out.println("7. Get Book Recommendation");
            System.out.println("8. Pay Fine");
            System.out.println("9. Exit to main menu");

            int input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
                case 1:
                    if (!isUserLoggedIn){
                        currentUserId = loginUser(scanner, 0);
                        if (!currentUserId.equals("")) {
                            isUserLoggedIn = true;
                        }
                    } 
                    else{
                        System.out.println("You are logged in as " + currentUserId + ".");
                    }
                    break;

                case 2:
                    listAllBooks();
                    break;
                
                case 3:
                    searchForBook(scanner);
                    break;

                case 4:
                    currentUserId = borrowBook(scanner, isUserLoggedIn, currentUserId);
                    if(!currentUserId.equals("")) {
                        isUserLoggedIn = true;
                    }
                    break;

                case 5:
                    currentUserId = returnBook(scanner, isUserLoggedIn, currentUserId);
                    if(!currentUserId.equals("")) {
                        isUserLoggedIn = true;
                    }
                    break;
                
                case 6:
                    showBorrowHistory(currentUserId);
                    break ;

                case 7:
                    recommendBooks(currentUserId);
                    break;
                
                case 8:
                    payFine(scanner, currentUserId);
                    break; 

                case 9:
                    System.out.println("Returning to main menu...");
                    return;

                default: 
                    System.out.println("Please choose a valid option");
            }
            System.out.println("\nPress Enter to return to the menu...");
            scanner.nextLine();
            clearScreen();
        }
    }   
}