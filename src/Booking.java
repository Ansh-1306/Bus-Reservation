import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Booking {
    String phone;
    String f_name;
    String l_name;
    String pswd;
    String gender;
    int age;
    ArrayList<String> tickets;
    File dir;
    static Graph network;
    Connection con;
    Scanner sc;

    public Booking(String phone, String f_name, String l_name, String pswd, String gender, int age,Connection con,Scanner sc) throws Exception {
        this.phone = phone;
        this.f_name = f_name;
        this.l_name = l_name;
        this.pswd = pswd;
        this.gender = gender;
        this.age = age;
        this.con=con;
        this.sc=sc;
        dir = new File("User_Data", f_name + "_" + phone.substring(7));
        tickets = new ArrayList<>(Arrays.asList(dir.list()));
        network = new Graph();
        optionMenu();
    }

    private void optionMenu() throws InterruptedException, IOException {
        boolean flag = true;
        while (flag) {
            System.out.println("                                ____________________________________ ");
            System.out.println("                               |                                    |");
            System.out.println("                               |  0 -> Go Back.                     |");
            System.out.println("                               |  1 -> Book Ticket.                 |");
            System.out.println("                               |  2 -> Cancel Ticket.               |");
            System.out.println("                               |  3 -> View Tickets.                |");
            System.out.println("                               |  4 -> Book Bus Trip.               |");
            System.out.println("                               |____________________________________|");
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
                    System.out.println("                  Logged Out !!");
                    break;
                case 1:
                    bookTicket();
                    break;
                case 2:
                    cancelTicket();
                    break;
                case 3:
                    viewTicket();
                    break;
                case 4:
                    bookBus();
                    break;
                default:
                    System.out.println(
                            "\n                  \u001B[31;1;3mEnter number corresponding to below option.\u001B[0m\n");
                    break;
            }
        }
    }

    private void bookTicket(){

    }

    private void bookBus() throws InterruptedException {
        Graph.displayStops();
        try {
            System.out.print("                  Enter starting point : ");
            int start = sc.nextInt();
            sc.nextLine();
            System.out.print("                  Enter destination point : ");
            int end = sc.nextInt();
            int distance = -1;
            int total_price=0;
            String type = "";
            if (start > 0 && start < 34 && end > 0 && end < 34) {
                distance = Graph.findShortestPath(Graph.stops[start - 1], Graph.stops[end - 1]);
            }
            System.out.println("                                ____________________________________ ");
            System.out.println("                               |                                    |");
            System.out.println("                               |  0 -> Go Back.                     |");
            System.out.println("                               |  1 -> Local.                       |");
            System.out.println("                               |  2 -> Express.                     |");
            System.out.println("                               |  3 -> Non AC Sleeper.              |");
            System.out.println("                               |  4 -> AC Seater.                   |");
            System.out.println("                               |  5 -> Volvo Seater.                |");
            System.out.println("                               |____________________________________|");
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
                    System.out.println("                  Booking Cancelled!");
                    return;
                case 1:
                    total_price = distance * 26;
                    type = "Local";
                    break;
                case 2:
                    total_price = distance * 31;
                    type = "Express";
                    break;
                case 3:
                    total_price = distance * 33;
                    type = "Non AC Sleeper";
                    break;
                case 4:
                    total_price = distance * 55;
                    type = "AC Seater";
                    break;
                case 5:
                    total_price = distance * 75;
                    type = "Volvo Seater";
                    break;
                default:
                    System.out.println(
                            "\n                  \u001B[31;1;3mInvalid Option.\n                  Please Try Again.\u001B[0m\n");
                            return;
            }
            double total_with_gst=total_price+total_price*0.05;
            sc.nextLine();
            System.out.println("                  From        : "+Graph.stops[start-1]);
            System.out.println("                  To          : "+Graph.stops[end-1]);
            System.out.println("                  Distance    : "+distance);
            System.out.println("                  Total Price : "+total_with_gst);
            System.out.print("                  Confirm Booking? (Yes/No):");
            String ch= sc.nextLine();
            if(ch.equalsIgnoreCase("yes")){



// Print Ticket 



            }else{
                System.out.println("                  Booking Cancelled!");
            }


        } catch (Exception e) {
            System.out.println("\n                  \u001B[31;1;3mEnter your choice in numbers only.\u001B[0m\n");
            sc.nextLine();
        }
    }

    private void cancelTicket() {
        System.out.print("                  Enter Ticket Number : ");
        String t_no = sc.nextLine();
        if (tickets.contains(t_no)) {
            System.out.print("                  Confirm Ticket Cancellation ?(Yes/No) : ");
            String c = sc.nextLine();
            if (c.equalsIgnoreCase("yes")) {
                tickets.remove(t_no);
                new File(t_no + ".txt").delete();
            } else {
                System.out.println("                  Cancellatoin Process Aborted !!");
            }
        } else {
            System.out.println("\n                  \u001B[31;1;3mNo Ticket Found.\u001B[0m\n");
        }
    }

    private void viewTicket() throws IOException {
        if(tickets.isEmpty()){
            System.out.println("\n                  \u001B[31;1;3mNo Tickets Found.\u001B[0m\n");
            return;
        }
        for (String t : tickets) {
            File f = new File(t + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println("                  " + s);
            }
            br.close();
            System.out.println();
            System.out.println();
            System.out.println();
        }

    }
}
