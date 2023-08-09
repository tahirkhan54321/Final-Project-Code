package org.example.algorithms;

import org.example.objects.Stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class OPTICS {

    private HashMap<Stock, LinkedList<Stock>> stocksGraph;
    private HashMap<Stock, Double> coreDistances;
    private HashMap<Stock, Double> reachabilityDistances;
    private HashMap<Stock, Boolean> visited;
    private ArrayList<StockCluster> clusters;

}
