package com.awhic.fr.util;

public class ConsoleUtils {
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void welcome() {
        System.out.println("Welcome to Folio Retriever.");
        sleeper();
        System.out.println("");
        System.out.println("Menu:");
        System.out.println("To get a fast quote, enter a stock ticker.");
        System.out.println("To view your portfolio balance, enter '$'.");
        System.out.println("To exit, enter '999': ");
    }

    public void sleeper() {
        try {
            Thread.sleep(1100L);
        } catch (InterruptedException ignored) { }
    }
}
