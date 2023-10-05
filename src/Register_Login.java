import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

class Register_Login {
    static Scanner sc = new Scanner(System.in);
    static Connection con;
    static String RED = "\u001B[31;1;3m";
    static String RESET = "\u001B[0m";
    static String GREEN = "\u001B[32;1;3m";

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
                    System.out.println(
                            "\n                  "+RED+"Enter number corresponding to below option."+RESET+"\n");
                    break;
            }
        }
    }

    private static void register() throws SQLException {

        sc.nextLine();
        String phone = "";
        int flag = 3;
        while (true) {
            System.out.print("                  Enter Phone number: ");
            phone = sc.nextLine();
            if (isValidNumber(phone)) {
                PreparedStatement ps1 = con.prepareStatement("select * from users where phone = ?");
                ps1.setString(1, phone);
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    System.out
                            .println("\n                  The number " + phone
                                    + " is already registered please login using password.\n");
                    return;
                }
                break;
            } else {
                System.out.println("\n                  "+RED+"Enter valid 10 digit mobile number."+RESET+"\n");
            }
            flag--;
            if (flag == 0) {
                System.out.println("\n                  "+RED+"Registration Failed.");
                System.out.println("                  Please Try Again."+RESET+"\n");
                return;
            }
        }
        String f_name = "", l_name = "";
        while (true) {
            System.out.print("                  Enter First name: ");
            f_name = sc.nextLine();
            if (areAllAlphabets(f_name) && !f_name.isEmpty())
                break;

        }
        while (true) {
            System.out.print("                  Enter Last name: ");
            l_name = sc.nextLine();
            if (areAllAlphabets(l_name) && !l_name.isEmpty())
                break;
        }
        String gender;
        while (true) {
            System.out.print("                  Enter Gender : ");
            gender = sc.nextLine();
            if (gender.equals("Male") || gender.equals("Female")) {
                break;
            }
            System.out.println("\n                  "+RED+"Enter Valid Gender (Male / Female)."+RESET+"\n");
        }
        int age;
        while (true) {
            System.out.print("                  Age : ");
            age = sc.nextInt();
            if (age >= 16 && age < 150) {
                break;
            }
            System.out.println("\n                  "+RED+"Enter Valid Age More Than 16."+RESET+"\n");
        }
        sc.nextLine();
        String pswd = "";
        flag = 3;
        while (true) {
            System.out.print("                  Set 4 digit password : ");
            pswd = sc.nextLine();
            if (pswd.length() == 4) {
                break;
            } else {
                System.out.println("\n                  "+RED+"Enter valid 4 digit password."+RESET+"\n");
            }
            flag--;
            if (flag == 0) {
                System.out.println("\n                  "+RED+"Registration Failed.");
                System.out.println("                  Please Try Again."+RESET+"\n");
                return;
            }
        }

        PreparedStatement ps2 = con.prepareCall("insert into users values(?,?,?,?,?,?)");
        ps2.setString(1, phone);
        ps2.setString(2, f_name);
        ps2.setString(3, l_name);
        ps2.setString(4, pswd);
        ps2.setString(5, gender);
        ps2.setInt(6, age);
        int c = ps2.executeUpdate();
        if (c == 1) {
            System.out.println("\n                  "+GREEN+"You have been successfully registered.");
            System.out.println("                  Please login using username and password."+RESET+"\n");

            File file = new File("User_Data", f_name + "_" + phone.substring(7));
            file.mkdir();

        } else {
            System.out.println("\n                  "+RED+"Registration Failed !!! Try Again."+RESET+"\n");
        }
    }

    private static void login() throws Exception {
        sc.nextLine();
        String phone = "";
        int flag = 3;
        while (true) {
            System.out.print("                  Enter Phone number: ");
            phone = sc.nextLine();
            if (isValidNumber(phone)) {
                PreparedStatement ps = con.prepareStatement("select * from users where phone = ?");
                ps.setString(1, phone);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String f_name = rs.getString(2);
                    String l_name = rs.getString(3);
                    String pswd = rs.getString(4);
                    String gender = rs.getString(5);
                    int age = rs.getInt(6);
                    System.out.print("                  Enter password : ");
                    String pass = sc.nextLine();
                    if (pswd.equals(pass)) {
                        System.out.println("\n                  "+GREEN+"Logged in successfully."+RESET+"\n");
                        new Booking(phone, f_name, l_name, pswd, gender, age, con, sc);
                        return;
                    } else {
                        System.out.println("\n                  "+RED+"Incorrect password."+RED+"\n");
                    }
                } else {
                    System.out.println(
                            "\n                  "+RED+"The entered phone number has not been registered.");
                    System.out.println("                  Please Register First!"+RESET+"\n");
                    return;
                }
            } else {
                System.out.println("\n                  "+RED+"Enter valid 10 digit mobile number."+RESET+"\n");
            }
            flag--;
            if (flag == 0) {
                System.out.println("\n                  "+RED+"Login Failed.");
                System.out.println("                  Please Try Again."+RESET+"\n");
                return;
            }
        }
    }

    private static void admin_login() throws SQLException, ParseException, IOException {
        sc.nextLine();
        System.out.print("                  Enter Admin Username : ");
        String user = sc.nextLine();
        System.out.print("                  Enter password : ");
        String pswd = sc.nextLine();
        if (user.equals("admin") && pswd.equals("admin@123")) {
            System.out.println();
            new Admin(con, sc);
        } else {
            System.out.println("\n                  "+RED+"Incorrect Username or Password.");
            System.out.println("                  Please Try Again."+RESET+"\n");

        }
    }

    private static boolean isValidNumber(String phoneNumber) {
        String regex = "^[6-9]\\d{9}$";
        return phoneNumber.matches(regex);
    }

    private static boolean areAllAlphabets(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                System.out.println("\n                  "+RED+"Name must only contain letters."+RESET+" \n");
                return false;
            }
        }
        return true;
    }
}