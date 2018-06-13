package com.zuora.exercise;

import com.zuora.exercise.input.InputProcessor;
import com.zuora.exercise.service.TopNPopularPathService;
import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static com.zuora.exercise.factory.TopNPopularPathServiceFactory.getTopNPopularPathService;
import static com.zuora.exercise.input.InputProcessor.validInput;

public class Application {

    private final TopNPopularPathService service = getTopNPopularPathService();
    private final InputProcessor inputProcessor = new InputProcessor();

    public static void main(String[] args) {
        if(validInput(args)) {
            new Application().start(args[0], parseInt(args[1]));
        }
        else {
            System.out.println("Invalid arguments. Please try again");
        }
    }

    private void start(String file, int n) {
        service.setup(inputProcessor.getData(file));
        String[] result = service.getTopNPopularPaths(n);
        printResult(result);
    }

    private void printResult(String[] result) {
        Arrays.stream(result)
                .forEach(System.out::println);
    }
}
