package com.zuora.exercise.input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class InputProcessor {

    public String[][] getData(String file) {
        return processFileInput(file)
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .map(val -> new String[]{val.split(",")[0].trim() , val.split(",")[1].trim()})
                .toArray(String[][]::new);
    }

    Stream<String> processFileInput(String filePath) {
        try {
            return Files.lines(Paths.get(filePath));
        } catch (IOException ex) {
            System.out.println("Cannot find file : " + filePath);
            return Stream.empty();
        }
    }

    public static boolean validInput(String[] args) {
        boolean isValid = false;
        if(args.length == 2 && isValidInteger(args[1])) {
            isValid = true;
        }
        return isValid;
    }

    private static boolean isValidInteger(String input) {
        boolean isValid = true;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            isValid = false;
        }
        return isValid;
    }

}
