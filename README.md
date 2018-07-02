<h2>Core decomposition for dynamic disk-resident graphs in Big Data.</h2>
<h3>Thesis project</h3>

Efficient decomposition of the graph is one of the methods that can bring important information and data for the relationship between users in a large graph.

There are 3 sets

<b>src0:</b> The first attempt for the core decomposition project
1. Static algorithm
2. Dynamic algorithm in ram

<b>src1:</b> The second and finalized attempt for the core decomposition project, we have:
1. Static algorithm
2. Dynamic algorithm in disk using LRU Cache
The purpose of this part was to achieve efficient decomposition of the graph using the core numbers of the nodes as a tool, having as main priority the I/Os in the secondary memory. 
<p><u>Note:</u> We insert/remove one edge each time with no optimization</p>

<b>src2:</b> The third attempt for the core decomposition project
1. Static algorithm
2. Dynamic algorithm in disk using LRU Cache
<p><u>Note:</u> Optimization of dynamic algorithm. Insert/Remove group of many edges</p>

The algorithm was tested in Big Data graphs from Stanford, Google, BerkStan, etc..
