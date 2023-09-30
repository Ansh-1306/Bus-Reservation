import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class Register_Login {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BusMNG", "root", "");

        while (true) {
            System.out.println("\n0 -> Exit.");
            System.out.println("1 -> New here? Register.");
            System.out.println("2 -> Already have an account, Login.");
            System.out.println("3 -> Admin Login.");
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
                    System.exit(0);
                    break;
                case 1: {
                    sc.nextLine();
                    String phone = "";
                    int flag2 = 3;
                    while (flag2 > 0) {
                        System.out.print("Enter Phone number : ");
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
                                System.out.println("Number must only conatins digits.\n");
                            }
                        } else {
                            System.out.println("Enter valid 10 digit phone number.\n");
                        }
                        flag2--;
                    }
                    if (flag2 == 0) {
                        System.out.println("Registration Failed.");
                        System.out.println("Please Try Again.");
                        break;
                    }
                    PreparedStatement ps1 = con.prepareStatement("select * from users where phone = ?");
                    ps1.setString(1, phone);
                    ResultSet rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        System.out
                                .println("The number " + phone + " is already registered please login using password.");
                    } else {
                        System.out.print("Enter First Name : ");
                        String f_name = sc.nextLine();
                        System.out.print("Enter Last Name : ");
                        String l_name = sc.nextLine();
                        String pswd = "";
                        int flag = 3;
                        while (flag > 0) {
                            System.out.print("Enter 4 digit password : ");
                            pswd = sc.nextLine();
                            if (pswd.length() == 4) {
                                break;
                            } else {
                                System.out.println("Enter valid 4 digit password.\n");
                            }
                            flag--;
                        }
                        if (flag == 0) {
                            System.out.println("Registration Failed.");
                            System.out.println("Please Try Again.");
                            break;
                        }
                        PreparedStatement ps2 = con.prepareCall("insert into users values(?,?,?,?)");
                        ps2.setString(1, phone);
                        ps2.setString(2, f_name);
                        ps2.setString(3, l_name);
                        ps2.setString(4, pswd);
                        int c = ps2.executeUpdate();
                        if (c == 1) {
                            System.out.println("\nYou have been successfully registered.");
                            System.out.println("Please login using username and password.");
                            File file = new File(f_name+"_"+phone.substring(7));
                            file.mkdir();
                        } else {
                            System.out.println("Registration Failed !!! Try Again.");
                        }
                    }
                    break;
                }
                case 2: {
                    sc.nextLine();
                    String phone = "";
                    int flag2 = 3;
                    while (flag2 > 0) {
                        System.out.print("Enter Phone number : ");
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
                                System.out.println("Number must only conatins digits.\n");
                            }
                        } else {
                            System.out.println("Enter valid 10 digit phone number.\n");
                        }
                        flag2--;
                    }
                    if (flag2 == 0) {
                        System.out.println("Login Failed.");
                        System.out.println("Please Try Again.");
                        break;
                    }
                    PreparedStatement ps1 = con.prepareStatement("select * from users where phone = " + phone);
                    ResultSet rs = ps1.executeQuery();
                    if (rs.next()) {
                        String f_name = rs.getString(2);
                        String l_name = rs.getString(3);
                        String pswd = rs.getString(4);
                        System.out.print("Enter password : ");
                        String pass = sc.nextLine();
                        if (pswd.equals(pass)) {
                            System.out.println("\nLogged in successfully.");
                            Booking b = new Booking(phone, f_name, l_name, pswd);
                            b.optionMenu();
                        } else {
                            System.out.println("Incorrect password.");
                        }
                    } else {
                        System.out.println("The entered phone number has not been registered.\n");
                        System.out.println("Please Register First!");
                    }
                    break;
                }
                case 3: {
                    sc.nextLine();
                    System.out.print("Enter Admin Username : ");
                    String user = sc.nextLine();
                    System.out.print("Enter password : ");
                    String pswd = sc.nextLine();
                    if (user.equals("admin") && pswd.equals("admin@123")) {
                        System.out.println();
                        Booking.admin();
                    } else {
                        System.out.println("Incorrect Username or Password");
                    }
                    break;
                }
                default:
                    System.out.println("Enter number corresponding to below option.");
                    break;
            }
        }
    }
}