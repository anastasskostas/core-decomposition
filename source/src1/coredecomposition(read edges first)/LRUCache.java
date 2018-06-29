/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coredecomposition;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 *
 * @author Tasos
 * @param <K>
 * @param <V>
 */
public class LRUCache < K, V > extends LinkedHashMap < K, V >{
    private int capacity; // Maximum number of items in the cache.
     
    public LRUCache(int capacity) { 
        super(capacity+1, 1.0f, true); // Pass 'true' for accessOrder.
        this.capacity = capacity;
    }
     
    @Override
    protected boolean removeEldestEntry(Entry entry) {
        return (size() > this.capacity);
    } 
}
