package com.company;

public class Trade {
    long tradeNQ;
    int tradeTime;
    long volume;
    double value;
    String seccode;
    double price;
    double rise;

    public Trade(String[] strings) {
        this.rise = 0;
        this.tradeNQ = Long.parseLong(strings[0]);
        this.tradeTime = Integer.parseInt(strings[1]);
        this.seccode = strings[3];
        this.price = Double.parseDouble(strings[4]);
        this.volume = Long.parseLong(strings[5]);
        this.value = Double.parseDouble(strings[8]);
    }

}
