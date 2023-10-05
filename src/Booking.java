import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Booking {
    String RED = "\u001B[31;1;3m";
    String RESET = "\u001B[0m";
    String GREEN = "\u001B[32;1;3m";
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

    public Booking(String phone, String f_name, String l_name, String pswd, String gender, int age, Connection con,
            Scanner sc) throws Exception {
        this.phone = phone;
        this.f_name = f_name;
        this.l_name = l_name;
        this.pswd = pswd;
        this.gender = gender;
        this.age = age;
        this.con = con;
        this.sc = sc;
        dir = new File("User_Data", f_name + "_" + phone.substring(7));
        tickets = new ArrayList<>(Arrays.asList(dir.list()));
        network = new Graph();
        optionMenu();
    }

    private void optionMenu() throws InterruptedException, IOException, SQLException {
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
                            "\n                  " + RED + "Enter number corresponding to below option." + RESET
                                    + "\n");
                    break;
            }
        }
    }

    private void bookTicket() throws SQLException, IOException {
        ArrayList<String> al1 = new ArrayList<>();
        ArrayList<String> al2 = new ArrayList<>();
        sc.nextLine();
        System.out.print("                  Enter Source : ");
        String start = sc.nextLine();
        System.out.print("                  Enter Destination : ");
        String end = sc.nextLine();
        PreparedStatement ps = con.prepareStatement("select Bus_id from buses where Stop_name = ?;");
        ps.setString(1, start);
        ResultSet rs1 = ps.executeQuery();
        while (rs1.next()) {
            al1.add(rs1.getString(1));
        }
        ps.setString(1, end);
        ResultSet rs2 = ps.executeQuery();
        while (rs2.next()) {
            al2.add(rs2.getString(1));
        }
        al1.retainAll(al2);
        if (al1.isEmpty()) {
            System.out.println("                  No Direct Bus From " + start + " to " + end);
            System.out.println();
            System.out.println();
            ArrayList<String> ids = Admin.viewBuses();
            System.out.print("                  Enter Bus Id : ");
            String id = sc.nextLine();
            if (ids.contains(id)) {
                System.out.print("                  Enter Source : ");
                start = sc.nextLine();
                System.out.print("                  Enter Destination : ");
                end = sc.nextLine();
                String seatCon = fileToString(id);
                System.out.print("                  Enter Number Of Seats : ");
                int seats = sc.nextInt();
                for (int i = 1; i <= seats; i++) {
                    System.out.print("                  Enter Seat " + i + " : ");
                    String s = sc.nextLine();
                    if (s.length() == 1)
                        s = "0" + s;
                    if (s.isEmpty()) {
                        i--;
                        continue;
                    }
                    if (seatCon.indexOf(s) != -1) {
                        seatCon = seatCon.replaceFirst(s, "XX");
                    } else {
                        System.out.println("                  Enter Valid Seat Number.");
                        i--;
                    }
                }
                stringToFile(seatCon, id);
                double x = 0, y = 0;
                PreparedStatement ps1 = con
                        .prepareStatement("select distance from buses where Bus_id = ? and Stop_name = ?;");
                ps1.setString(1, id);
                ps1.setString(2, start);
                ResultSet rs = ps1.executeQuery();
                while (rs.next()) {
                    x = rs.getInt(1);
                }
                ps1.setString(1, id);
                ps1.setString(2, end);
                while (rs.next()) {
                    y = rs.getInt(1);
                }
                x = Math.abs(x - y);
                double total_price =seats * x * 1.25;

                String ch = "";
                while (!(ch.equalsIgnoreCase("yes") || ch.equalsIgnoreCase("no"))) {
                    System.out.println();
                    System.out.print("                  Confirm Booking? (" + GREEN + "Yes" + RESET + "/" + RED + "No"
                            + RESET + ") : ");
                    ch = sc.nextLine();
                    if (ch.equalsIgnoreCase("yes")) {
                        if (Payment.pay(total_price, sc, pswd)) {
                            System.out.println("\n                  " + GREEN
                                    + "Payment Successful.\n                  Bus Booked Successfully." + RESET + "\n");
                            printTicket();
                        } else {
                            System.out.println("\n                  " + RED
                                    + "Payment Failure.\n                  Booking Cancelled." + RESET + "\n");
                        }
                    } else {
                        System.out.println("\n                  " + RED + "Booking Cancelled!" + RESET + "\n");
                    }
                }
            }
        } else {
            for (int i = 0; i < al1.size(); i++) {
                System.out.println();
                System.out.println("                  Bus ID : " + al1.get(i));
                System.out.print("                  Stops : [ ");
                ps = con.prepareStatement("select * from buses where Bus_id = ?");
                ps.setString(1, al1.get(i));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.print(rs.getString(3) + ", ");
                }
                System.out.println("\b\b ]\n\n");
            }
            System.out.print("                  Enter Bus Id : ");
            String id = sc.nextLine();
            if (al1.contains(id)) {
                String seatCon = fileToString(id);
                System.out.print("                  Enter Number Of Seats : ");
                int seats = sc.nextInt();
                for (int i = 1; i <= seats; i++) {
                    System.out.print("                  Enter Seat " + i + " : ");
                    String s = sc.nextLine();
                    if (s.length() == 1)
                        s = "0" + s;
                    if (s.isEmpty()) {
                        i--;
                        continue;
                    }
                    if (seatCon.indexOf(s) != -1) {
                        seatCon = seatCon.replaceFirst(s, "XX");
                    } else {
                        System.out.println("                  Enter Valid Seat Number.");
                        i--;
                    }
                }
                stringToFile(seatCon, id);
                double x = 0, y = 0;
                PreparedStatement ps1 = con
                        .prepareStatement("select distance from buses where Bus_id = ? and Stop_name = ?;");
                ps1.setString(1, id);
                ps1.setString(2, start);
                ResultSet rs = ps1.executeQuery();
                while (rs.next()) {
                    x = rs.getInt(1);
                }
                ps1.setString(1, id);
                ps1.setString(2, end);
                while (rs.next()) {
                    y = rs.getInt(1);
                }
                x = Math.abs(x - y);
                double total_price =seats* x * 1.25;
                String ch = "";
                while (!(ch.equalsIgnoreCase("yes") || ch.equalsIgnoreCase("no"))) {
                    System.out.println();
                    System.out.print("                  Confirm Booking? (" + GREEN + "Yes" + RESET + "/" + RED + "No"
                            + RESET + ") : ");
                    ch = sc.nextLine();
                    if (ch.equalsIgnoreCase("yes")) {
                        if (Payment.pay(total_price, sc, pswd)) {
                            System.out.println("\n                  " + GREEN
                                    + "Payment Successful.\n                  Bus Booked Successfully." + RESET + "\n");
                            printTicket();
                        } else {
                            System.out.println("\n                  " + RED
                                    + "Payment Failure.\n                  Booking Cancelled." + RESET + "\n");
                        }
                    } else {
                        System.out.println("\n                  " + RED + "Booking Cancelled!" + RESET + "\n");
                    }
                }
            }
        }
    }

    private String fileToString(String id) throws IOException {
        String seatCon = "";
        FileReader fr = new FileReader(id + ".txt");
        BufferedReader br = new BufferedReader(fr);
        String s;
        while ((s = br.readLine()) != null) {
            seatCon = seatCon + s + "\n";
        }
        return seatCon;
    }

    private void stringToFile(String seatCon, String id) throws IOException {
        FileWriter fw = new FileWriter(new File(id + ".txt"));
        fw.write(seatCon);
        fw.close();
    }

    private void bookBus() throws InterruptedException {
        Graph.displayStops();
        try {
            System.out.print("                  Enter Source : ");
            int start = sc.nextInt();
            System.out.print("                  Enter Destination : ");
            int end = sc.nextInt();
            int distance = -1;
            int total_price = 0;
            String type = "";
            if (start > 0 && start < 34 && end > 0 && end < 34) {
                distance = Graph.findShortestPath(Graph.stops[start - 1], Graph.stops[end - 1]);
            } else {
                System.out.println("\n                  " + RED + "Enter Valid Stop Number!" + RESET + "\n");
                return;
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
                    System.out.println("\n                  " + RED + "Booking Cancelled!" + RESET + "\n");
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
                            "\n                  " + RED + "Invalid Option.\n                  Please Try Again."
                                    + RESET + "\n");
                    return;
            }
            double total_with_gst = Math.ceil(total_price + total_price * 0.05);
            sc.nextLine();
            System.out.println();
            System.out.println("                  From          : " + Graph.stops[start - 1]);
            System.out.println("                  To            : " + Graph.stops[end - 1]);
            System.out.println("                  Distance      : " + distance);
            System.out.println("                  Type          : " + type);
            System.out.println("                  Total Price   : " + total_with_gst + " Rs");
            String ch = "";
            while (!(ch.equalsIgnoreCase("yes") || ch.equalsIgnoreCase("no"))) {
                System.out.println();
                System.out.print("                  Confirm Booking? (" + GREEN + "Yes" + RESET + "/" + RED + "No"
                        + RESET + ") : ");
                ch = sc.nextLine();
                if (ch.equalsIgnoreCase("yes")) {
                    if (Payment.pay(total_price, sc, pswd)) {
                        System.out.println("\n                  " + GREEN
                                + "Payment Successful.\n                  Bus Booked Successfully." + RESET + "\n");
                        printTicket();
                    } else {
                        System.out.println("\n                  " + RED
                                + "Payment Failure.\n                  Booking Cancelled." + RESET + "\n");
                    }
                } else {
                    System.out.println("\n                  " + RED + "Booking Cancelled!" + RESET + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("\n                  " + RED + "Enter your choice in numbers only." + RESET + "\n");
            sc.nextLine();
        }
    }

    private void cancelTicket() {
        System.out.print("                  Enter Ticket Number : ");
        String t_no = sc.nextLine();
        if (tickets.contains(t_no)) {
            String ch = "";
            while (!(ch.equalsIgnoreCase("yes") || ch.equalsIgnoreCase("no"))) {
                System.out.print("                  Confirm Ticket Cancellation ?(" + RED + "Yes" + RESET + "/" + GREEN
                        + "No" + RESET + ") : ");
                ch = sc.nextLine();
                if (ch.equalsIgnoreCase("yes")) {
                    tickets.remove(t_no);
                    new File(t_no + ".txt").delete();
                } else {
                    System.out
                            .println("\n                  " + RED + "Cancellation Process Aborted !!" + RESET + "\nga");
                }
            }
        } else {
            System.out.println("\n                  " + RED + "No Ticket Found." + RESET + "\n");
        }
    }

    private void viewTicket() throws IOException {
        if (tickets.isEmpty()) {
            System.out.println("\n                  " + RED + "No Tickets Found." + RESET + "\n");
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

    private void printTicket() {

    }
}
