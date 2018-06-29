/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coredecomposition;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Tasos
 */
public class CoreDecomposition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        boolean flag=true;
        if (flag==true){
            int nodes;
            int node1,node2;
            ArrayList<Integer> deg;
            ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

            try {
                /*====================== READ FIRST FILE ====================*/
                //String file_name = "./dataBig/LJ/soc-LiveJournal1.txt";
                //String file_name = "./dataBigNew/roadNet-TX.txt";
                //String file_name = "./dataBig/SE/file5-6/soc-Epinions1.txt";
                String file_name = "./dataBigNew/web-Google.txt";
                //String file_name = "./dataSmall/Core1Un.txt";
                //String file_name = "./dataMoreEdge/SE/soc-Epinions1.txt";
                
                ReadFile file = new ReadFile (file_name);
                graph=file.openFile();
                nodes=file.GetN();

                /*==================== RUN STATIC ALGORITHM ==================*/
                StaticAlgorithm staticAlg = new StaticAlgorithm(graph,nodes);
                deg = staticAlg.CreateStaticAlg();
                for (int l=1;l<deg.size();l++)
                    System.out.println(l+" "+deg.get(l));
                System.out.println("=======");


                /*====================== READ SECOND FILE ====================*/
                //file_name = "./dataBig/LJ/addEdgeLJ.txt";
                //file_name = "./dataBigNew/addEdgeTX.txt";
                //file_name = "./dataBig/SE/file5-6/addEdgeSE.txt";
                file_name = "./dataSmall/addEdge.txt";
                //file_name = "./dataBigNew/addEdgeBerk.txt";
                //file_name = "./dataMoreEdge/SE/addEdgeSE.txt";
                ReadFile file2 = new ReadFile (file_name);
                file2.openFile();
                node1=file2.GetN();
                node2=file2.GetM();

                /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
                /*======================== INSERT EDGE ======================*/
                /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

                if ((node1>nodes) || (node2>nodes)){
                    graph.add(new ArrayList<>());
                    deg.add(1);
                    nodes = nodes +1;
                }
                graph.get(node1).add(node2);
                graph.get(node2).add(node1);

                //==================== RUN DYNAMIC ALGORITHM ==================
                //DynamicAlgorithm dynamicAlg = new DynamicAlgorithm(graph,deg,nodes);
                //dynamicAlg.Insertion(node1,node2);



                /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
                /*======================== DELETE EDGE =====================*/
                /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

                /*graph.get(node1).remove(new Integer(node2));
                graph.get(node2).remove(new Integer(node1));

                //==================== RUN DYNAMIC ALGORITHM ==================
                DynamicAlgorithm dynamicAlg = new DynamicAlgorithm(graph,deg,nodes);
                dynamicAlg.Deletion(node1,node2);*/
            }

            catch (IOException e) {
                System.out.println( e.getMessage() );
            }
        }
        else{
            
        }
        
    }
    
}

            