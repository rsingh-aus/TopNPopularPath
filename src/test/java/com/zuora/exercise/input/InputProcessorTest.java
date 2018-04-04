package com.zuora.exercise.input;

import co.unruly.matchers.StreamMatchers;
import java.util.stream.Stream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Test
public class InputProcessorTest {

    private InputProcessor processor;
    private final String VALID_FILE = "ValidFile.txt";
    private String[][] data;

    @BeforeMethod
    public void setUp() {
        processor = new InputProcessor();
        data = new String[][]{{"User1", "/"}, {"User1", "login"}, {"User2", "/"}, {"User2", "subscriber"}, {"User2", "login"}};
    }

    public void shouldReturnEmptyStreamIfInputFilePathNotValid() {
        Stream<String> inputFileStream = processor.processFileInput("inValidFile.txt");
        assertThat(inputFileStream, StreamMatchers.empty());
    }

    public void shouldReturnCorrectStreamForValidFileInput() {
        Stream<String> inputFileStream = processor.processFileInput(VALID_FILE);
        assertThat(inputFileStream, StreamMatchers.contains("User1 , /","User1 , login","User2,/","User2,subscriber","User2, login"));
    }

    public void shouldCreateDataArrayFromFileInput() {
        assertThat(processor.getData(VALID_FILE), is(data));
    }

}