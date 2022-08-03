package com.awhic.fr.util;

import com.awhic.fr.code.exception.InvalidApiTokenException;

import java.io.File;
import java.io.FileNotFoundException;
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
}
