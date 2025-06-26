# 📚 Library Management System (Java Console Application)
![Java](https://img.shields.io/badge/Java-Console--App-blue?logo=java&logoColor=white)
![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux-lightgrey)
![Status](https://img.shields.io/badge/Status-Stable-brightgreen)
![Data Persistence](https://img.shields.io/badge/Data-Persistent%20Storage-yellow)
![Logging](https://img.shields.io/badge/Logging-Enabled-important)

A feature-rich **Library Management System** built in Java using the console. This project is designed to manage library operations such as book borrowing, returning, fine tracking, and transaction logging — all with persistent storage using serialization.

-----

## ✅ Features

  - 📖 **Book Management**
      - Add, Delete, Search, List books
  - 👤 **User Registration & Login**
      - Each user has their own borrowing history
  - 🔄 **Borrow & Return Books**
      - Books can only be borrowed if they are available
  - ⏰ **Due Date Tracking**
      - Books must be returned within **7 days**
  - 💰 **Fine System**
      - ₹10/day fine after due date
      - Borrowing is blocked if **outstanding fine \> ₹50**
      - Users can pay partial or full fine via "Pay Fine" menu
  - 📂 **Data Persistence**
      - All user and book data is stored using `.ser` files
  - 🧾 **Transaction Logging**
      - All borrow and return operations are logged to a text file
  - 📈 **Admin Dashboard**
      - View total fines collected across all users
  - 📑 **Export to CSV**
      - Export current book list to `books.csv`
  - 🔒 **Admin Mode**
      - Protected with password (`admin123` by default)

-----

## 🧪 Sample Data

The repository includes a sample `books.csv` file under `data/` so you can test the system immediately after cloning. Other files like `.ser` and logs are generated after first run and are **excluded from GitHub** using `.gitignore`.

-----

## 🛠️ Technologies Used

  - **Language:** Java (Object-Oriented Programming, Collections)
  - **Persistence:** Java Serialization (`.ser` files)
  - **Logging:** Text-based logging via `transaction_log.txt`
  - **I/O:** Reading/writing CSV, file-based storage

-----

## 📁 Project Structure

```
LibraryManagementSystem/
│
├── data/ # Stores persistent and runtime data
│ ├── books.csv # Sample book data (versioned)
│ ├── users_data.ser # Saved user objects (ignored in Git)
│ ├── library_data.ser # Saved book objects (ignored in Git)
│ └── transaction_log.txt # Log of all transactions (ignored in Git)
│
├── src/ # Java source code
│ ├── Main.java # Entry point
│ ├── models/
│ │ ├── Book.java
│ │ └── User.java
│ ├── services/
│ │ ├── LibraryService.java
│ │ └── FileHandler.java
│ └── utils/
│ └── TransactionLogger.java
│
├── README.md # This file
└── .gitignore # Git ignore rules
```

## 🚀 How to Run

1.  **Clone the repository**

    ```bash
    git clone https://github.com/dinesh38555/Library-Management-System.git
    cd Library-Management-System
    ```

2.  **Compile the Java code**

    ```bash
    javac -d bin src/**/*.java
    ```

3.  **Run the application**

    ```bash
    java -cp bin Main
    ```

4.  **Use the Console Menu**

    Choose between User or Admin access. Register/Login → Borrow books → Return books → Pay fines.

### 🔐 Admin Login

Default Password: `admin123`

*(Can be changed in `LibraryService.java`'s `adminPassword` field)*

Admin options include:

  * View all transaction logs
  * View total fines collected
  * Export books list to CSV

### 💵 Fine System Logic

  * Every borrowed book has a 7-day deadline.
  * After 7 days, a fine of ₹10/day is applied.
  * Borrowing is blocked until the total outstanding fine is ₹50 or less.
  * Users can pay partial or full fines using the menu.
  * Admin can track total fines collected across all users.


### 📊 Demo Tips

To effectively test the fine system without waiting:

* **Simulate a late return:**
    1.  Borrow a book as a user.
    2.  **Temporarily modify the `borrowedDate`** in your code (e.g., in `LibraryService.java`, you can set `book.setBorrowedDate(LocalDate.now().minusDays(10));`). **Remember to remove this line after testing.**
    3.  Return the book to observe the fine calculation.
* **Pay the fine:** Use the "Pay the pending fines" option from the user menu.
* **Verify as Admin:** Switch to admin mode to view transaction logs and total collected fines.


### 📝 Notes

  * Runtime data (`*.ser`, `transaction_log.txt`) is not pushed to GitHub.
  * Only `books.csv` is tracked for demo/testing.
  * The entire application runs in the console — no GUI required. You can easily extend this to a JavaFX/Swing GUI.

-----

## 📣 Contributions

Want to improve this system? Feel free to fork the repository and raise a pull request\!
Ideas for features include:

  * GUI (JavaFX or Swing)
  * Role-based access
  * Search filters
  * REST API backend
  * SQLite or MySQL integration

-----

## 📄 License

This project is licensed under the MIT License.
