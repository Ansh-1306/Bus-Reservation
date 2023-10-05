import java.util.Scanner;

public class Payment {
    public static boolean pay(double price, Scanner sc, String pswd) {

        System.out.println("                  Amount : " + price);
        int flag = 3;
        while (flag > 0) {
            System.out.print("                  Enter password to Pay: ");
            String pass = sc.nextLine();
            if (pass.equals(pswd))
                return true;
            flag--;
        }
        return false;
    }
}
