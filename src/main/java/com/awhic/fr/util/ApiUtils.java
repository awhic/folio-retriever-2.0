package com.awhic.fr.util;

import com.awhic.fr.exception.InvalidApiTokenException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ApiUtils {
    public String getApiKey() {
        try {
            return apiKey();
        } catch (FileNotFoundException e) {
            throw new InvalidApiTokenException();
        }
    }
    private static String apiKey() throws FileNotFoundException {
        File file = new File("src/main/resources/api-key.txt");
        Scanner scanner = new Scanner(file);

        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            throw new InvalidApiTokenException();
        }
    }

    public void writeApiKey() {
        System.out.print("Paste your API key here: ");
        Scanner inquiry = new Scanner(System.in);
        String result = inquiry.nextLine();

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/api-key.txt");
            fileWriter.flush();
            fileWriter.write(result);
            fileWriter.close();
            System.out.println("Success!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmpty() {
        try {
            getApiKey();
        } catch (InvalidApiTokenException e) {
            return true;
        }
        return false;
    }
}
