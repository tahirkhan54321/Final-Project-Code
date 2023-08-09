package org.clustering;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.clustering.objects.Stock;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CSVReader {

    public ArrayList<Stock> populateStocks(String filepath) {
        ArrayList<Stock> stocks = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Paths.get(filepath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            boolean firstRow = true;

            for (CSVRecord record : csvParser) {
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                String ticker = record.get(0);
                String sector = record.get(2);
                double stockPrice = Double.valueOf(record.get(3));
                double priceBookRatio = Double.valueOf(record.get(12));
                double priceSalesRatio = Double.valueOf(record.get(11));

                Stock stock = new Stock(ticker, sector, stockPrice, priceBookRatio, priceSalesRatio);
                stocks.add(stock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        //Testing - (Passed)
        System.out.println("allStocksList size is: " + stocks.size());
        for (Stock stock : stocks) {
            System.out.println(stock.getTicker() + ", sector: " + stock.getSector() +
                    ", P/B: " + stock.getPriceBookRatio() + ", P/S: " + stock.getPriceSalesRatio());
        }
        */

        return stocks;

    }
}
