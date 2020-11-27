package com.ikinsure.hyperskill.hard.phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String BASE_FILE = "C:\\Users\\Wojciech\\IdeaProjects\\zabawa\\directory.txt";
    private static final String FIND_FILE = "C:\\Users\\Wojciech\\IdeaProjects\\zabawa\\find.txt";

    public static void main(String[] args) throws IOException {

        // searches & sorting
        Searchable<String> linearSearch = new LinearSearch<>();
        Searchable<String> jumpSearch = new JumpSearch<>();
        Searchable<String> binarySearch = new BinarySearch<>();
        Sortable<String> bubbleSort = new BubbleSort<>();
        Sortable<String> quickSort = new QuickSort<>();

        // loading data
        List<String> srcData = load(BASE_FILE).stream()
                .map(s -> s.substring(s.indexOf(" ") + 1)).collect(Collectors.toList());
        List<String> queries = load(FIND_FILE);
        List<String> data = new ArrayList<>(srcData);


        // linear search
        System.out.println("Start searching (linear search)...");
        long start = System.currentTimeMillis();
        int counter = linearSearch.search(data, queries);
        long end = System.currentTimeMillis();
        Duration duration = Duration.of(end - start, ChronoUnit.MILLIS);
        System.out.println("Found " + counter + " / " + queries.size() + " entries. Time taken: " + formatTime(duration));


        // bubble sort + jump search
        Collections.copy(data, srcData);
        System.out.println("\nStart searching (bubble sort + jump search)...");
        start = System.currentTimeMillis();
        //Collections.sort(data);
        boolean val = bubbleSort.sort(data, duration);
        end = System.currentTimeMillis();
        Duration sortDuration = Duration.of(end - start, ChronoUnit.MILLIS);

        start = System.currentTimeMillis();
        if (val) {
            counter = jumpSearch.search(data, queries);
            end = System.currentTimeMillis();
            Duration searchDuration = Duration.of(end - start, ChronoUnit.MILLIS);
            System.out.println("Found " + counter + " / " + queries.size() + " entries. Time taken: " + formatTime(searchDuration.plus(sortDuration)));
            System.out.println("Sorting time: " + formatTime(sortDuration));
            System.out.println("Searching time: " + formatTime(searchDuration));
        } else {
            counter = linearSearch.search(data, queries);
            end = System.currentTimeMillis();
            Duration searchDuration = Duration.of(end - start, ChronoUnit.MILLIS);
            System.out.println("Found " + counter + " / " + queries.size() + " entries. Time taken: " + formatTime(searchDuration.plus(sortDuration)));
            System.out.println("Sorting time: " + formatTime(sortDuration) + " - STOPPED, moved to linear search");
            System.out.println("Searching time: " + formatTime(searchDuration));
        }


        // quick sort + binary search
        Collections.copy(data, srcData);
        System.out.println("\nStart searching (quick sort + binary search)...");
        start = System.currentTimeMillis();
        quickSort.sort(data, duration);
        end = System.currentTimeMillis();
        sortDuration = Duration.of(end - start, ChronoUnit.MILLIS);

        start = System.currentTimeMillis();
        counter = binarySearch.search(data, queries);
        end = System.currentTimeMillis();
        Duration searchDuration = Duration.of(end - start, ChronoUnit.MILLIS);
        System.out.println("Found " + counter + " / " + queries.size() + " entries. Time taken: " + formatTime(searchDuration.plus(sortDuration)));
        System.out.println("Sorting time: " + formatTime(sortDuration));
        System.out.println("Searching time: " + formatTime(searchDuration));
    }

    private static List<String> load(String file) throws IOException {
        return Files.readAllLines(Path.of(file));
    }

    private static String formatTime(Duration d) {
        return d.toMinutesPart() + " min. " + d.toSecondsPart() + " sec. " + d.toMillisPart() + " ms.";
    }
}