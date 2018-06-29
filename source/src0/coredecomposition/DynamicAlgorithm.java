/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coredecomposition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Tasos
 */
public class DynamicAlgorithm {
    private final ArrayList<ArrayList<Integer>> graph;
    private ArrayList<Integer> deg;
    private ArrayList<Integer> V;
    private int nodes;
    private int[] visited;
    private int[] color;
    
    
    public DynamicAlgorithm(ArrayList<ArrayList<Integer>> graph,ArrayList<Integer> deg,int nodes){
        this.graph = graph;
        this.nodes = nodes;
        this.deg = deg;
        V = new ArrayList<>();
    }
    
    
    public void Insertion(int u,int v){
        int core;
        visited = new int[nodes+1];
        color = new int[nodes+1];
        
        if (deg.get(u)>deg.get(v)){
            core = deg.get(v);
            Color(v,core);//XPruneColor(v,core);//YPruneColor(v,core);
            RecolorInsert(core);
            UpdateInsert(core);
        }
        else{
            core = deg.get(u);
            Color(u,core);//XPruneColor(u,core);//YPruneColor(u,core);
            RecolorInsert(core);
            UpdateInsert(core);
        }
        
    }
    
    
    public void Deletion(int u,int v){
        int core;
        visited = new int[nodes+1];
        color = new int[nodes+1];
       
        if (deg.get(u)>deg.get(v)){
            core = deg.get(v);
            Color(v,core);//XPruneColor(v,core);//YPruneColor(v,core);
            RecolorDelete(core);
            UpdateDelete(core);
        }
        if (deg.get(u)<deg.get(v)){
            core = deg.get(u);
            Color(u,core);//XPruneColor(u,core);//YPruneColor(u,core);
            RecolorDelete(core);
            UpdateDelete(core);
        }
        if (deg.get(u) == deg.get(v)){
            core = deg.get(u);
            Color(u,core);//XPruneColor(u,core);//YPruneColor(u,core);
            if (color[v]==0){
                for (int i=0;i<=nodes;i++){
                    visited[i]=0;
                }
                Color(v,core);//XPruneColor(v,core);//YPruneColor(v,core);
                RecolorDelete(core);
                UpdateDelete(core);
            }
            else{
                RecolorDelete(core);
                UpdateDelete(core);
            }
        }
        
    }
    
    public void Color(int node,int c){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(node);
        visited[node]=1;
        while (!queue.isEmpty()){
            node = queue.remove();
            int nodeTotalNeighbors = graph.get(node).size();
            for (int j=0;j<nodeTotalNeighbors;j++){
                int neighbor = graph.get(node).get(j);
                if ((visited[neighbor] == 0) && (deg.get(neighbor) == c)){
                    queue.add(neighbor);
                    visited[neighbor] = 1;
                }
            }
            if (color[node]==0){
                V.add(node);
                color[node]=1;
            }
        }
    }
    
    public void RecolorInsert(int c){
        boolean flag = false;
        for (int j=0;j<V.size();j++){
            int u = V.get(j);
            if (color[u] == 1){
                int X=0;
                for (int k=0;k<graph.get(u).size();k++){
                    int w = graph.get(u).get(k);
                    if ((color[w]==1) || (deg.get(w)>c)){
                        X = X +1;
                    }
                }
                if (X<=c){
                    color[u] = 0;
                    flag = true;
                }
            }
        }
        if (flag == true){
            RecolorInsert(c);
        }
    }
    
    public void UpdateInsert(int c){
        for (int j=0;j<V.size();j++){
            int w = V.get(j);
            if (color[w]==1){
                int increaseC = c+1;
                deg.set(w,increaseC);
            }
        }
        for (int l=1;l<deg.size();l++)
            System.out.println(l+" "+deg.get(l));
    }
    
    public void RecolorDelete(int c){
        boolean flag = false;
        for (int j=0;j<V.size();j++){
            int u = V.get(j);
            if (color[u] == 1){
                int X=0;
                for (int k=0;k<graph.get(u).size();k++){
                    int w = graph.get(u).get(k);
                    if ((color[w]==1) || (deg.get(w)>c)){
                        X = X +1;
                    }
                }
                if (X<c){
                    color[u] = 0;
                    flag = true;
                }
            }
        }
        if (flag == true){
            RecolorDelete(c);
        }
    }
    
    public void UpdateDelete(int c){
        for (int j=0;j<V.size();j++){
            int w = V.get(j);
            if (color[w]==0){
                int decreaseC = c-1;
                deg.set(w,decreaseC);
            }
        }
        for (int l=1;l<deg.size();l++)
            System.out.println(l+" "+deg.get(l));
    }
    
    
    /* To implement this pruning strategy in Insertion Algorithm, we can 
    replace the Color algorithm with the XPruneColor algorithm.*/
    
    /*Second, we consider the edge deletion case. Suppose
that we delete an edge (u0, v0) from graph G
and the core numbers of all the nodes in Vc are c.
We consider three different cases: (1) Cu0 > Cv0 , (2)
Cu0 < Cv0 , and (3) Cu0 = Cv0 . For Cu0 > Cv0 , we only
need to find the nodes in Gv0 , because the deletion of
edge (u0, v0) does not affect the core numbers of the
nodes in Gu0 . Recall that after deleting an edge, the
core numbers of the nodes in Vc decrease by at most 1.
Therefore, after deleting an edge (u0, v0), if Xv0
≥ c,
then v0’s core number will not be changed. This is
because Xv0
≥ c implies v0 has at least c neighbors
whose core numbers are larger than or equal to c.
That is to say, the core number of node v0 is still
c. Since v0’s core number does not change, we do
not need to update the core numbers of the nodes
in Gv0 . As a result, under the case of Cu > Cv in
Algorithm 5 (line 4 in Algorithm 5), we can first
compute Xv. If Xv ≥ c, we do nothing. Symmetrically,
for Cu0 < Cv0 , we have a similar pruning rule as the
case of Cu0 > Cv0 . For Cu0 = Cv0 , we first compute
Xu0 and Xv0. If Xu0 < c, then we need to update the 
    core numbers of the nodes in Gu0 . Also, if Xv0 < c,
we update the core numbers of the nodes in Gv0 . For
the case that Xu0
≥ c and Xv0
≥ c, we do nothing,
because no node’s core number needs to be updated.
It is worth mentioning that Xu0 and Xv0 are computed
based on the core numbers of the nodes that have not
been updated. The detailed algorithm with X-pruning
for the edge deletion case can be easily implemented,
we thus omit it for brevity.*/
    
    
    
    public void XPruneColor(int node,int c){
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(node);
        visited[node]=1;
        while (!queue.isEmpty()){
            node = queue.remove();
            int X = ComputeX(node);
            if (X>c){
                int nodeTotalNeighbors = graph.get(node).size();
                for (int j=0;j<nodeTotalNeighbors;j++){
                    int neighbor = graph.get(node).get(j);
                    if ((visited[neighbor] == 0) && (deg.get(neighbor) == c)){
                        queue.add(neighbor);
                        visited[neighbor] = 1;
                    }
                }
                if (color[node]==0){
                    V.add(node);
                    color[node]=1;
                }
            }
            
        }
    }
    
    
    /*First, to integrate the YPruneColor algorithm into the Insertion algorithm,
    we need to replace the Color algorithm with the YPruneColor algorithm as well as
    handle the following special case. 
    1) That is, if Cu0 = Cv0 = c, Yu0 = c and Yv0 < c, we need to invoke
    YPruneColor(G, v0, c). 
    2) If Cu0 = Cv0 = c, Yu0 < c and Yv0 = c, we have to invoke YPruneColor(G, u0, c).
    The reason is because we need to allow the BFS algorithm to go through the 
    edge (u0, v0) in order to add both u0 and v0 into Vc. 
    3) If Cu0 = Cv0 = c and Yu0 = Yv0 = c, then we have to invoke both 
    YPruneColor(G, u0, c) and YPruneColor(G, v0, c) so as to add both u0 and v0 into Vc.*/
    
    /*Second, to integrate the YPruneColor algorithm into the Deletion algorithm, 
    we only need to replace the Color algorithm with the YPruneColor algorithm.*/
    
    public void YPruneColor(int node,int c){
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(node);
        visited[node]=1;
        while (!queue.isEmpty()){
            node = queue.remove();
            int Y = ComputeY(node);
            if (Y<c){
                int nodeTotalNeighbors = graph.get(node).size();
                for (int j=0;j<nodeTotalNeighbors;j++){
                    int neighbor = graph.get(node).get(j);
                    if ((visited[neighbor] == 0) && (deg.get(neighbor) == c)){
                        queue.add(neighbor);
                        visited[neighbor] = 1;
                    }
                }
            }
            if (color[node]==0){
                V.add(node);
                color[node]=1;
            }
        }
    }
    
    
    /* Xv = |{u : u ∈ N(v), Cu ≥ Cv}| */
    public int ComputeX(int node){
        int prune=0;
        for (int k=0;k<graph.get(node).size();k++){
            int w = graph.get(node).get(k);
            if (deg.get(w)>=deg.get(node)){
                prune = prune +1;
            }
        }
        return prune;
    }
    
    /* Yv = |{u : u ∈ N(v), Cu > Cv}|.*/
    public int ComputeY(int node){
        int prune=0;
        for (int k=0;k<graph.get(node).size();k++){
            int w = graph.get(node).get(k);
            if (deg.get(w)>deg.get(node)){
                prune = prune +1;
            }
        }
        return prune;
    }
}

/*Combination of X-pruning and Y -pruning: Here
we discuss how to combine both X-pruning and Y -
pruning for edge insertion case and edge deletion
case, respectively. For edge insertion case, we can
integrate both X-pruning and Y -pruning into the
coloring procedure. Specifically, in the coloring procedure,
when the BFS algorithm visits a node u, we
calculate both Xu and Yu. Then, we use the X-pruning
rule to determine the color of node u, and make use
of both X-pruning and Y -pruning rules to determine
whether the algorithm visits u’s neighbors or not. For
the edge deletion case, we can easily integrate both Xpruning
and Y -pruning via the following two steps.
First, we replace the Color algorithm in Deletion with
the YPruneColor algorithm. Second, we integrate the
X-pruning rule into the Deletion algorithm.*/
