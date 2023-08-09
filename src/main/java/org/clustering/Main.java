package org.example;

import org.example.objects.Stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        /*
        Steps:
            Initialise
                Create an Arraylist to store the stocks
                Create a hashmap to store the tickers and sectors
                Call the APIs to populate these
                Loop through the arraylist and add the sector to the stock object
            Adjacency List
                Create a blank adjacency list graph
                Populate the graph by looping through the stocks arraylist
                https://chat.openai.com/share/4cd90465-2c03-4a3f-8027-064173962da1 todo: get rid of
            Run DBSCAN
            Run OPTICS
            Output to file (assuming DBSCAN and OPTICS don't already)
         */

        //Initialise and populate all stocks list
        String filepath = "src/main/java/org/example/constituents-financials_csv.csv";
        CSVReader csvReader = new CSVReader();
        ArrayList<Stock> allStocksList = csvReader.populateStocks(filepath);

        //Create adjacency list graph using Stock and an associated LinkedList of Stock
        HashMap<Stock, LinkedList<Stock>> stocksGraph = new HashMap<>();

        //Populate the graph
        for (int i = 0; i < allStocksList.size(); i++) {
            //information about the current stock in the list
            Stock currentStockObject = allStocksList.get(i);
            String currentSector = currentStockObject.getSector();
            //Linkedlist for stocks adjacent to the current one
            LinkedList<Stock> stocksAdjacentToCurrentStock = new LinkedList<>();

            //information about the other stocks in the list & to check if sectors are equal
            for (int j = 0; j < allStocksList.size(); j++) {
                //a stock cannot be adjacent to itself
                if (i != j) {
                    Stock otherStock = allStocksList.get(j);
                    String otherSector = otherStock.getSector();
                    //if a stock is in the same sector then it's considered adjacent
                    if (currentSector.equals(otherSector)) {
                        stocksAdjacentToCurrentStock.add(otherStock);
                    }
                }
            }
            //add the stock and its associated linkedlist to the graph
            stocksGraph.put(currentStockObject, stocksAdjacentToCurrentStock);
        }

        //TODO: run DBSCAN


        //TODO: run OPTICS

    }
}