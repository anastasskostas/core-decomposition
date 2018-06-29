/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coredecomposition;


import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
        
/**
 *
 * @author Tasos
 */
public class ReadFile {
    private String path;
    private int n,m;
    ArrayList<Integer> edge;

    public ReadFile(String file_path) {
        path=file_path;
        n=m=0;
        edge = new ArrayList<>();
    }
    
    public ArrayList<ArrayList<Integer>> openFile() throws IOException {
        FileReader fr = new FileReader(path);
        int graphSize;
        int flag;
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        
        
        String delims = "[ \t]+";
        String textData = null;
        String[] tokens;
        
        
        try (BufferedReader textReader = new BufferedReader(fr)) {
            graph.add(new ArrayList<>());
            flag = 0;
            graphSize=-1;
            int count=0;
            
            System.out.println("done");
            while(true){
                textData = textReader.readLine();                
                if (textData==null){
                    break;
                }
                tokens = textData.split(delims);
                if (tokens[0].equals("Undirected")){
                    flag = 1;
                    break;
                }    
                else if (tokens[0].equals("Directed")){
                    flag = 2;
                    break;
                }
                else if (tokens[0].equals("AddEdge")){
                    flag = 3;
                    break;
                }
                else if (tokens[0].equals("DeleteEdge")){
                    flag = 4;
                    break;
                }
                else if (tokens[0].equals("#")){
                    /*count++;
                    if (count>=4){
                        flag=2;
                        break;
                    }*/
                    //textData = textReader.readLine();
                    //tokens = textData.split(delims);
                }
            }
            
            if (flag == 1){
                while(true){
                    textData = textReader.readLine();
                    if (textData==null){
                        break;
                    }
                    tokens = textData.split(delims);     
                    n = Integer.parseInt(tokens[0]);
                    m = Integer.parseInt(tokens[1]);
                    
                    /*System.out.print(n+" "+m);
                    System.out.println();*/
                    
                    if (n>graphSize){
                        for (int j=graphSize;j<n;j++)
                            graph.add(new ArrayList<>());
                        graphSize=n;
                    }
                    graph.get(n+1).add(m+1);
                }
            }
            else if (flag == 2){
                System.out.println("done1");
                boolean check1;
                while(true){
                    textData = textReader.readLine();
                    if (textData==null){
                        break;
                    }
                    //System.out.println(textData);
                    tokens = textData.split(delims); 
                    n = Integer.parseInt(tokens[0]);
                    m = Integer.parseInt(tokens[1]);
                    
                    /*System.out.print(n+" "+m);
                    System.out.println();*/
                    /*if (n%1000000==0){
                        System.out.println(n+" "+m);
                    }*/
                    if (n>graphSize){
                        for (int j=graphSize;j<n;j++)
                            graph.add(new ArrayList<>());
                        graphSize=n;
                    }
                    if (m>graphSize){
                        for (int j=graphSize;j<m;j++)
                            graph.add(new ArrayList<>());
                        graphSize=m;
                    }
                    graph.get(n+1).add(m+1);
                    graph.get(m+1).add(n+1);
                }
            }
            else if (flag == 3){
                /*while(true){
                    textData = textReader.readLine();
                    if (textData==null){
                        break;
                    }
                    tokens = textData.split(delims); 
                    n = Integer.parseInt(tokens[0]);
                    m = Integer.parseInt(tokens[1]);
                    graph.get(0).add(n+1);
                    graph.get(0).add(m+1);
                }*/
                
                int pos;
                while(true){
                    textData = textReader.readLine();
                    if (textData==null){
                        break;
                    }
                    tokens = textData.split(delims); 
                    n = Integer.parseInt(tokens[0]);
                    m = Integer.parseInt(tokens[1]);
                    n=n+1;
                    m=m+1;
                    if (!edge.contains(n)){
                        edge.add(n);
                        graph.add(new ArrayList<>());
                    }
                    pos = edge.indexOf(n);
                    graph.get(pos).add(m);
                    
                    if (!edge.contains(m)){
                        edge.add(m);
                        graph.add(new ArrayList<>());
                    }
                    pos = edge.indexOf(m);
                    graph.get(pos).add(n);
                    
                    //graph.get(0).add(n);
                    //graph.get(0).add(m);
                }
            }
            
            /*if (flag == 0){
                //n=n+1;
                //m=m+1;
            }
            else {
                n=graphSize+1;
                m=m+1;
            }*/
            System.out.println("done3");
            return graph;
        }
            
    }
    
    public int GetN(){
        return n;
    }
    
    public int GetM(){
        return m;
    }
    
    public ArrayList<Integer> GetEdgeList(){
        return edge;
    }
    
    /*int Readlines() throws IOException {
        FileReader file_to_read = new FileReader(path);
        BufferedReader bf = new BufferedReader(file_to_read);
        
        String aLine;
        int numberOfLines = 0;
        
        while (( aLine = bf.readLine()) != null) {
            numberOfLines++;
        }
        bf.close();
        
        return numberOfLines;
    }*/
}

