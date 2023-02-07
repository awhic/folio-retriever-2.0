package com.awhic.fr.util;

public class ConsoleUtils {

    public void welcome() {
        System.out.println("FolioRetriever initialized.");
    }

    public void help() {
        String helpMessage = "t - Enter \"t\" followed by a space and a stocker ticker to get a fast quote.\n" +
                "p - View portfolio balance\n" +
                "pd - Display portfolio entries\n" +
                "k - view API key\n" +
                "ke - edit API key\n" +
                "pe - Edit portfolio";

        //TODO: update this, and add exit command
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
