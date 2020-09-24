package hard.budget;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static double balance = 0.0;
    private static double sum = 0.0;
    private static ArrayList<Purchase> list = new ArrayList<>();

    public static void main(String[] args) {
        int decision = 1;
        while (decision != 0) {
            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "0) Exit");
            decision = SCANNER.nextInt();
            System.out.println();
            switch (decision) {
                case 1:
                    income();
                    break;
                case 2:
                    purchase();
                    break;
                case 3:
                    list();
                    break;
                case 4:
                    balance();
                    break;
                default:
                    decision = 0;
                    System.out.println("Bye!");
            }
            System.out.println();
        }
    }

    private static void income() {
        System.out.println("Enter income: ");
        balance += SCANNER.nextDouble();
        System.out.println("Income was added!");
    }

    private static void purchase() {
        Purchase.Categories category = type();
        if (category == null) {
            return;
        }
        System.out.println("Enter purchase name: ");
        SCANNER.nextLine();
        String name = SCANNER.nextLine();
        System.out.println("Enter its price: ");
        double price = SCANNER.nextDouble();
        sum += price;
        balance -= balance == 0.0 ? 0.0 : price;
        System.out.println("Purchase was added!");
        list.add(new Purchase(name, price, category));
    }

    private static void list() {
        if (list.isEmpty()) {
            System.out.println("Purchase list is empty!");
            return;
        }
        Purchase.Categories category = typeWithAll();
        if (category == null) {
            return;
        }
        System.out.println(category.description + ":");
        if (category == Purchase.Categories.ALL) {
            for (Purchase p : list) {
                System.out.println(p);
            }
        }
        else {
            int counter = 0;
            for (Purchase p : list) {
                if (p.getCategory() == category) {
                    System.out.println(p);
                    counter++;
                }
            }
            if (counter == 0) {
                System.out.println("Purchase list is empty!");
            }
        }
        System.out.println("Total sum: $" + sum);
        System.out.println();
        list();
    }

    private static Purchase.Categories type() {
        System.out.println("Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back");
        switch (SCANNER.nextInt()) {
            case 1:
                return Purchase.Categories.FOOD;
            case 2:
                return Purchase.Categories.CLOTHES;
            case 3:
                return Purchase.Categories.ENTERTAINMENT;
            case 4:
                return Purchase.Categories.OTHER;
        }
        return null;
    }

    private static Purchase.Categories typeWithAll() {
        System.out.println("Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "6) Back");
        switch (SCANNER.nextInt()) {
            case 1:
                return Purchase.Categories.FOOD;
            case 2:
                return Purchase.Categories.CLOTHES;
            case 3:
                return Purchase.Categories.ENTERTAINMENT;
            case 4:
                return Purchase.Categories.OTHER;
            case 5:
                return Purchase.Categories.ALL;
        }
        return null;
    }

    private static void balance() {
        System.out.println("Balance: $" + balance);
    }
}