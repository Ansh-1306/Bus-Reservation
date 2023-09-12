import java.util.Scanner;

public class Booking {
    static Scanner sc = new Scanner(System.in);
    String phone;
    String f_name;
    String l_name;
    String pswd;

    public Booking(String phone, String f_name, String l_name, String pswd) {
        this.phone = phone;
        this.f_name = f_name;
        this.l_name = l_name;
        this.pswd = pswd;
        initialise();
    }

    void initialise(){
        
    }
    
    void optionMenu() {
        boolean flag = true;
        while (flag) {
            System.out.println("\n0 -> Go Back.");
            System.out.println("1 -> Book Ticket.");
            System.out.println("2 -> View All Buses.");
            System.out.println("3 -> Track Live Bus Location.");
            System.out.println("4 -> View Tickets.");
            System.out.println("5 -> View And Update Profile.");
            int choice = -1;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Enter your choice in numbers only.");
                choice = -1;
                sc.nextLine();
            }
            switch (choice) {
                case 0:
                    flag = false;
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                default:
                    System.out.println("Enter number corresponding to below option.");
                    break;
            }
        }
    }

    public static void admin(){
        boolean flag = true;
        while (flag) {
            System.out.println("\n0 -> Go Back.");
            System.out.println("1 -> Add Bus.");
            System.out.println("2 -> Remove Bus.");
            System.out.println("3 -> Edit Bus Schedules.");
            int choice = -1;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Enter your choice in numbers only.");
                choice = -1;
                sc.nextLine();
            }
            switch (choice) {
                case 0:
                    flag = false;
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                default:
                    System.out.println("Enter number corresponding to below option.");
                    break;
            }
        }
    }
}
