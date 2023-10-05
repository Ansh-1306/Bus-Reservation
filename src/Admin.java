import java.sql.Connection;
import java.util.Scanner;

public class Admin {
    Scanner sc;
    Connection con;
    Graph network;
    Admin(Connection con,Scanner sc){
        this.sc=sc;
        this.con=con;
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
                    
                    break;
                case 2:

                    break;
                case 3:

                    break;
                default:
                    System.out.println(
                            "\n                  \u001B[31;1;3mEnter number corresponding to below option.\u001B[0m\n");
                    break;
            }
        }
    }
}
