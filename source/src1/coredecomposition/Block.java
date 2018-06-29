/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coredecomposition;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Tasos
 */
public class Block {
    private final ArrayList<ArrayList<Integer>> graph;
    int number;
    int size;
    
    
    public Block(ArrayList<ArrayList<Integer>> graph){
        this.graph = graph;
        
    }
    
    void write(RandomAccessFile raf) throws IOException{
        ArrayList<Index> allNodes;
        allNodes=new ArrayList<>();
        int table[] = new int[2];
        int totalBlocks=0;
        int totalBlockss=0;
        int count=0;
        ArrayList<Integer> numOfBlocksForEachVertex;
        numOfBlocksForEachVertex = new ArrayList<>();
        for (int i=1;i<graph.size();i++){
            totalBlocks=0;
            for (int j=0;j<graph.get(i).size();j++){
                /*if (count<blockSize){
                    raf.writeInt(graph.get(i).get(j));
                    count++;
                }
                else{
                    totalBlocks++;
                    //totalBlockss++;
                    count=0;
                    raf.writeInt(graph.get(i).get(j));
                    count++;
                }*/

                /*while (count<blockSize){
                    raf.writeInt(graph.get(i).get(j));
                    count++;
                }
                totalBlocks++;
                count=0;*/

                if (count<table.length){
                    table[count]=graph.get(i).get(j);
                    count++;
                 }
                 else{
                    for (int k=0;k<table.length;k++){
                        raf.writeInt(table[k]);
                    }
                    totalBlocks++;
                    count=0;
                    table[count]=graph.get(i).get(j);
                    count++;
                 }
            }
            for (int k=0;k<table.length;k++){
                raf.writeInt(table[k]);
            }
            totalBlocks++;

            count=0;
            Index strin;
            //strin = new Index(i,totalBlockss,totalBlocks);
            
            
            //allNodes.add(strin);
            totalBlockss=totalBlockss+totalBlocks;
        }
        Index st;
        //st = new Index(1,2,3);
        //st.WriteIndex(allNodes);
    }
    
    void read(RandomAccessFile raf) throws IOException{
        
    }
}
