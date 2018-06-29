/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coredecomposition;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Tasos
 */
public class RandomAccessFile {
    private ArrayList<ArrayList<Integer>> graph;
    private ArrayList<Integer> deg;
    private final ArrayList<Integer> V;
    private final ArrayList<Integer>visitedd;
    private final ArrayList<Integer>colorr;
    private final java.io.RandomAccessFile raf;
    ArrayList<Integer> neighbours;
    private ArrayList<Index> IndexForAllNodes;
    private int nodes;
    private int[] visited;
    private int[] color;
    private int blockAccessToWrite;
    private int blockAccessToRead;    
    
    
    public RandomAccessFile() throws FileNotFoundException{
        //this.graph = graph;
        //this.nodes = nodes;
        //this.deg = deg;
        V = new ArrayList<>();
        visitedd = new ArrayList<>();
        colorr = new ArrayList<>();
        raf = new java.io.RandomAccessFile("C:/Users/Tasos/Desktop/CoreDecomposition_1/RAF1", "rw");
        IndexForAllNodes=new ArrayList<>();
        neighbours=new ArrayList<>();
        blockAccessToWrite=0;
        blockAccessToRead=0;
    }
    
    public ArrayList ReadIndex(){
        try
        {
            FileInputStream fis = new FileInputStream("C:/Users/Tasos/Desktop/CoreDecomposition_1/myfile.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            IndexForAllNodes = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
            return IndexForAllNodes;
        }catch(IOException ioe){
            ioe.printStackTrace();
            return IndexForAllNodes;
        }catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
            return IndexForAllNodes;
        }
    }
    
    public void WriteIndex(){
        try{
            FileOutputStream fos= new FileOutputStream("C:/Users/Tasos/Desktop/CoreDecomposition_1/myfile.bin");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(IndexForAllNodes);
            oos.close();
            fos.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    public void WriteNewEdge(int node1,int node2) throws IOException{
        int position=IndexForAllNodes.get(node1).blockPosition;
        raf.seek(position*2*4 + IndexForAllNodes.get(node1).totalBlocksPerNode*2*4 + IndexForAllNodes.get(node1).reserved*4);
        //raf.seek(position*2*4+(IndexForAllNodes.get(node1).totalBlocksPerNode-6)*2*4+IndexForAllNodes.get(node1).reserved*4);
        //raf.seek(position*2*4+IndexForAllNodes.get(node1).records*2*4);
        blockAccessToWrite++;
        raf.writeInt(node2);
        if (IndexForAllNodes.get(node1).reserved==2){
            //IndexForAllNodes.get(node).totalBlocksPerNode++;
            IndexForAllNodes.get(node1).reserved=0;
        }
        else {
            IndexForAllNodes.get(node1).reserved++;
        }
    }
    
    public void ReadNeighbours(int temp) throws IOException{
        int pos=IndexForAllNodes.get(temp).blockPosition;
        raf.seek(pos*2*4);
        blockAccessToRead++;
        for (int i=0;i<IndexForAllNodes.get(temp).totalBlocksPerNode*2;i++){
            int number=raf.readInt();
            if (number!=-1){
                neighbours.add(number);
            }
            else{
                break;
            }
        }
    }
    
}
