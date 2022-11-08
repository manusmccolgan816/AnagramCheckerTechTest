import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AnagramCheckerTest {

    @Test
    void isValidInputTrueWithAllLetters() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertTrue(anagramChecker.isValidInput("helloworld"));
    }

    @Test
    void isValidInputFalseWithSomeDigits() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertFalse(anagramChecker.isValidInput("he110w0r1d"));
    }

    @Test
    void isValidInputFalseWithSomeWhitespace() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertFalse(anagramChecker.isValidInput("hello world"));
    }

    @Test
    void isValidInputFalseWithSomeSpecialChars() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertFalse(anagramChecker.isValidInput("hello!*/+there-|%&$£"));
    }

    @Test
    void isValidInputFalseWithSomeNewLine() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertFalse(anagramChecker.isValidInput("hello\nworld"));
    }

    @Test
    void isValidInputFalseWithEmptyString() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertFalse(anagramChecker.isValidInput(""));
    }

    @Test
    void isValidInputFalseWithNull() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertFalse(anagramChecker.isValidInput(null));
    }


    @Test
    void isAnagramTrueWithValidAnagram() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertTrue(anagramChecker.isAnagram("friend", "finder"));
    }

    @Test
    void isAnagramFalseWithNonAnagramWords() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertFalse(anagramChecker.isAnagram("friend", "friends"));
    }

    @Test
    void isAnagramFalseWithSameWords() {
        AnagramChecker anagramChecker = new AnagramChecker();
        assertFalse(anagramChecker.isAnagram("friend", "friend"));
    }


    @Test
    void readEntriesFromFileCorrectlySuccessful() {
        HashMap<HashSet<String>, Boolean> expectedResult = new HashMap<>();
        HashSet<String> hs1 = new HashSet<>();
        hs1.add("friend");
        hs1.add("finder");
        expectedResult.put(hs1, true);
        HashSet<String> hs2 = new HashSet<>();
        hs2.add("hill");
        hs2.add("lake");
        expectedResult.put(hs2, false);
        HashSet<String> hs3 = new HashSet<>();
        hs3.add("skill");
        hs3.add("kills");
        expectedResult.put(hs3, true);
        AnagramChecker anagramChecker = new AnagramChecker();

        assertEquals(expectedResult, anagramChecker.readEntriesFromFile("src/test/resources/testEntries.txt"));
    }

    @Test
    void readEntriesFromFileIncorrectlyUnsuccessful() {
        HashMap<HashSet<String>, Boolean> expectedResult = new HashMap<>();
        HashSet<String> hs1 = new HashSet<>();
        hs1.add("friend");
        hs1.add("finder");
        expectedResult.put(hs1, true);
        HashSet<String> hs2 = new HashSet<>();
        hs2.add("hill");
        hs2.add("lake");
        expectedResult.put(hs2, true); // The value of hs2 should be false. This is true for test purposes
        AnagramChecker anagramChecker = new AnagramChecker();

        assertNotEquals(expectedResult, anagramChecker.readEntriesFromFile("src/test/resources/testEntries.txt"));
    }


    @Test
    void writeEntryToFileCorrectlySuccessful() {
        Entry entry = new Entry("testuser", "round", "pound", false);
        String expectedResult = "testuser round pound false";
        String filename = "src/test/resources/writeTestEntries";
        AnagramChecker anagramChecker = new AnagramChecker();

        File testFile = new File(filename);
        // Delete file so that a new one can be written to
        testFile.delete();

        anagramChecker.writeEntryToFile(entry, filename);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String actualResult = bufferedReader.readLine();
            assertEquals(expectedResult, actualResult);
        } catch (IOException e) {
            fail("IOException occurred when reading from file in writeEntryToFileSuccessful()");
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeEntryToFileIncorrectlyUnsuccessful() {
        Entry entry = new Entry("testuser", "round", "pound", false);

        // String here should not match String written to file
        String expectedResult = "this is not what should be written";

        String filename = "src/test/resources/writeTestEntries";
        AnagramChecker anagramChecker = new AnagramChecker();

        File testFile = new File(filename);
        // Delete file so that a new one can be written to
        testFile.delete();

        anagramChecker.writeEntryToFile(entry, filename);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String actualResult = bufferedReader.readLine();
            assertNotEquals(expectedResult, actualResult);
        } catch (IOException e) {
            fail("IOException occurred when reading from file in writeEntryToFileSuccessful()");
            throw new RuntimeException(e);
        }
    }
}