package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {
    public static HashMap<String, PriorityQueue<Double>> hashMap = new HashMap<>(); // queue store priceIncrease

    public static void main(String[] args) throws IOException {
        final Trade[] previousTrade = {null};
        Files.lines(Paths.get("C:\\Users\\User\\IdeaProjects\\Stream API\\src\\com\\company\\trades.txt"))
                .skip(1)
                .map(Main::analysis)
                .forEach(t -> {
                    if(!hashMap.containsKey(t.seccode)) {
                        hashMap.put(t.seccode, new PriorityQueue<>(10));
                    }
                    PriorityQueue<Double> queueOfIncrease = hashMap.get(t.seccode);
                    if(previousTrade[0] == null) {
                        previousTrade[0] = t;
                        return;
                    }
                    Double currentIncrease = t.price - previousTrade[0].price;
                    if (queueOfIncrease.size() < 10) { queueOfIncrease.add(currentIncrease); }
                    else if (queueOfIncrease.poll() < currentIncrease) {
                        queueOfIncrease.poll();
                        queueOfIncrease.add(currentIncrease);
                    }
                    previousTrade[0] = t;
                });

        for (Map.Entry<String, PriorityQueue<Double>> entry : hashMap.entrySet()) {
            PriorityQueue<Double> queue = entry.getValue();
            for (Double d : queue) {
                System.out.print(d + " ");
            }
            System.out.println();
        }
    }

    private static Trade analysis(String string) {
        String[] strings = string.split("\t");

        return new Trade(strings);
    }
}
