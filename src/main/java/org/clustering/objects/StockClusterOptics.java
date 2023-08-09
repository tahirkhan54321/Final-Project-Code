package org.clustering.objects;

import java.util.ArrayList;

public class StockCluster {

    private ArrayList<Stock> stocks;

    public StockCluster() {
        stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public boolean isEmpty() {
        return stocks.isEmpty();
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

}
