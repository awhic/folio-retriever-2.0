package com.awhic.fr.util;

public class ConsoleUtils {

    public void welcome() {
        System.out.println("FolioRetriever initialized.");
    }

    public void help() {
        String helpMessage = "t - Enter \"t\" followed by a space and a stocker ticker to get a fast quote.\n" +
                "p - View portfolio balance\n" +
                "p? - View portfolio entries\n" +
                "key - view API key\n" +
                "key-edit - edit API key\n" +
                "edit - edit portfolio";

        System.out.println(helpMessage);
    }

    public String spacer(String value, int space) {
        StringBuilder stringBuilder = new StringBuilder();
        int spacerSize = space - value.length();
        for (int i = 0; i < spacerSize; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
