// package Bill;

import java.io.Console;
import java.util.*;

abstract class ElectricityBills {

    protected static final String GST_NO = "GST123456789";
    // protected static final Random random = new Random();
    String region;
    String type;
    double units;
    double billAmount;

    public ElectricityBills(String region, String type, double units) {
        this.region = region;
        this.type = type;
        this.units = units;
    }

    public abstract double calculateBill();

    public void printBill() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("\n==================================");
        System.out.println("        ⚡ ELECTRICITY BILL ⚡       ");
        System.out.println("==================================");
        Thread.sleep(3500); // Adding delay for better effect
        System.out.println(" Date: " + new Date());
        System.out.println(" GST No: " + GST_NO);
        System.out.println("Customer No: " + (Math.random() * (90000 - 10000 + 1) + 10000));
        System.out.println(" Region: " + capitalize(region));
        System.out.println(" Type: " + capitalize(type));
        System.out.println(" Consumed Units: " + units);
        System.out.println(" Fixed Charges: " + 300);
        System.out.println(" Total Amount: Rs. " + (billAmount + 300));
        System.out.println("==================================\n");
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

class Rural extends ElectricityBills {

    public Rural(String type, double units) {
        super("Rural", type, units);
    }

    @Override
    public double calculateBill() {
        double govtSubsidy = (units > 0 && units <= 100) ? 500 : 0;
        if (type.equals("domestic")) {
            if (units <= 100) {
                billAmount = units * 8.50 - govtSubsidy;
            } else if (units <= 200) {
                billAmount = units * 9.00;
            } else {
                billAmount = units * 6.00;
            }
        } else if (type.equals("commercial")) {
            if (units <= 100) {
                billAmount = units * 10.00;
            } else if (units <= 200) {
                billAmount = units * 16.50;
            } else {
                billAmount = units * 19.00;
            }
        }
        return billAmount;
    }
}

class Urban extends ElectricityBills {

    public Urban(String type, double units) {
        super("Urban", type, units);
    }

    @Override
    public double calculateBill() {
        double govtSubsidy = (units > 0 && units <= 100) ? 500 : 0;
        if (type.equals("domestic")) {
            if (units <= 100) {
                billAmount = units * 3.00 - govtSubsidy;
            } else if (units <= 200) {
                billAmount = units * 8.00;
            } else {
                billAmount = units * 12.00;
            }
        } else if (type.equals("commercial")) {
            if (units <= 100) {
                billAmount = units * 10.00;
            } else if (units <= 200) {
                billAmount = units * 17.50;
            } else {
                billAmount = units * 18.00;
            }
        }
        return billAmount;
    }
}

public class Test1 {

    private static final String LOGIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Console console = System.console();
            if (console == null) {
                System.out.println("⚠ Console not available. Running in IDE?");
                return;
            }

            System.out.print(" Enter login password: ");
            char[] passwordChars = console.readPassword();
            String password = new String(passwordChars);

            if (!password.equals(LOGIN_PASSWORD)) {
                throw new SecurityException(" Incorrect password! Access denied.");
            }

            System.out.print(" Enter region (Urban/Rural): ");
            String region = sc.nextLine().trim().toLowerCase();
            System.out.print(" Enter type (Domestic/Commercial): ");
            String type = sc.nextLine().trim().toLowerCase();
            System.out.print(" Enter consumed units: ");
            double units = sc.nextDouble();

            ElectricityBills bill;
            if (region.equals("rural")) {
                bill = new Rural(type, units);
            } else if (region.equals("urban")) {
                bill = new Urban(type, units);
            } else {
                throw new IllegalArgumentException(" Invalid region!");
            }

            double totalAmount = bill.calculateBill();
            bill.printBill();
            askPaymentMode(sc, totalAmount);

        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter numbers where required.");
        } catch (Exception e) {
            System.out.println("⚠ Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    public static void askPaymentMode(Scanner sc, double amount) throws InterruptedException {
        System.out.println(" Would you like to pay online or offline?");
        System.out.println("1️ . Online");
        System.out.println("2️ . Offline");
        System.out.print("➡ Enter choice: ");
        int mode = sc.nextInt();

        if (mode == 1) {
            processPayment(sc, amount);
        } else {
            System.out.println("\n Please visit our office to pay:");
            System.out.println(" 23, Circuit House, Kalani Nagar, Near Airport, Indore");
        }
    }

    public static void processPayment(Scanner sc, double amount) throws InterruptedException {
        System.out.println("\n Select payment method:");
        System.out.println("1️ . UPI");
        System.out.println("2️ . Card");
        System.out.print("➡ Enter choice: ");
        int choice = sc.nextInt();

        System.out.print(" Enter amount to pay: ");
        double paidAmount = sc.nextDouble();

        Thread.sleep(1500); // Adding slight delay for better user experience

        if (paidAmount < amount) {
            System.out.println("⚠ Warning! Pending amount: Rs. " + (amount - paidAmount));
            System.out.println(" If you do not pay soon, your electricity will be cut!\n");
        } else {
            System.out.println(" Payment successful! Electricity bill paid in full.\n");
        }
    }
}
