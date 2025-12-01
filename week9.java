import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public class week9 {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("pride-and-prejudice.txt");
            return;
        }

        // read stop words (same folder now)
        List<String> stopWords = Files.readAllLines(Paths.get("stop_words.txt"));
        Set<String> stopWordSet = new HashSet<>(Arrays.asList(stopWords.get(0).split(",")));
        for (char ch = 'a'; ch <= 'z'; ch++) stopWordSet.add(String.valueOf(ch));

        // read input file
        String content = Files.readString(Paths.get(args[0]));

        // stream words, count freqs
        System.out.println("processing...");
        Map<String, Long> freqs = Arrays.stream(content.toLowerCase().split("[^a-zA-Z0-9]+"))
            .filter(w -> !stopWordSet.contains(w) && w.length() > 1)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // print top 25
        System.out.println("--------- top 25 -----------");
        freqs.entrySet().stream()
            .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
            .limit(25)
            .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue()));
    }
}