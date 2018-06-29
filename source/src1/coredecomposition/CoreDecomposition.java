/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coredecomposition;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Tasos
 */
public class CoreDecomposition {
    static int blockSize = 512;
    static int limit = 20;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        boolean flag=false;
        if (flag==true){
            int nodes;
            int node1,node2;
            ArrayList<Integer> core;
            ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

            try {
                //====================== READ FIRST FILE ====================//
                //String file_name = "./dataBig/SE/file3-4/soc-Epinions1.txt";
                //String file_name = "./dataBig/EE/Email-Enron1.txt";
                //String file_name = "./dataSmall/Core1Un.txt";
                //String file_name = "./dataBig/LJ/soc-LiveJournal1.txt";
                //String file_name = "./dataBigNew/web-BerkStan.txt";
                //String file_name = "./dataBigNew/Email-EuAll.txt";
                //String file_name = "./dataBig/SE/file5-6/soc-Epinions1.txt";
                //String file_name = "./dataMoreEdges/SE/soc-Epinions1.txt";
                //String file_name = "./dataMoreEdges/SE1/soc-Epinions1.txt";
                //String file_name = "./dataMoreEdges/small/core1Un.txt";
                //String file_name = "./dataMoreEdges/small1/core1Un.txt";
               
                
                String file_name = "./dataMoreEdges/experiments/temp4/web-Stanford.txt";
                ReadFile file = new ReadFile (file_name);
                graph=file.openFile();
                //nodes=file.GetN();
                nodes = graph.size()-1;
                
                //==================== RUN STATIC ALGORITHM ==================//
                StaticAlgorithm staticAlg = new StaticAlgorithm(graph,nodes);
                core = staticAlg.CreateStaticAlg();
                for (int l=1;l<core.size();l++)
                    System.out.println(l+" "+core.get(l));
                System.out.println("=======");
                
                //======================== CREATE FILES ======================//
                CreateIndexAndGraph(graph,core);
                
            }
            
            catch (IOException e) {
                System.out.println( e.getMessage() );
            }
        }
        else{
            //String file_name = "./dataBig/SE/file3-4/addEdgeSE.txt";
            //String file_name = "./dataBig/EE/addEdgeEE.txt";
            //String file_name = "./dataBigNew/addEdgeBerk.txt";
            //String file_name = "./dataBigNew/addEdgeEmail.txt";
            //String file_name = "./dataBig/EE/addEdgeEE.txt";
            //String file_name = "./dataBig/SE/file5-6/addEdgeSE.txt";
            //String file_name = "./dataSmall/addEdge.txt";
            //String file_name = "./dataMoreEdges/SE/addEdgeSE.txt";
            //String file_name = "./dataMoreEdges/SE1/addEdgeSE.txt";
            //String file_name = "./dataMoreEdges/small/addEdge.txt";
            //String file_name = "./dataMoreEdges/small1/addEdge.txt";
            
            
            String file_name = "./dataMoreEdges/experiments/temp4/addEdge1000.txt";
            
            
            ReadFile file2 = new ReadFile (file_name);
            ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
            graph=file2.openFile();
            
            //==================== RUN DYNAMIC ALGORITHM ==================//
            DynamicAlgorithm dynamicAlg = new DynamicAlgorithm();
            dynamicAlg.Insertion(graph);
            /*for (int temp=0;temp<graph.get(0).size();temp=temp+2){
                int node1=graph.get(0).get(temp);
                int node2=graph.get(0).get(temp+1);
                DynamicAlgorithm dynamicAlg = new DynamicAlgorithm();
                dynamicAlg.Insertion(node1,node2);
            }*/
            
        }
        
    }
    
    private static void CreateIndexAndGraph(ArrayList<ArrayList<Integer>> graph, ArrayList<Integer> core) throws IOException{
        ArrayList<Index> IndexForAllNodes;
        IndexForAllNodes=new ArrayList<>();
        Index temp;
        temp = new Index(0,0,0,0,0,0,0);
        IndexForAllNodes.add(temp);
        RandomAccessFile raf = new RandomAccessFile("C:/Users/Tasos/Desktop/CoreDecomposition_1/Graph", "rw");
        raf.setLength(0);
        int writeIO=0;
        
        int bufferSize=1;
        int table[] = new int[blockSize];
        int totalBlocksPerNode=0;
        int blockPosition=0;
        int reserved=0;
        int records=0;
        int blocksPerNode=0;

        ArrayList<Integer> numOfBlocksForEachVertex;
        numOfBlocksForEachVertex = new ArrayList<>();
        for (int i=1;i<graph.size();i++){
            if (i%100000==0){
                System.out.println(i);
            }
            for (int j=0;j<graph.get(i).size();j++){
                if (reserved < blockSize){
                    table[reserved]=graph.get(i).get(j);
                    reserved++;
                    records++;
                 }
                 else{
                    for (int k=0;k < blockSize;k++){
                        raf.writeInt(table[k]);
                    }
                    blocksPerNode++;
                    writeIO++;
                    reserved=0;
                    table[reserved]=graph.get(i).get(j);
                    reserved++;
                    records++;
                 }
            }
            for (int k=0;k<reserved;k++){
                raf.writeInt(table[k]);
            }
            if (reserved!=0){
                writeIO++;
            }
            /*else{
                System.out.println(i+" dsaddddddddsadad: "+blocksPerNode );
            }*/
            
            
            if (blockSize-reserved < limit){
                raf.seek(raf.getFilePointer()+((blockSize-reserved)*4)+(blockSize*bufferSize*4));
                blocksPerNode++;
                totalBlocksPerNode=blocksPerNode+bufferSize;
                System.out.println(i +":"+ (blockSize-reserved));
            }
            else{
                raf.seek(raf.getFilePointer()+((blockSize-reserved)*4)+(blockSize*(bufferSize-1)*4)); // rest for last block
                blocksPerNode++;
                totalBlocksPerNode=blocksPerNode+bufferSize-1;
            }
            
            
            
            /*raf.seek(raf.getFilePointer()+((blockSize-reserved)*4)+(blockSize*bufferSize*4));
            blocksPerNode++;
            if (graph.get(i).size()==0){
                blocksPerNode--;
            }
            //raf.seek(raf.getFilePointer()+(blockSize-reserved)*4); // rest for last block
            //raf.seek(raf.getFilePointer()+blockSize*bufferSize*4); // buffer blocks*/
            
            //totalBlocksPerNode=blocksPerNode+bufferSize;
            
            
            
            /*raf.seek(raf.getFilePointer()+(blockSize-reserved)*4); // rest for last block
            blocksPerNode++;
            bufferSize=0;*/
            
            /*for (int k=reserved;k < blockSize;k++){
                raf.writeInt(-1);
            }
            blocksPerNode++;
            for (int k=0;k<bufferSize*blockSize;k++){
                raf.writeInt(-1);
            }*/
            
            
            

            Index strin;
            strin = new Index(i,blockPosition,blocksPerNode,totalBlocksPerNode,reserved,records,core.get(i));

            blockPosition=blockPosition+totalBlocksPerNode;

            IndexForAllNodes.add(strin);
            reserved=0;
            records=0;
            totalBlocksPerNode=0;
            blocksPerNode=0;
            
        }
        raf.close();
        System.out.println("I/Os: "+ writeIO);

        try{
            FileOutputStream fos= new FileOutputStream("C:/Users/Tasos/Desktop/CoreDecomposition_1/Index.bin");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(IndexForAllNodes);
            oos.close();
            fos.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}

                /*==================== RUN STATIC ALGORITHM ==================*/
                /*StaticAlgorithm staticAlg = new StaticAlgorithm(graph,nodes);
                deg = staticAlg.CreateStaticAlg();
                for (int l=1;l<deg.size();l++)
                    System.out.println(l+" "+deg.get(l));
                System.out.println("=======");*/


                /*====================== READ SECOND FILE ====================*/
                /*file_name = "./dataBig/LJ/addEdgeLJ.txt";
                ReadFile file2 = new ReadFile (file_name);
                file2.openFile();
                node1=file2.GetN();
                node2=file2.GetM();*/

                /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
                /*======================== INSERT EDGE ======================*/
                /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

                /*if ((node1>nodes) || (node2>nodes)){
                    graph.add(new ArrayList<>());
                    deg.add(1);
                    nodes = nodes +1;
                }
                graph.get(node1).add(node2);
                graph.get(node2).add(node1);

                //==================== RUN DYNAMIC ALGORITHM ==================
                DynamicAlgorithm dynamicAlg = new DynamicAlgorithm(graph,deg,nodes);
                dynamicAlg.Insertion(node1,node2);*/



                /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
                /*======================== DELETE EDGE =====================*/
                /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

                /*graph.get(node1).remove(new Integer(node2));
                graph.get(node2).remove(new Integer(node1));

                //==================== RUN DYNAMIC ALGORITHM ==================
                DynamicAlgorithm dynamicAlg = new DynamicAlgorithm(graph,deg,nodes);
                dynamicAlg.Deletion(node1,node2);*/