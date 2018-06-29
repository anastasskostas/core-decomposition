/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coredecomposition;

import java.util.ArrayList;

/**
 *
 * @author Tasos
 */
public class StaticAlgorithm {
    private final ArrayList<ArrayList<Integer>> graph;
    private int nodes;
    private ArrayList<Integer> deg;
    //private int[] deg;
    private int[] bin;
    private int[] pos;
    private int[] vert;
    private int n,d,md,i,start,num;
    private int v,u,w,du,pu,pw;
    
    public StaticAlgorithm(ArrayList<ArrayList<Integer>> graph,int nodes){
        this.graph = graph;
        this.nodes = nodes;
        
    }
    
    public ArrayList<Integer> CreateStaticAlg(){
        //deg = new int[nodes+1];
        deg = new ArrayList<>();
        n=graph.size()-1;                   //line 9
        md=0;
        deg.add(-1);
        for (int v=1;v<=n;v++){             //line 10
            d=graph.get(v).size();
            deg.add(d);//deg[v] = d;
            if (d > md)
                md=d;
        }
        bin = new int[md+1];                //line 16
        for (d=0;d<=md;d++)
            bin[d] = 0;
        for (v=1;v<=n;v++){
            bin[deg.get(v)] = bin[deg.get(v)]+1;//bin[deg[v]] = bin[deg[v]] +1;
        }
        
        start = 1;                          // line 18
        for (d=0;d<=md;d++){
            num = bin[d];
            bin[d] = start;
            start = start + num;
        }
        pos = new int[nodes+1];
        vert = new int[nodes+1];
        for (v=1;v<=n;v++){                 //line 24
            pos[v] = bin[deg.get(v)];//pos[v] = bin[deg[v]];
            vert[pos[v]] = v;
            bin[deg.get(v)] = bin[deg.get(v)]+1;//bin[deg[v]] = bin[deg[v]]+1;
        }
        
        for (d=md;d>=1;d--){                //line 29
            bin[d] = bin[d-1];
        }
        bin[0]=1;
        
        for (i=1;i<=n;i++){                 //line 32
            v = vert[i];
            for (int j=0;j<graph.get(v).size();j++){
                u=graph.get(v).get(j);
                if (deg.get(u)>deg.get(v)){//if (deg[u] > deg[v]){
                    du = deg.get(u);//du = deg[u];
                    pu = pos[u];
                    pw = bin[du];
                    w = vert[pw];
                    if (u!=w){              //line 40
                        pos[u] = pw;
                        pos[w] = pu;
                        vert[pu] = w;
                        vert[pw] = u;
                    }
                    bin[du] = bin[du]+1;
                    //deg[u] = deg[u]-1;
                    deg.set(u, deg.get(u)-1);
                }
            }
        }
        
        
        /*for (v=0;v<=md;v++){
            System.out.println(bin[v]);
        }*/
        /*for (v=1;v<=n;v++){
            System.out.print(v+" "+vert[v]+" "+pos[v]);
            System.out.println();
        }*/
        /*for (v=1;v<=n;v++){
            System.out.print(v+" "+deg.get(v));
            System.out.println();
        }*/
        
        return deg;
    }
}
