package com.awhic.fr.util;

public class ConsoleUtils {

    public void welcome() {
        System.out.println("FolioRetriever initialized.");
    }

    public void help() {
        String helpMessage = "t - Enter \"t\" followed by a space and a stocker ticker to get a fast quote.\n" +
                "p - View portfolio balance\n" +
                "key - view API key\n" +
                "key-edit - edit API key\n" +
                "edit - edit portfolio";

        System.out.println(helpMessage);
    }
}
