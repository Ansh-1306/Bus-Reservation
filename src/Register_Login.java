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
                    System.out.println(
                            "\n                  \u001B[31;1;3mEnter number corresponding to below option.\u001B[0m\n");
                    break;
            }
        }
    }

    public static void register() throws SQLException {

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
                System.out.println("\n                  \u001B[31;1;3mEnter valid 10 digit mobile number.\u001B[0m\n");
            }
            flag--;
            if (flag == 0) {
                System.out.println("\n                  \u001B[31;1;3mRegistration Failed.");
                System.out.println("                  Please Try Again.\u001B[0m\n");
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
            System.out.println("\n                  \u001B[31;1;3mEnter Valid Gender (Male / Female).\u001B[0m\n");
        }
        int age;
        while (true) {
            System.out.print("                  Age : ");
            age = sc.nextInt();
            if (age >= 16 && age < 150) {
                break;
            }
            System.out.println("\n                  \u001B[31;1;3mEnter Valid Age More Than 16.\u001B[0m\n");
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
                System.out.println("\n                  \u001B[31;1;3mEnter valid 4 digit password.\u001B[0m\n");
            }
            flag--;
            if (flag == 0) {
                System.out.println("\n                  \u001B[31;1;3mRegistration Failed.");
                System.out.println("                  Please Try Again.\u001B[0m\n");
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
            System.out.println("\n                  \u001B[32;3mYou have been successfully registered.");
            System.out.println("                  Please login using username and password.\u001B[0m\n");

            File file = new File("User_Data", f_name + "_" + phone.substring(7));
            file.mkdir();

        } else {
            System.out.println("\n                  \u001B[31;1;3mRegistration Failed !!! Try Again.\u001B[0m\n");
        }
    }

    public static void login() throws Exception {
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
                        System.out.println("\n                  \u001B[32;3mLogged in successfully.\u001B[0m\n");
                        Booking b = new Booking(phone, f_name, l_name, pswd, gender, age, con,sc);
                        return;
                    } else {
                        System.out.println("\n                  \u001B[31;1;3mIncorrect password.\\u001B[31;1;3m\n");
                    }
                } else {
                    System.out.println(
                            "\n                  \u001B[31;1;3mThe entered phone number has not been registered.");
                    System.out.println("                  Please Register First!\u001B[0m\n");
                    return;
                }
            } else {
                System.out.println("\n                  \u001B[31;1;3mEnter valid 10 digit mobile number.\u001B[0m\n");
            }
            flag--;
            if (flag == 0) {
                System.out.println("\n                  \u001B[31;1;3mLogin Failed.");
                System.out.println("                  Please Try Again.\u001B[0m\n");
            }
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
            new Admin(con,sc);
        } else {
            System.out.println("\n                  \u001B[31;1;3mIncorrect Username or Password.");
            System.out.println("                  Please Try Again.\u001B[0m\n");

        }
    }

    private static boolean isValidNumber(String phoneNumber) {
        String regex = "^[6-9]\\d{9}$";
        return phoneNumber.matches(regex);
    }

    private static boolean areAllAlphabets(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                System.out.println("\n                  \u001B[31;1;3mName must only contain letters.\u001B[0m \n");
                return false;
            }
        }
        return true;
    }
}