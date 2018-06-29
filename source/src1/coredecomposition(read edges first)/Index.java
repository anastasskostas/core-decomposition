/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coredecomposition;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Tasos
 */
public class Index implements Serializable{
    int id;
    int blockPosition;
    int blocksPerNode;
    int totalBlocksPerNode;
    int reserved;
    int core;
    int records;
    
    Index(int id,int blockPosition,int blocksPerNode,int totalBlocksPerNode,int reserved,int records,int core){
        this.id = id;
        this.blockPosition = blockPosition;
        this.blocksPerNode = blocksPerNode;
        this.totalBlocksPerNode = totalBlocksPerNode;
        this.reserved = reserved;
        this.records = records;
        this.core = core;
    }
    
    void WriteIndex(ArrayList allNodes){
        try{
                FileOutputStream fos= new FileOutputStream("C:/Users/Tasos/Desktop/myfile.bin");
                ObjectOutputStream oos= new ObjectOutputStream(fos);
                oos.writeObject(allNodes);
                oos.close();
                fos.close();
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }
    }
}
