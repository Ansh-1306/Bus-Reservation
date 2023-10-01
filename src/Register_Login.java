import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Register_Login {
    static Scanner sc = new Scanner(System.in);
    static Connection con;

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BusMNG", "root", "");

        while (true) {
            System.out.println("                             _________________________________________");
            System.out.println("                            |                                         |");
            System.out.println("                            |  0 -> Exit.                             |");
            System.out.println("                            |  1 -> New here? Register.               |");
            System.out.println("                            |  2 -> Already have an account, Login.   |");
            System.out.println("                            |  3 -> Admin Login.                      |");
            System.out.println("                            |_________________________________________|");
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
                    System.exit(0);
                    break;
                case 1:
                    register();
                    break;

                case 2:
                    login();
                    break;
                case 3:
                    admin_login();
                    break;
                default:
                    System.out.println("                  Enter number corresponding to below option.");
                    break;
            }
        }
    }

    public static void register() throws SQLException {

        sc.nextLine();
        String phone = "";
        int flag2 = 3;
        while (flag2 > 0) {
            System.out.print("                  Enter Phone number : ");
            phone = sc.nextLine().trim();
            if (phone.length() == 10 && phone.charAt(0) <= '9' && phone.charAt(0) >= '6') {
                int count = 0;
                for (int i = 0; i < phone.length(); i++) {
                    if (Character.isDigit(phone.charAt(i))) {
                        count++;
                    }
                }
                if (count == 10) {
                    break;
                } else {
                    System.out.println("                  Number must only conatins digits.\n");
                }
            } else {
                System.out.println("                  Enter valid 10 digit phone number.\n");
            }
            flag2--;
        }
        if (flag2 == 0) {
            System.out.println("                  Registration Failed.");
            System.out.println("                  Please Try Again.");
            return;
        }
        PreparedStatement ps1 = con.prepareStatement("select * from users where phone = ?");
        ps1.setString(1, phone);
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) {
            System.out
                    .println("                  The number " + phone + " is already registered please login using password.");
        } else {
            System.out.print("                  Enter First Name : ");
            String f_name = sc.nextLine().trim();
            System.out.print("                  Enter Last Name : ");
            String l_name = sc.nextLine().trim();
            String gender;
            while (true) {
                System.out.print("                  Enter Gender : ");
                gender = sc.nextLine();
                if (gender.equals("Male") || gender.equals("Female")) {
                    break;
                } else {
                    System.out.println("                  Enter Valid Gender (Male / Female)");
                }
            }
            String pswd = "";
            int flag = 3;
            while (flag > 0) {
                System.out.print("                  Enter 4 digit password : ");
                pswd = sc.nextLine();
                if (pswd.length() == 4) {
                    break;
                } else {
                    System.out.println("                  Enter valid 4 digit password.\n");
                }
                flag--;
            }
            if (flag == 0) {
                System.out.println("                  Registration Failed.");
                System.out.println("                  Please Try Again.");
                return;
            }
            PreparedStatement ps2 = con.prepareCall("insert into users values(?,?,?,?,?)");
            ps2.setString(1, phone);
            ps2.setString(2, f_name);
            ps2.setString(3, l_name);
            ps2.setString(4, pswd);
            ps2.setString(5, gender);
            int c = ps2.executeUpdate();
            if (c == 1) {
                System.out.println("\n                  You have been successfully registered.");
                System.out.println("                  Please login using username and password.");

                File file = new File("User_Data", f_name + "_" + phone.substring(7));
                file.mkdir();

            } else {
                System.out.println("                  Registration Failed !!! Try Again.");
            }
        }
    }

    public static void login() throws SQLException {
        sc.nextLine();
        String phone = "";
        int flag2 = 3;
        while (flag2 > 0) {
            System.out.print("                  Enter Phone number : ");
            phone = sc.nextLine();
            if (phone.length() == 10 && phone.charAt(0) <= '9' && phone.charAt(0) >= '6') {
                int count = 0;
                for (int i = 0; i < phone.length(); i++) {
                    if (Character.isDigit(phone.charAt(i))) {
                        count++;
                    }
                }
                if (count == 10) {
                    break;
                } else {
                    System.out.println("                  Number must only conatins digits.\n");
                }
            } else {
                System.out.println("                  Enter valid 10 digit phone number.\n");
            }
            flag2--;
        }
        if (flag2 == 0) {
            System.out.println("                  Login Failed.");
            System.out.println("                  Please Try Again.");
            return;
        }
        PreparedStatement ps1 = con.prepareStatement("select * from users where phone = " + phone);
        ResultSet rs = ps1.executeQuery();
        if (rs.next()) {
            String f_name = rs.getString(2);
            String l_name = rs.getString(3);
            String pswd = rs.getString(4);
            String gender = rs.getString(5);
            System.out.print("                  Enter password : ");
            String pass = sc.nextLine();
            if (pswd.equals(pass)) {
                System.out.println("\n                  Logged in successfully.");
                Booking b = new Booking(phone, f_name, l_name, pswd, gender);
                b.optionMenu();
            } else {
                System.out.println("                  Incorrect password.");
            }
        } else {
            System.out.println("                  The entered phone number has not been registered.\n");
            System.out.println("                  Please Register First!");
        }
    }

    public static void admin_login() {
        sc.nextLine();
        System.out.print("                  Enter Admin Username : ");
        String user = sc.nextLine();
        System.out.print("                  Enter password : ");
        String pswd = sc.nextLine();
        if (user.equals("admin") && pswd.equals("admin@123")) {
            System.out.println();
            Booking.admin();
        } else {
            System.out.println("                  Incorrect Username or Password");
        }
    }
}