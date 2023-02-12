package ind.awhic.ls.util;

public class ConsoleUtils {

    public void welcome() {
        System.out.println("LightShares initialized.");
    }

    public void help() {
        String helpMessage = "t - Enter \"t\" followed by a space and a stocker ticker to get a fast quote.\n" +
                "p - View portfolio balance\n" +
                "pv - View portfolio entries\n" +
                "pe - Edit portfolio entries\n" +
                "k - View API key\n" +
                "ke - Edit API key\n" +
                "x - Exit the application";
        System.out.println(helpMessage);
    }

    public String spacer(String value, int space) {
        StringBuilder stringBuilder = new StringBuilder();
        int spacerSize = space - value.length();
        stringBuilder.append(" ".repeat(Math.max(0, spacerSize)));
        return stringBuilder.toString();
    }
}
