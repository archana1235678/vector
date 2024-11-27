import java.util.Scanner;
import java.util.Vector;

class Pizza {
    private String size;
    private Vector<String> toppings;

    public Pizza(String size) {
        this.size = size;
        this.toppings = new Vector<>();
    }

    public void addTopping(String topping) {
        toppings.add(topping);
    }

    @Override
    public String toString() {
        return "Size: " + size + ", Toppings: " + String.join(", ", toppings);
    }
}

class PizzaShop {
    private Vector<Vector<Pizza>> orders;

    public PizzaShop() {
        orders = new Vector<>();
    }

    public void addOrder(Vector<Pizza> order) {
        if (orders.size() < 100) {
            orders.add(order);
            System.out.println("Order placed successfully.");
        } else {
            System.out.println("Maximum order limit reached!");
        }
    }

    public void addPizzaToOrder(int orderIndex, Pizza pizza) {
        if (orderIndex >= 0 && orderIndex < orders.size()) {
            orders.get(orderIndex).add(pizza);
            System.out.println("Pizza added to Order #" + (orderIndex + 1));
        } else {
            System.out.println("Invalid order number.");
        }
    }

    public void deletePizzaFromOrder(int orderIndex, int pizzaIndex) {
        if (orderIndex >= 0 && orderIndex < orders.size()) {
            Vector<Pizza> order = orders.get(orderIndex);
            if (pizzaIndex >= 0 && pizzaIndex < order.size()) {
                order.remove(pizzaIndex);
                System.out.println("Pizza #" + (pizzaIndex + 1) + " has been deleted from Order #" + (orderIndex + 1) + ".");
            } else {
                System.out.println("Invalid pizza number.");
            }
        } else {
            System.out.println("Invalid order number.");
        }
    }

    public void deleteOrder(int orderIndex) {
        if (orderIndex >= 0 && orderIndex < orders.size()) {
            orders.remove(orderIndex);
            System.out.println("Order #" + (orderIndex + 1) + " has been deleted.");
        } else {
            System.out.println("Invalid order number.");
        }
    }

    public void displayOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders placed.");
            return;
        }
        System.out.println("Your orders:");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println("Order #" + (i + 1) + ":");
            Vector<Pizza> order = orders.get(i);
            for (int j = 0; j < order.size(); j++) {
                System.out.println("  Pizza #" + (j + 1) + ": " + order.get(j));
            }
        }
    }
}

public class PizzaShopApp1 {
    private static final int PLACE_ORDER = 1;
    private static final int ADD_PIZZA = 2;
    private static final int DELETE_PIZZA = 3;
    private static final int DELETE_ORDER = 4;
    private static final int DISPLAY_ORDERS = 5;
    private static final int EXIT = 6;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PizzaShop pizzaShop = new PizzaShop();

        System.out.println("Welcome to the Pizza Shop!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println(PLACE_ORDER + ". Place a new order");
            System.out.println(ADD_PIZZA + ". Add a pizza to an existing order");
            System.out.println(DELETE_PIZZA + ". Delete a pizza from an existing order");
            System.out.println(DELETE_ORDER + ". Delete an existing order");
            System.out.println(DISPLAY_ORDERS + ". Display all orders");
            System.out.println(EXIT + ". Exit");

            int choice = getValidInput(scanner);
            switch (choice) {
                case PLACE_ORDER: // Place a new order
                    Vector<Pizza> currentOrder = new Vector<>();
                    while (true) {
                        System.out.print("Enter pizza size (small, medium, large) or 'done' to finish this order: ");
                        String size = scanner.nextLine();
                        if (size.equalsIgnoreCase("done")) {
                            break;
                        }

                        Pizza pizza = new Pizza(size);
                        while (true) {
                            System.out.print("Enter topping to add (Onions, Black Olives, Mushrooms, Extra Cheese, Pepperoni, Sausage, Bell Pepper) or 'done' to finish adding toppings: ");
                            String topping = scanner.nextLine();
                            if (topping.equalsIgnoreCase("done")) {
                                break;
                            }
                            pizza.addTopping(topping);
                        }

                        currentOrder.add(pizza);
                    }
                    pizzaShop.addOrder(currentOrder);
                    break;

                case ADD_PIZZA: // Add a pizza to an existing order
                    System.out.print("Enter the order number to which you want to add a pizza: ");
                    int orderNumberToAdd = getValidOrderNumber(scanner);
                    System.out.print("Enter pizza size (small, medium, large): ");
                    String sizeToAdd = scanner.nextLine();
                    Pizza pizzaToAdd = new Pizza(sizeToAdd);

                    while (true) {
                        System.out.print("Enter topping to add (or 'done' to finish adding toppings): ");
                        String toppingToAdd = scanner.nextLine();
                        if (toppingToAdd.equalsIgnoreCase("done")) {
                            break;
                        }
                        pizzaToAdd.addTopping(toppingToAdd);
                    }

                    pizzaShop.addPizzaToOrder(orderNumberToAdd, pizzaToAdd);
                    break;

                case DELETE_PIZZA: // Delete a pizza from an existing order
                    System.out.print("Enter the order number to delete a pizza from: ");
                    int orderNumberToDeleteFrom = getValidOrderNumber(scanner);
                    pizzaShop.displayOrders();
                    System.out.print("Enter the pizza number to delete: ");
                    int pizzaNumberToDelete = getValidPizzaNumber(scanner, orderNumberToDeleteFrom);
                    pizzaShop.deletePizzaFromOrder(orderNumberToDeleteFrom, pizzaNumberToDelete);
                    break;

                case DELETE_ORDER: // Delete an existing order
                    System.out.print("Enter the order number to delete: ");
                    int orderNumberToDelete = getValidOrderNumber(scanner);
                    pizzaShop.deleteOrder(orderNumberToDelete);
                    break;

                case DISPLAY_ORDERS: // Display all orders
                    pizzaShop.displayOrders();
                    break;

                case EXIT: // Exit
                    System.out.println("Thank you for using our app!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static int getValidInput(Scanner scanner) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int getValidOrderNumber(Scanner scanner) {
        while (true) {
            int orderNumber = getValidInput(scanner) - 1; // Adjust for zero indexing
            if (orderNumber >= 0) {
                return orderNumber;
            } else {
                System.out.println("Invalid order number. Please try again.");
            }
        }
    }

    private static int getValidPizzaNumber(Scanner scanner, int orderNumber) {
        while (true) {
            int pizzaNumber = getValidInput(scanner) - 1; // Adjust for zero indexing
            if (pizzaNumber >= 0) {
                return pizzaNumber;
            } else {
                System.out.println("Invalid pizza number. Please try again.");
            }
        }
    }
}