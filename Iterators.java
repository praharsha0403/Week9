// Iterators.java
import java.nio.file.*;
import java.util.*;
import java.io.IOException;

public class Iterators {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("pride-and-prejudice.txt");
            return;
        }

        Set<String> stopWords = new HashSet<>(Arrays.asList(Files.readString(Path.of("stop_words.txt")).split(",")));
        for (char ch = 'a'; ch <= 'z'; ch++) stopWords.add(String.valueOf(ch));

        Map<String, Integer> freqs = new HashMap<>();
        int count = 0;

        for (String line : Files.readAllLines(Path.of(args[0]))) {
            for (String rawWord : line.split("[^a-zA-Z0-9]+")) {
                String word = rawWord.toLowerCase();
                if (!stopWords.contains(word) && word.length() > 1) {
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                    count++;
                    if (count % 5000 == 0) {
                        printTop(freqs);
                    }
                }
            }
        }

        printTop(freqs);
    }

    static void printTop(Map<String, Integer> freqs) {
        System.out.println("-------------------------");
        freqs.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(25)
                .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue()));
    }
}
