import java.util.Scanner;
import services.LibraryService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService ls = new LibraryService();
        while (true) {
            System.out.println("Main Menu: ");
            System.out.println("1. Admin Login");
            System.out.println("2. User Mode");
            System.out.println("3. Exit");
            int input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
                case 1:
                    ls.adminMenu(scanner);
                    break;
                
                case 2:
                    ls.userMenu(scanner);
                    break;

                case 3:
                    System.out.println("Exiting application....");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
