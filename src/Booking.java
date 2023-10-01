import java.io.File;
import java.util.Scanner;

public class Booking {
    static Scanner sc = new Scanner(System.in);
    String phone;
    String f_name;
    String l_name;
    String pswd;
    String gender;
    File dir;

    public Booking(String phone, String f_name, String l_name, String pswd, String gender) {
        this.phone = phone;
        this.f_name = f_name;
        this.l_name = l_name;
        this.pswd = pswd;
        this.gender = gender;
        dir = new File("User_Data", f_name + "_" + phone.substring(7));
        initialise();
    }

    void initialise() {
        Graph network = new Graph();
        Graph.findShortestPath(network.graph, new BusStop("Dahod"), new BusStop("Dang"));
    }

    synchronized void optionMenu() {
        boolean flag = true;
        while (flag) {
            System.out.println("                                ____________________________________");
            System.out.println("                               |                                    |");
            System.out.println("                               |  0 -> Go Back.                     |");
            System.out.println("                               |  1 -> Book Ticket.                 |");
            System.out.println("                               |  2 -> View All Buses.              |");
            System.out.println("                               |  3 -> Track Live Bus Location.     |");
            System.out.println("                               |  4 -> View Tickets.                |");
            System.out.println("                               |  5 -> View And Update Profile.     |");
            System.out.println("                               |____________________________________| ");
            int choice = -1;
            System.out.println();
            System.out.print("                  Your Choice : ");
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
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
                    System.out.println("                  Enter number corresponding to below option.");
                    break;
            }
        }
    }

    public static void admin() {
        Graph network = new Graph();
        boolean flag = true;
        while (flag) {
            System.out.println();
            System.out.println("                                 ________________________________");
            System.out.println("                                |                                |");
            System.out.println("                                |  0 -> Go Back.                 |");
            System.out.println("                                |  1 -> Add Bus.                 |");
            System.out.println("                                |  2 -> Remove Bus.              |");
            System.out.println("                                |  3 -> Edit Bus Schedules.      |");
            System.out.println("                                |________________________________|");
            int choice = -1;
            System.out.println();
            System.out.print("                  Your Choice : ");
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                choice = -1;
                sc.nextLine();
            }
            switch (choice) {
                case 0:
                    System.out.println();
                    System.out.println("                  Logged Out From Admin !!!");
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
                    System.out.println("                  Enter number corresponding to below option.");
                    break;
            }
        }
    }
}
