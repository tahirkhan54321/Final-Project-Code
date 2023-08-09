package org.example.algorithms;

import org.example.objects.Stock;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class DBSCAN {

    //A stock can either be considered unclassified initially or outlier if it's not in a cluster
    private static final int UNCLASSIFIED = 0;
    private static final int OUTLIER = -1;
    private HashMap<Stock, LinkedList<Stock>> stockGraph;
    private double epsilon;
    private int minPts;


    /**
     * Constructor
     *
     * @param stockGraph the graph we want to perform DBSCAN on
     * @param epsilon    the distance set for epsilon
     * @param minPts     the minimum number of points to be considered a cluster
     */
    public DBSCAN(HashMap<Stock, LinkedList<Stock>> stockGraph, double epsilon, int minPts) {
        this.stockGraph = stockGraph;
        this.epsilon = epsilon;
        this.minPts = minPts;
    }

    /**
     * Finds related stocks which are within epsilon distance of each other
     *
     * @param stock the stock which we are finding nearby stocks
     * @return an arraylist of stocks "near to" the parameter stock
     */
    private ArrayList<Stock> regionQuery(Stock stock) {
        LinkedList<Stock> relatedStocks = stockGraph.get(stock);
        ArrayList<Stock> result = new ArrayList<>();
        for (Stock relatedStock : relatedStocks) {
            if (stock.distanceTo(relatedStock) <= epsilon) {
                result.add(relatedStock);
            }
        }
        return result;
    }

    /**
     * Discovers the clusters around a stock and assigns them to a clusterId
     *
     * @param stock
     * @param clusterId
     * @return
     */
    private boolean expandCluster(Stock stock, int clusterId) {

        //store the starting point of this potential cluster in an arraylist called seeds
        ArrayList<Stock> seeds = regionQuery(stock);
        //check to see if the stock in question has enough stocks around it to meet minPts criterion, if not then it's an outlier
        if (seeds.size() < minPts) {
            stock.setClusterId(OUTLIER);
            return false;
        }

        //if stock is not an outlier, set its clusterId in the Stock object
        for (Stock seed : seeds) {
            seed.setClusterId(clusterId);
        }
        //remove the stock we're expanding around from the potential core points list as it's already been processed
        seeds.remove(stock);

        //explore the seeds arraylist until there is nothing left and assign update their clusterIds
        while (!seeds.isEmpty()) {
            Stock currentStock = seeds.get(0);
            //a list of stocks near to the current stock being queried
            ArrayList<Stock> nearbyStocks = regionQuery(currentStock);
            //only if there are more than minPts stocks near to currentStock, run the for loop
            if (nearbyStocks.size() >= minPts) {
                for (Stock nearby : nearbyStocks) {
                    if (nearby.getClusterId() == UNCLASSIFIED || nearby.getClusterId() == OUTLIER) {
                        //if the current nearby stock is unclassified then add it to the seeds list, i.e. expand the cluster
                        if (nearby.getClusterId() == UNCLASSIFIED) {
                            seeds.add(nearby);
                        }
                        //assign this nearby stock to this clusterId from parameter
                        nearby.setClusterId(clusterId);
                    }
                }
            }
            //remove the current stock from the seeds list as it's been processed and expanded upon
            seeds.remove(currentStock);
        }

//        Testing - performance metric
//        System.out.println("DBSCAN cluster " + clusterId + " complete at " + currentTimeMillis());
        return true;
    }

    /**
     * Method to return a collection (an arraylist)
     * of clusters where "each cluster" is an arraylist of stocks
     * Note that the clusterId is stored in the stock object
     *
     * @return
     */
    public ArrayList<ArrayList<Stock>> cluster() {
        //start the clustering ids at 1, i.e. is part of a cluster now
        int clusterId = 1;
        //iterate through the graph, if a stock is unclassified, attempt to classify it
        for (Stock stock : stockGraph.keySet()) {
            //if this stock has not been classified
            if (stock.getClusterId() == UNCLASSIFIED) {
                //if a new cluster has been formed, create new clusterId
                if (expandCluster(stock, clusterId)) {
                    clusterId++;
                }
            }
        }

        //a collection of arraylists of stocks in each cluster which we are populating
        ArrayList<ArrayList<Stock>> clusters = new ArrayList<>();
        int numClusters = clusterId - 1;
        //add a new arraylist for every clusterId
        for (int i = 0; i < numClusters; i++) {
            clusters.add(new ArrayList<>());
        }

        //loop through the stocks, add them to the appropriate collection in the clusters data structure defined above
        for (Stock stock : stockGraph.keySet()) {
            int currentClusterIdStock = stock.getClusterId();
            if (currentClusterIdStock != OUTLIER) {
                clusters.get(currentClusterIdStock - 1).add(stock);
            }
        }

        return clusters;
    }


    /**
     * Creates a new file with a list of DBSCAN outliers
     *
     * @param filename
     */
    public void outputOutliersToFile(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            //track outliers
            ArrayList<Stock> outliers = new ArrayList<>();

            //loop through the object's graph and add outliers to the outliers arraylist
            for (Stock stock : stockGraph.keySet()) {
                if (stock.getClusterId() == OUTLIER) {
                    outliers.add(stock);
                }
            }

            //write the outliers to a file
            for (Stock outlier : outliers) {
                writer.write(outlier.toString() + "\n");
            }
            writer.close();

           /*
           Testing
           System.out.println("Outliers written to file: " + filename);
           */

        } catch (IOException e) {
            System.out.println("Error writing outliers for DBSCAN: " + e.getMessage());
        }
    }

}
