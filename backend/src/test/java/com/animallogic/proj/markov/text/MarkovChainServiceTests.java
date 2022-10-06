package com.animallogic.proj.markov.text;

import com.animallogic.proj.markov.text.exception.ParameterInvalidException;
import com.animallogic.proj.markov.text.fixture.Fixtures;
import com.animallogic.proj.markov.text.service.FileService;
import com.animallogic.proj.markov.text.service.MarkovChainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkovChainServiceTests {

    FileService fileService;
    MarkovChainService chainService;

    @BeforeEach
    void setUp() {
        chainService = new MarkovChainService();
        fileService = new FileService();
    }

    @Test
    @DisplayName("Invalid input will gen expected exceptions.")
    void whenInputTextAndInvalidKeySize() {
        String filename = "test";
        List<String> content = Fixtures.getSimpleContent();
        Exception exception = Assertions.assertThrows(ParameterInvalidException.class, () -> {
            chainService.generateRandomNext(2000000, 1, content, filename);
        });
        String errorMessage = "Can only generate 100K words!";
        assertEquals(exception.getMessage(), errorMessage);

        exception = Assertions.assertThrows(ParameterInvalidException.class, () -> {
            chainService.generateRandomNext(1000, 0, content, filename);
        });
        errorMessage = "Key size must be larger than 0!";
        assertEquals(exception.getMessage(), errorMessage);

        exception = Assertions.assertThrows(ParameterInvalidException.class, () -> {
            chainService.generateRandomNext(1000, 1, new ArrayList<>(), filename);
        });
        errorMessage = "Content must have more than 20 words !";
        assertEquals(exception.getMessage(), errorMessage);

        exception = Assertions.assertThrows(ParameterInvalidException.class, () -> {
            chainService.generateRandomNext(1000, content.size(), content, filename);
        });
        errorMessage = "Key size is bigger than content length !";
        assertEquals(exception.getMessage(), errorMessage);
    }

    @Test
    @DisplayName("Should generate proper words")
    void whenInputTextAndKeySize() throws ParameterInvalidException {
        String filename = "test";
        List<String> content = Fixtures.getSimpleContent();
        List<String> list = chainService.generateRandomNext(100, 1, content, filename);

        //check if all generated words order are able to find from source text
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).equals("on")) {
                //bypass the last element, since it is assigned to first node, which will break the testing.
                break;
            }
            String next = list.get(i);
            String previous = list.get(i - 1);
            boolean orderFlag = findPreviousWord(content, previous, next);
            Assertions.assertTrue(orderFlag);
        }
    }

    @Test
    @DisplayName("Should generate proper words with different Key Size")
    public void testValidSourceAndValidKeySize() throws ParameterInvalidException {
        String filename = "test";
        List<String> content = Fixtures.getContent();
        List<String> list = chainService.generateRandomNext(100, 3, content, filename);

        //check if all generated words order are able to find from source text
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).trim().equals("all.")) {
                //bypass the last element, since it is assigned to first node, which will break the testing.
                break;
            }
            String next = list.get(i);
            String previous = list.get(i - 1);
            boolean orderFlag = findPreviousWord(content, previous, next);
            Assertions.assertTrue(orderFlag);
        }
    }

    /**
     * A helper method to check the generated words compare with the original word,
     * due to the markov property, a word only depends on its previous word, so generated word's previous
     * must be in the original text and before itself.
     *
     * @param list
     * @param previous
     * @param next
     * @return
     */
    public boolean findPreviousWord(List<String> list, String previous, String next) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).equals(previous) && list.get(i).equals(next)) {
                return true;
            }
        }
        return false;
    }

}
