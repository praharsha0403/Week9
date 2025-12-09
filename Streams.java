// Streams.java
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public class Streams {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("pride-and-predjudice.txt");
            return;
        }

        Set<String> stopWords = new HashSet<>(Arrays.asList(Files.readString(Path.of("stop_words.txt")).split(",")));
        for (char ch = 'a'; ch <= 'z'; ch++) stopWords.add(String.valueOf(ch));

        String content = Files.readString(Path.of(args[0]));

        Map<String, Long> freqs = Arrays.stream(content.toLowerCase().split("[^a-zA-Z0-9]+"))
            .filter(w -> !stopWords.contains(w) && w.length() > 1)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        freqs.entrySet().stream()
            .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
            .limit(25)
            .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue()));
    }
}
