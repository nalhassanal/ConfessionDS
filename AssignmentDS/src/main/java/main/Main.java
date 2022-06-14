package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String stop;
        confession conf = new confession();
        do {
            conf.mainDisplay(); // akan ada condition
            System.out.println("Do you want to continue? ");
            stop = input.nextLine();
        } while (!stop.equalsIgnoreCase("no"));
        System.out.println("Thank you for using our service");
        // which will break the loop
        // ex. spam filer maybe?
    }

}
