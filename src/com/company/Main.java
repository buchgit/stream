package com.company;

import javafx.util.Pair;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static Map<String, Double[]> hashMap = new TreeMap<>();
    public static PriorityQueue<Pair<String, Double>> maxIncreaseQueue = new PriorityQueue<>(Comparator.comparing(Pair::getValue));
    public static PriorityQueue<Pair<String, Double>> minIncreaseQueue;

    static {
        minIncreaseQueue = new PriorityQueue<>((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
    }

    public static void main(String[] args) throws IOException {
        final Trade[] previousTrade = {null};
        Files.lines(Paths.get("C:\\Users\\User\\IdeaProjects\\Stream API\\src\\com\\company\\1.txt"))
                .skip(1)
                .map(Main::analysis)
                .forEach(t -> {
                    if(!hashMap.containsKey(t.seccode)) {
                        hashMap.put(t.seccode, new Double[]{Double.MIN_VALUE, Double.MAX_VALUE});
                        previousTrade[0] = t;
                        return;
                    }
                    Double[] increaseMaxMin = hashMap.get(t.seccode);


                    Double currentIncrease = t.price - previousTrade[0].price;
                    if (increaseMaxMin[0] < currentIncrease) {
                        increaseMaxMin[0] = currentIncrease;
                    } else if (increaseMaxMin[1] > currentIncrease) {
                        increaseMaxMin[1] = currentIncrease;
                    }
                    previousTrade[0] = t;
                });

        for (Map.Entry<String, Double[]> entry : hashMap.entrySet()) {

            if (maxIncreaseQueue.size() < 9) {
                maxIncreaseQueue.add(new Pair<>(entry.getKey(), entry.getValue()[0]));
            } else if (entry.getValue()[0] > maxIncreaseQueue.peek().getValue()) {
                maxIncreaseQueue.poll();
                maxIncreaseQueue.add(new Pair<>(entry.getKey(), entry.getValue()[0]));
            }

            if (minIncreaseQueue.size() < 10) {
                minIncreaseQueue.add(new Pair<>(entry.getKey(), entry.getValue()[1]));
            } else if (entry.getValue()[1] < minIncreaseQueue.peek().getValue()) {
                minIncreaseQueue.poll();
                maxIncreaseQueue.add(new Pair<>(entry.getKey(), entry.getValue()[1]));
            }
        }

        for (Pair<String, Double> pair : maxIncreaseQueue) {
            System.out.printf("%s:  %.3f%n", pair.getKey(), pair.getValue());
        }

        System.out.println(maxIncreaseQueue.size());
    }

    private static Trade analysis(String string) {
        String[] strings = string.split("\t");

        return new Trade(strings);
    }
}
