package com.motorph.inventory;

import java.util.Date;
import java.util.Scanner;

public class InventorySystem {
    private final InventoryBST bst;
    private final Scanner scanner;

    public InventorySystem() {
        bst = new InventoryBST();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            displayMainMenu();
            System.out.print("Select an option: ");
            String option = scanner.nextLine();
            switch (option) {
                case "1" -> addStock();
                case "2" -> deleteStock();
                case "3" -> searchStock();
                case "4" -> displayInventory();
                case "5" -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Stock");
        System.out.println("2. Delete Stock");
        System.out.println("3. Search Inventory");
        System.out.println("4. Display Inventory (Sorted)");
        System.out.println("5. Exit");
    }

    private void addStock() {
        System.out.print("Enter Engine Number (10 characters): ");
        String engineNumber = scanner.nextLine().trim();
        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine().trim();

        if (engineNumber.length() != 10 || brand.isEmpty()) {
            System.out.println("Invalid input.");
            return;
        }
        if (bst.search(engineNumber) != null) {
            System.out.println("Duplicate engine number.");
            return;
        }
        InventoryData newRecord = new InventoryData(brand, engineNumber, new Date(), "On-hand", "New");
        bst.insert(newRecord);
        System.out.println("Product added successfully:\n" + newRecord);
    }

    private void deleteStock() {
        System.out.print("Enter Engine Number to delete: ");
        String engineNumber = scanner.nextLine().trim();
        InventoryData record = bst.search(engineNumber);
        if (record == null) {
            System.out.println("Product not found.");
            return;
        }
        if (!"On-hand".equalsIgnoreCase(record.getStatus())) {
            System.out.println("Product cannot be deleted (status is not 'On-hand').");
            return;
        }
        System.out.print("Confirm deletion of product (Y/N): ");
        String confirmation = scanner.nextLine().trim();
        if (confirmation.equalsIgnoreCase("Y")) {
            record.setStatus("Old");
            record.setLevel("Sold");
            bst.delete(engineNumber);
            System.out.println("Product deleted successfully. Updated record:\n" + record);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void searchStock() {
        System.out.print("Enter Engine Number to search: ");
        String engineNumber = scanner.nextLine().trim();
        InventoryData record = bst.search(engineNumber);
        if (record != null) {
            System.out.println("Product Found:\n" + record);
        } else {
            System.out.println("Product not found.");
        }
    }

    private void displayInventory() {
        System.out.println("Inventory (sorted by Engine Number):");
        bst.inOrder();
    }

}
