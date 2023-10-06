import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin {
    
    static String RED = "\u001B[31;1;3m";
    static String RESET = "\u001B[0m";
    static String GREEN = "\u001B[32;1;3m";
    static Scanner sc;
    static Connection con;

    Admin(Connection con, Scanner sc) throws SQLException, ParseException, IOException {
        Admin.sc = sc;
        Admin.con = con;

        boolean flag = true;
        while (flag) {
            System.out.println();
            System.out.println("                                 ________________________________");
            System.out.println("                                |                                |");
            System.out.println("                                |  0 -> Go Back.                 |");
            System.out.println("                                |  1 -> Add Bus.                 |");
            System.out.println("                                |  2 -> Remove Bus.              |");
            System.out.println("                                |  3 -> View All Buses.          |");
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
                    // To add new bus routes.
                    addBus();
                    break;
                case 2:
                    // To remove existing bus routes.
                    removeBus();
                    break;
                case 3:
                    // To view all routes.
                    viewBuses();
                    break;
                default:
                    System.out.println(
                            "\n                  "+RED+"Enter number corresponding to below option."+RESET+"\n");
                    break;
            }
        }
    }

    private static void addBus() throws SQLException, ParseException, IOException {
        System.out.print("                  Enter Bus ID : ");
        sc.nextLine();
        String id = sc.nextLine();
        ArrayList<String> busStops = new ArrayList<>();
        ArrayList<Integer> distance = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        // Check if the bus_id already exists.
        PreparedStatement ps = con.prepareStatement("select * from buses where bus_id = ?;");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("\n                  "+RED+"Bus with " + id + " already exists."+RESET+"\n");
            return;
        }
        System.out.print("                  Enter Number of Stops in the Route : ");
        int num;
        try {
            num = sc.nextInt();
        } catch (Exception e) {
            return;
        }
        System.out.println();
        sc.nextLine();
        for (int i = 1; i <= num; i++) {
            System.out.print("                  Enter Stop " + i + " : ");
            String name = sc.nextLine();
            busStops.add(name);
            System.out.print("                  Enter Distance from starting stop : ");
            try {
                int dist = sc.nextInt();
                sc.nextLine();
                distance.add(dist);
            } catch (Exception e) {
                return;
            }
            System.out.print("                  Enter Arrival Time (HH:MM:SS) : ");
            String arrival = sc.nextLine();
            System.out.println();
            time.add(arrival);
        }
        int r = 0;
        // Checkking if data entered is valid or not and inserting it into the database.
        if (hasEmptyValue(busStops) && isAscendingOrder(distance) && isValidAndAscendingOrder(time)) {
            PreparedStatement pst = con.prepareStatement("insert into buses values(?,?,?,?,?);");
            for (int i = 0; i < busStops.size(); i++) {
                pst.setString(1, id);
                pst.setInt(2, i + 1);
                pst.setString(3, busStops.get(i));
                pst.setInt(4, distance.get(i));
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                java.util.Date date = sdf.parse(time.get(i));
                Time x = new Time(date.getTime());
                pst.setTime(5, x);
                r = pst.executeUpdate();
            }
            if (r > 0) {
                System.out.println("\n                  "+GREEN+"New Bus Route Added Successfully."+RESET+"\n");
                File file = new File(id + ".txt");
                file.createNewFile();
                seatInitialise(file);
            } else {
                System.out.println("\n                  "+RED+"There seems to be a problem adding the bus data."+RESET+"\n");
            }
        } else {
            System.out.println(
                    "\n                  "+RED+"The data you added seems to violate the conditions for addinf new routes."+RESET+"\n");
        }
    }

    // Initialise seating configuration in buses
    private static void seatInitialise(File file) throws IOException {
        FileWriter fw = new FileWriter(file);


        fw.write(" _______________________\n");
        fw.write("|                       |\n");
        fw.write("| [01] [02]   [03] [04] |\n");
        fw.write("| [05] [06]   [07] [08] |\n");
        fw.write("| [09] [10]   [11] [12] |\n");
        fw.write("| [13] [14]   [15] [16] |\n");
        fw.write("| [17] [18]   [19] [20] |\n");
        fw.write("| [21] [22]   [23] [24] |\n");
        fw.write("| [25] [26]   [27] [28] |\n");
        fw.write("| [29] [30]   [31] [32] |\n");
        fw.write("| [33] [34]   [35] [36] |\n");
        fw.write("| [37] [38]   [39] [40] |\n");
        fw.write("| [41] [42]   [43] [44] |\n");
        fw.write("| [45] [46]   [47] [48] |\n");
        fw.write("| [49] [50]   [51] [52] |\n");
        fw.write("| [53] [54]   [55] [56] |\n");
        fw.write("| [57] [58]   [59] [60] |\n");
        fw.write("|_______________________|\n");
        fw.close();
    }

    private static void removeBus() throws SQLException {
        System.out.print("                  Enter Bus ID : ");
        sc.nextLine();
        String id = sc.nextLine();
        System.out.print("                  Confirm Deletion? (" + GREEN + "Yes" + RESET + "/" + RED + "No"+ RESET + ") : ");
        String c = sc.nextLine();
        if (c.equalsIgnoreCase("no")) {
            System.out.println("\n                  "+GREEN+"Deletion Cancelled!"+RESET+"\n");
        } else {
            PreparedStatement ps = con.prepareStatement("delete from buses where Bus_id = ?;");
            ps.setString(1, id);
            int r = ps.executeUpdate();
            // check if routes is present and delete it.
            if (r > 0) {
                System.out.println("\n                  "+RED+"Bus deleted Successfully."+RESET+"\n");
                File file = new File(id + ".txt");
                file.delete();
            } else {
                System.out.println("\n                  "+RED+"No Buses with given Bus ID found."+RESET+"\n");
            }
        }
    }

    public static ArrayList<String> viewBuses() throws SQLException {
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BusMNG", "root", "");
        Statement st = con.createStatement();
        ArrayList<String> ids = new ArrayList<>();
        ResultSet rs = st.executeQuery("select Bus_id from buses group by Bus_id;");
        while (rs.next()) {
            ids.add(rs.getString(1));
        }
        // View all bus routes.
        for (int i = 0; i < ids.size(); i++) {
            PreparedStatement ps = con.prepareStatement("select * from buses where Bus_id = ?;");
            ps.setString(1, ids.get(i));
            ResultSet rs1 = ps.executeQuery();
            System.out.println("                  Bus ID : " + ids.get(i));
            System.out.print("                  Stops : [ ");
            while (rs1.next()) {
                System.out.print(rs1.getString(3) + ", ");
            }
            System.out.println("\b\b ]\n\n");
        }
        return ids;
    }

    private static boolean isValidAndAscendingOrder(List<String> list) {
        // Check if the list is not empty
        if (list.isEmpty()) {
            return false;
        }

        // Define the expected time format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Check if all times are valid and in ascending order
        for (int i = 0; i < list.size() - 1; i++) {
            String currentTimeStr = list.get(i);
            String nextTimeStr = list.get(i + 1);

            try {
                // Parse the input time strings
                LocalTime currentTime = LocalTime.parse(currentTimeStr, timeFormatter);
                LocalTime nextTime = LocalTime.parse(nextTimeStr, timeFormatter);

                // Check if the times are in ascending order
                if (currentTime.isAfter(nextTime)) {
                    return false;
                }
            } catch (Exception e) {
                // Exception indicates invalid time format
                return false;
            }
        }

        return true; // All times are valid and in ascending order
    }

    private static boolean hasEmptyValue(List<String> list) {
        for (String str : list) {
            if (str == null || str.trim().isEmpty()) {
                return false; // Found an empty or null string
            }
        }
        return true; // No empty or null string found
    }

    private static boolean isAscendingOrder(List<Integer> list) {
        // Check if the list is not empty and the first element is 0
        if (list.isEmpty() || list.get(0) != 0) {
            return false;
        }

        // Check if the remaining elements are in ascending order
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) <= list.get(i - 1)) {
                return false;
            }
        }

        return true;
    }
}
