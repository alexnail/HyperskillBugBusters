package memorizingtool;//Chapter 5

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Continuing with our theme of memorization, this class is designed to help us remember words or strings.
 * After all, words can be quite elusive, especially when working with large amounts of text.
 * The WordMemorize class provides methods to store and recall words.
 * <p>
 * This class goes a step further to offer additional functionalities specific to words.
 * It has methods like
 * "concatenate" to join multiple words together,
 * "length" to determine the length of a word
 * "reverse" to reverse the order of characters in a word.
 * <p>
 * With the WordMemorize class in our toolkit, we can confidently keep track of important words and manipulate them as needed.
 */
public class WordMemorize {
    static ArrayList<String> list = new ArrayList<>();
    boolean finished = false;
    static List<Object> args = new ArrayList<>();
    private static final Map<String, Class<?>[]> commands = createCommands();

    public WordMemorize() {
        list.clear();
    }

    //a satisfying click, the heavy doors slowly creaked open, revealing a dazzling...
    void Run() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        while (!finished) {
            args.clear();
            System.out.println("Perform action:");
            String[] data = scanner.nextLine().split(" ");

            try {
                for (int i = 1; i < data.length; i++) {
                    if (commands.get(data[0])[i - 1].equals(int.class))
                        args.add(Integer.parseInt(data[i]));
                    else {
                        args.add(data[i]);
                    }
                }
            } catch (NullPointerException ignored) {
            } catch (NumberFormatException e) {
                System.out.println("Some arguments can't be parsed!");
            }

            try {
                var methodName = data[0].substring(1);
                this.getClass().getDeclaredMethod(methodName, commands.get(data[0]))
                        .invoke(this, args.toArray());
            } catch (NoSuchMethodException e) {
                System.out.println("No such command");
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect amount of arguments");
            }
        }
    }

    void help() {
        System.out.println(
            "===================================================================================================================\n" +
            "Usage: COMMAND [<TYPE> PARAMETERS]\n" +
            "===================================================================================================================\n" +
            "General commands:\n" +
            "===================================================================================================================\n" +
            "/help - Display this help message\n" +
            "/menu - Return to the menu\n" +
            "\n" +
            "/add [<T> ELEMENT] - Add the specified element to the list\n" +
            "/remove [<int> INDEX] - Remove the element at the specified index from the list\n" +
            "/replace [<int> INDEX] [<T> ELEMENT] - Replace the element at specified index with the new one\n" +
            "/replaceAll [<T> OLD] [<T> NEW] - Replace all occurrences of specified element with the new " +
            "one\n" +
            "\n" +
            "/index [<T> ELEMENT] - Get the index of the first specified element in the list\n" +
            "/sort [ascending/descending] - Sort the list in ascending or descending order\n" +
            "/frequency - The frequency count of each element in the list\n" +
            "/print [<int> INDEX] - Print the element at the specified index in the list\n" +
            "/printAll [asList/lineByLine/oneLine] - Print all elements in the list in specified format\n" +
            "/getRandom - Get a random element from the list\n" +
            "/count [<T> ELEMENT] - Count the number of occurrences of the specified element in the list\n" +
            "/size - Get the number of elements in the list\n" +
            "/equals [<int> INDEX1] [<int> INDEX2] - Check if two elements are equal\n" +
            "/clear - Remove all elements from the list\n" +
            "/compare [<int> INDEX1] [<int> INDEX2] Compare elements at the specified indices in the list\n" +
            "/mirror - Mirror elements' positions in list\n" +
            "/unique - Unique elements in the list\n" +
            "/readFile [<string> FILENAME] - Import data from the specified file and add it to the list\n" +
            "/writeFile [<string> FILENAME] - Export the list data to the specified file");
        System.out.println(
            "===================================================================================================================\n" +
            "Word-specific commands:\n" +
            "===================================================================================================================\n" +
            "/concat [<int> INDEX1] [<int> INDEX2] Concatenate two specified strings\n" +
            "/swapCase [<int> INDEX] Output swapped case version of the specified string\n" +
            "/upper [<int> INDEX] Output uppercase version of the specified string\n" +
            "/lower [<int> INDEX] Output lowercase version of the specified string\n" +
            "/reverse [<int> INDEX] Output reversed version of the specified string\n" +
            "/length [<int> INDEX] Get the length of the specified string\n" +
            "/join [<string> DELIMITER] Join all the strings with the specified delimiter\n" +
            "/regex [<string> PATTERN] Search for all elements that match the specified regular expression " +
            "pattern\n" +
            "===================================================================================================================");
    }

    void menu() {
        this.finished = true;
    }

    void add(String element) {
        list.add(element);
        System.out.println("Element " + element + " added");
    }

    //chamber filled with sparkling jewels and ancient artifacts.
    void remove(int index) {
        try {
            list.remove(index);
            System.out.println("Element on " + index + " position removed");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void replace(int index, String element) {
        try {
            list.set(index, element);
            System.out.println("Element on " + index + " position replaced with " + element);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void replaceAll(String from, String to) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(from)) {
                list.set(i, to);
            }
        }
        System.out.println("Each " + from + " element replaced with " + to);
    }

    void index(String value) {
        int i = list.indexOf(value);
        if (i == -1) {
            System.out.println("There is no such element");
        } else {
            System.out.println("First occurrence of " + value + " is on " + i + " position");
        }
    }

    void sort(String way) {
        if (!way.equals("ascending") && !way.equals("descending")) {
            System.out.println("Incorrect argument, possible arguments: ascending, descending");
            return;
        }
        Comparator<String> comparator = Comparator.naturalOrder();
        list.sort("descending".equals(way) ? comparator.reversed() : comparator);
        System.out.printf("Memory sorted %s\n", way);
    }

    //And so, Lily's unwavering curiosity and determination led her to a treasure...
    void frequency() {
        if (list.isEmpty()) {
            System.out.println("There are no elements in a list");
            return;
        }
        Map<String, Long> counts = new HashMap<>();
        for (String i : list) {
            if (counts.get(i) == null) {
                counts.put(i, 1L);
            } else {
                counts.put(i, counts.get(i) + 1);
            }
        }

        System.out.println("Frequency:");
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    void print(int index) {
        try {
            System.out.println("Element on " + index + " position is " + list.get(index));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    //trove of knowledge and beauty. From that day forward, she became known as the village's greatest...
    void getRandom() {
        try {
            Random random = new Random();
            var idx = random.nextInt(list.size());
            System.out.println("Random element: " + list.get(idx));
        } catch (IllegalArgumentException e) {
            System.out.println("There are no elements memorized");
        }
    }

    void printAll(String type) {
        switch (type) {
            case "asList":
                System.out.println("List of elements:\n" + Arrays.toString(list.toArray()));
                break;
            case "lineByLine":
                System.out.println("List of elements:\n");
                for (String i : list) {
                    System.out.println(i);
                }
                break;
            case "oneLine":
                System.out.println("List of elements:");
                for (int i = 0; i < list.size() - 1; i++) {
                    System.out.print(list.get(i) + " ");
                }
                if (list.size() > 0) System.out.print(list.get(list.size() - 1));
                System.out.println();
                break;
            default:
                System.out.println("Incorrect argument, possible arguments: asList, lineByLine, oneLine");
                break;
        }
    }

    void count(String value) {
        int amount = 0;
        for (String i : list) {
            if (i.equals(value)) {
                amount++;
            }
        }
        System.out.println("Amount of " + value + ": " + amount);
    }

    void size() {
        System.out.println("Amount of elements: " + list.size());
    }

    void equals(int i, int j) {
        try {
            boolean res = list.get(i).equals(list.get(j));
            System.out.printf("%d and %d elements are%s equal: %s\n",
                    i, j, res ? "" : " not", list.get(i) + (res ? " = " : " != ") + list.get(j));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void readFile(String path) throws IOException {
        try {
            FileReaderWords readerThread = new FileReaderWords();
            ArrayList<String> imported = readerThread.read(path);
            list.addAll(imported);
            System.out.println("Data imported: " + imported.size());
        } catch (NoSuchFileException e) {
            System.out.println("File not found!");
        }
    }

    void writeFile(String path) throws IOException {
        FileWriterWords writer = new FileWriterWords();
        writer.write(path, list);
        System.out.println("Data exported: " + list.size());
    }

    void clear() {
        list.clear();
        System.out.println("Data cleared");
    }

    void compare(int i, int j) {
        try {
            if (list.get(i).compareTo(list.get(j)) > 0) {
                System.out.println("Result: " + list.get(i) + " > " + list.get(j));
            } else if (list.get(i).compareTo(list.get(j)) < 0) {
                System.out.println("Result: " + list.get(i) + " < " + list.get(j));
            } else {
                System.out.println("Result: " + list.get(i) + " = " + list.get(j));
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void mirror() {
        Collections.reverse(list);
        System.out.println("Data reversed");
    }

    void unique() {
        Map<String, Long> counts = new HashMap<>();
        for (String i : list) {
            if (counts.get(i) == null) {
                counts.put(i, 1L);
            } else {
                counts.put(i, counts.get(i) + 1);
            }
        }
        ArrayList<String> list2 = new ArrayList<>();
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            list2.add(entry.getKey());
        }
        System.out.println("Unique values: " + Arrays.toString(list2.toArray()));
    }

    //explorer, sharing her discoveries and inspiring others to pursue their own adventures.
    void concat(int i, int j) {
        try {
            System.out.println("Concatenated string: " + list.get(i) + list.get(j));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void swapCase(int i) {
        try {
            System.out.printf("\"%s\" string with swapped case: ", list.get(i));
            for (char c : (list.get(i)).toCharArray()) {
                if (Character.isUpperCase(c)) {
                    System.out.print(Character.toLowerCase(c));
                } else if (Character.isLowerCase(c)) {
                    System.out.print(Character.toUpperCase(c));
                } else {
                    System.out.print(c);
                }
            }
            System.out.println();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void upper(int i) {
        try {
            System.out.printf("Uppercase \"%s\" string: %s\n", list.get(i), (list.get(i)).toUpperCase());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void lower(int i) {
        try {
            System.out.printf("Lowercase \"%s\" string: %s\n", list.get(i), (list.get(i)).toLowerCase());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void reverse(int i) {
        try {
            System.out.printf("Reversed \"%s\" string: %s\n", list.get(i), new StringBuilder(list.get(i)).reverse());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void length(int i) {
        try {
            System.out.printf("Length of \"%s\" string: %d\n", list.get(i), (list.get(i)).length());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
        }
    }

    void join(String delimiter) {
        System.out.printf("Joined string: %s\n", String.join(delimiter, list));
    }

    void regex(String regex) {
        try {
            List<String> matchingElements = new ArrayList<>();
            Pattern pattern = Pattern.compile(regex);
            for (String element : list) {
                if (pattern.matcher(element).matches()) {
                    matchingElements.add(element);
                }
            }
            if (matchingElements.isEmpty()) {
                System.out.println("There are no strings that match provided regex");
            } else {
                System.out.println("Strings that match provided regex:");
                System.out.println(Arrays.toString(matchingElements.toArray()));
            }
        } catch (PatternSyntaxException e) {
            System.out.println("Incorrect regex pattern provided");
        }
    }

    private static Map<String, Class<?>[]> createCommands() {
        Map<String, Class<?>[]> commands = new HashMap<>();
        commands.put("/help", new Class<?>[]{});
        commands.put("/menu", new Class<?>[]{});
        commands.put("/add", new Class<?>[]{String.class});
        commands.put("/remove", new Class<?>[]{int.class});
        commands.put("/replace", new Class<?>[]{int.class, String.class});
        commands.put("/replaceAll", new Class<?>[]{String.class, String.class});
        commands.put("/index", new Class<?>[]{String.class});
        commands.put("/sort", new Class<?>[]{String.class});
        commands.put("/frequency", new Class<?>[]{});
        commands.put("/print", new Class<?>[]{int.class});
        commands.put("/printAll", new Class<?>[]{String.class});
        commands.put("/getRandom", new Class<?>[]{});
        commands.put("/count", new Class<?>[]{String.class});
        commands.put("/size", new Class<?>[]{});
        commands.put("/equals", new Class<?>[]{int.class, int.class});
        commands.put("/readFile", new Class<?>[]{String.class});
        commands.put("/writeFile", new Class<?>[]{String.class});
        commands.put("/clear", new Class<?>[]{});
        commands.put("/compare", new Class<?>[]{int.class, int.class});
        commands.put("/mirror", new Class<?>[]{});
        commands.put("/unique", new Class<?>[]{});
        commands.put("/concat", new Class<?>[]{int.class, int.class});
        commands.put("/swapCase", new Class<?>[]{int.class});
        commands.put("/upper", new Class<?>[]{int.class});
        commands.put("/lower", new Class<?>[]{int.class});
        commands.put("/reverse", new Class<?>[]{int.class});
        commands.put("/length", new Class<?>[]{int.class});
        commands.put("/join", new Class<?>[]{String.class});
        commands.put("/regex", new Class<?>[]{String.class});
        return commands;
    }
}
