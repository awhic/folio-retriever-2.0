package com.awhic.fr.util;

public class ConsoleUtils {

    public void welcome() {
        System.out.println("Welcome to Folio Retriever.");
        sleeper();
        System.out.println("");
        help();
    }

    public void help() {
        System.out.println("To get a fast quote, enter a stock ticker.");
        System.out.println("To view your portfolio balance, enter '-p'.");
        System.out.println("To edit your API key, enter '-a'.");
        System.out.println("To view your stored API key, enter '-a?'.");
        System.out.println("To edit your portfolio, enter '-e'");
        System.out.println("To exit, enter '-x': ");
    }

    public void sleeper() {
        try {
            Thread.sleep(1100L);
        } catch (InterruptedException ignored) { }
    }
}
