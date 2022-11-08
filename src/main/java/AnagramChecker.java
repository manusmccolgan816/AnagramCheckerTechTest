import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnagramChecker {

    private static final Logger LOGGER = Logger.getLogger(AnagramChecker.class.getName());
    HashMap<HashSet<String>, Boolean> knownValueBoolMappings;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            new AnagramChecker().inputEntry("src/main/resources/entries.txt");
            System.out.print("Input n to add a new entry or another key to quit: ");
        } while (scanner.nextLine().equals("n"));
    }

    /**
     * Takes username and two text values as input from the user and checks is they are anagrams. The username, two
     * text values and result of the anagram check are then appended to a file.
     */
    public void inputEntry(String filename) {
        Scanner scanner = new Scanner(System.in);
        String username, value1, value2;

        // Repeatedly prompt for each input until input is valid
        do {
            System.out.print("Input username: ");
            username = scanner.nextLine();
        } while (!isValidInput(username));
        do {
            System.out.print("Input first word: ");
            value1 = scanner.nextLine();
        } while (!isValidInput(value1));
        do {
            System.out.print("Input second word: ");
            value2 = scanner.nextLine();
        } while (!isValidInput(value2));

        knownValueBoolMappings = readEntriesFromFile(filename);

        Entry entry = new Entry(username, value1, value2, isAnagram(value1, value2));
        LOGGER.info("Entry object created");

        writeEntryToFile(entry, filename);

        if (entry.getIsAnagram())
            System.out.println("Entry is an anagram.");
        else
            System.out.println("Entry is not an anagram.");
    }

    /**
     * Checks if two strings are anagrams. An anagram is a word that can be rearranged to form another word.
     *
     * @param value1 the first value
     * @param value2 the second value
     * @return true if value1 and value2 are anagrams, false if not.
     */
    public boolean isAnagram(String value1, String value2) {
        // The values cannot be anagrams if they are the same word or have different lengths
        if (value1.equals(value2) || value1.length() != value2.length()) return false;

        if (knownValueBoolMappings != null) {
            // Create an unordered set containing the two values to check
            HashSet<String> inputs = new HashSet<>();
            inputs.add(value1);
            inputs.add(value2);
            // If these two values have been checked previously (and so exist as keys in knownValues)...
            if (knownValueBoolMappings.get(inputs) != null) {
                LOGGER.info("Input values have previously been checked. Reading result from file.");
                // ...return the known result
                return knownValueBoolMappings.get(inputs);
            }
        }

        LOGGER.info("Input values not found in the cache. Running anagram checking algorithm.");

        HashMap<Character, Integer> val1map = new HashMap<>();
        HashMap<Character, Integer> val2map = new HashMap<>();

        // Set each unique character in value1 as a key, with its number of occurrences as its value
        for (char c : value1.toCharArray()) {
            val1map.merge(c, 1, Integer::sum);
        }
        // Do this again for the second array
        for (char c : value2.toCharArray()) {
            val2map.merge(c, 1, Integer::sum);
        }

        return val1map.equals(val2map); // Two hashmaps are considered equal if they have the same key value mappings
    }

    /**
     * Checks if the input is valid. Input is invalid if it is an empty String, null or contains any whitespace,
     * digits or special characters. A message is printed if the input is invalid.
     *
     * @param input the input to assess
     * @return true if the input is valid, false if not
     */
    public boolean isValidInput(String input) {
        if (input == null || input.equals("") || input.matches(".*\\s.*") || input.matches(".*[0-9].*")
                || input.matches(".*[^a-z0-9 ].*")) {
            System.out.println("Input invalid. Must not contain whitespace, digits or special characters.");
            return false;
        }
        return true;
    }

    /**
     * Reads entries from a file and stores their values and result in a HashMap.
     *
     * @param filename the name of the file to read from
     * @return a HashMap that maps a set of two strings to a boolean for each entry
     */
    public HashMap<HashSet<String>, Boolean> readEntriesFromFile(String filename) {
        HashMap<HashSet<String>, Boolean> fileValueBoolMappings = new HashMap<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            String line;
            String[] lineArray;
            // Loop through every line in the file
            while ((line = bufferedReader.readLine()) != null) {
                lineArray = line.split(" "); // Store the current line as an array of strings

                // entryValues stores the two values to compare, with no recognised order
                HashSet<String> entryValues = new HashSet<>();
                entryValues.add(lineArray[1]);
                entryValues.add(lineArray[2]);

                // The unordered HashSet of the two values is the key and the boolean result is the value
                fileValueBoolMappings.put(entryValues, Boolean.parseBoolean(lineArray[3]));
            }
            bufferedReader.close();
            LOGGER.info("Entries read from file and stored in cache.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException when reading from file.", e);
        } catch (ArrayIndexOutOfBoundsException e1) {
            LOGGER.log(Level.SEVERE, "ArrayOutOfBoundsException when reading from file.", e1);
        }
        return fileValueBoolMappings;
    }

    /**
     * Writes the passed entry to the file in the format: username value1 value2 isAnagram.
     *
     * @param entry the entry to write to the file
     * @param filename the name of the file to write to
     */
    public void writeEntryToFile(Entry entry, String filename) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true));
            // Write the entry to the file
            bufferedWriter.write(entry.getUsername() + " " + entry.getValue1() + " " + entry.getValue2() +
                    " " + entry.getIsAnagram());
            bufferedWriter.newLine(); // Start a new line for the next entry to be written to
            bufferedWriter.close();
            LOGGER.info("Entry written to file.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException when writing to file.", e);
        }
    }
}