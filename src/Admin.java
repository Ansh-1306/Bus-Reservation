import java.sql.Connection;
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
    Scanner sc;
    Connection con;
    Graph network;

    Admin(Connection con, Scanner sc) throws SQLException, ParseException {
        this.sc = sc;
        this.con = con;
        network = new Graph();

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
                    addBus();
                    break;
                case 2:
                    removeBus();
                    break;
                case 3:
                    viewBuses();
                    break;
                default:
                    System.out.println(
                            "\n                  \u001B[31;1;3mEnter number corresponding to below option.\u001B[0m\n");
                    break;
            }
        }
    }

    public void addBus() throws SQLException, ParseException {
        System.out.print("                  Enter Bus ID : ");
        sc.nextLine();
        String id = sc.nextLine();
        ArrayList<String> busStops = new ArrayList<>();
        ArrayList<Integer> distance = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();

        PreparedStatement ps = con.prepareStatement("select * from buses where bus_id = ?;");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("                  Bus with " + id + " already exists.");
            return;
        }
        System.out.print("                Enter Number of Stops in the Route : ");
        int num = sc.nextInt();
        sc.nextLine();
        for (int i = 1; i <= num; i++) {
            System.out.print("                  Enter Stop " + i + " : ");
            String name = sc.nextLine();
            busStops.add(name);
            System.out.print("                  Enter Distance from starting stop : ");
            int dist = sc.nextInt();
            distance.add(dist);
            sc.nextLine();
            System.out.print("                  Enter Arrival Time (HH:MM:SS) : ");
            String arrival = sc.nextLine();
            time.add(arrival);
        }
        int r = 0;
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
                System.out.println("                  New Bus Route Added Successfully.");
            } else {
                System.out.println("                  There seems to be a problem adding the bus data.");
            }
        } else {
            System.out.println(
                    "                  The data you added seems to violate the conditions for addinf new routes.");
        }

    }

    public void removeBus() throws SQLException {
        System.out.print("                  Enter Bus ID : ");
        sc.nextLine();
        String id = sc.nextLine();
        PreparedStatement ps = con.prepareStatement("delete from buses where Bus_id = ?;");
        ps.setString(1, id);
        int r = ps.executeUpdate();
        if (r > 0) {
            System.out.println("                  Bus deleted Successfully.");
        } else {
            System.out.println("                  No Buses with given Bus ID found.");
        }

    }

    public void viewBuses() throws SQLException {
        Statement st = con.createStatement();
        ArrayList<String> ids = new ArrayList<>();
        ResultSet rs = st.executeQuery("select Bus_id from buses group by Bus_id;");
        while(rs.next()){
            ids.add(rs.getString(1));
        }
        for(int i=0;i<ids.size();i++){
            PreparedStatement ps = con.prepareStatement("select * from buses where Bus_id = ?;");
            ps.setString(1, ids.get(i));
            ResultSet rs1 = ps.executeQuery();
            System.out.println("                  Bus ID : "+ids.get(i));
            System.out.print("                Stops : [ ");
            while(rs1.next()){
                System.out.print(rs1.getString(3)+", ");
            }
            System.out.println("]\n\n");
        }
    }

    public static boolean isValidAndAscendingOrder(List<String> list) {
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

    public static boolean hasEmptyValue(List<String> list) {
        for (String str : list) {
            if (str == null || str.trim().isEmpty()) {
                return false; // Found an empty or null string
            }
        }
        return true; // No empty or null string found
    }

    public static boolean isAscendingOrder(List<Integer> list) {
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
