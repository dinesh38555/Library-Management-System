📚 Library Management System (Java Console Application)
A feature-rich Library Management System built in Java using the console. This project is designed to manage library operations such as book borrowing, returning, fine tracking, and transaction logging — all with persistent storage using serialization.

✅ Features
📖 Book Management (Add, Delete, Search, List)

👤 User Registration & Login (with borrowing history)

🔄 Borrow & Return Books

⏰ Due Date Tracking (7-day deadline)

💰 Fine Calculation & Payment

₹10/day after due date

Block borrowing if fine > ₹50

📂 Data Persistence using .ser files

📈 Track Total Fines Collected (admin dashboard)

📑 Export to CSV (books list)

🧾 Transaction Logging (borrow/return history)

🔒 Admin Mode (password-protected)

🧪 Sample Data
The project includes sample book data (data/books.csv) so you can test immediately after cloning.

🛠️ Technologies Used
Language: Java (OOP + Collections)

Persistence: Java Serialization

Logging: Custom text-based transaction log

File I/O: CSV export, .ser binary storage

📁 Project Structure
bash
Copy
Edit
LibraryManagementSystem/
│
├── data/                     # Stores persistent and runtime data
│   ├── books.csv             # Sample data (tracked)
│   ├── users_data.ser        # User data (runtime)
│   ├── library_data.ser      # Book state (runtime)
│   └── transaction_log.txt   # All borrow/return logs (runtime)
│
├── src/
│   ├── Main.java
│   ├── models/
│   │   ├── Book.java
│   │   └── User.java
│   ├── services/
│   │   ├── LibraryService.java
│   │   └── FileHandler.java
│   └── utils/
│       └── TransactionLogger.java
│
├── README.md
└── .gitignore
🚀 How to Run
Clone the repo

bash
Copy
Edit
git clone https://github.com/dinesh38555/Library-Management-System.git
cd Library-Management-System
Compile the code

bash
Copy
Edit
javac -d bin src/**/*.java
Run the project

bash
Copy
Edit
java -cp bin Main
Follow the console menu to interact (user or admin)

🔐 Admin Login
Default Admin Password: admin123
(Can be modified in LibraryService.java)

📊 Demo Capabilities
Borrow a book → Wait 7+ days → Try returning → Pay fine

Admin → View total fines collected → Export books to CSV

📝 Notes
Serialized files and logs are ignored in GitHub via .gitignore

Only books.csv is tracked for demo/test purposes

Project is self-contained and runs entirely on the console

📣 Contributions
Want to improve features or add GUI version (JavaFX/Swing)? Feel free to fork and raise a pull request!

📄 License
This project is licensed under the MIT License.