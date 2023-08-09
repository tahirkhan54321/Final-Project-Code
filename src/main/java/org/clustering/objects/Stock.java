package org.example.objects;

public class Stock {

    /*
    Backup for alternative data sources
    private int totalAssets;
    private int totalLiabilities;
    private int sales;
    private int shareCount;
    */
    private String ticker;
    private double stockPrice;
    private String sector;
    private double priceBookRatio;
    private double priceSalesRatio;
    private int clusterId;

    public Stock(String ticker, String sector, double stockPrice, double priceBookRatio, double priceSalesRatio) {
        this.ticker = ticker;
        this.sector = sector;
        this.stockPrice = stockPrice;
        this.priceBookRatio = priceBookRatio;
        this.priceSalesRatio = priceSalesRatio;
        this.clusterId = 0;
        /*
        Backup for alternative data sources
        this.totalAssets = totalAssets;
        this.totalLiabilities = totalLiabilities;
        this.sales = sales;
        this.shareCount = shareCount;
        double bookValue = 1.0 * (totalAssets - totalLiabilities);
        this.priceBookRatio = (this.stockPrice) / (bookValue / this.shareCount);
        this.priceSalesRatio = (double) this.stockPrice / this.sales;
        */
    }

    public String getTicker() {
        return ticker;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getPriceBookRatio() {
        return priceBookRatio;
    }

    public double getPriceSalesRatio() {
        return priceSalesRatio;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    /**
     * Note that this is Eucliean distance
     *
     * @param relatedStock another stock
     * @return Euclidean distance between two stocks based on P/B and P/S ratio
     */
    public double distanceTo(Stock relatedStock) {
        double priceBookDiff = this.priceBookRatio - relatedStock.priceBookRatio;
        double priceSalesDiff = this.priceSalesRatio - relatedStock.priceSalesRatio;
        return Math.sqrt(priceBookDiff * priceBookDiff + priceSalesDiff * priceSalesDiff);
    }

    public String toString() {
        return "Ticker: " + this.ticker + ", Stock Price: " + this.stockPrice + ", P/B ratio: " +
                this.priceBookRatio + ", P/S ratio: " + this.priceSalesRatio;
    }
}
