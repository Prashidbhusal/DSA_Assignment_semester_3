package Week8to11;
public class Graph {
    int vertices;
    int[][] adjacencyMatrix;

    // Initialize the matrix
    Graph(int v){
        vertices=v;
        adjacencyMatrix =new int[vertices][vertices];
    }

    // Add edges
    void addEdge(int source,int destination,int distance) {
        adjacencyMatrix[source][destination]=distance;
        adjacencyMatrix[destination][source]=distance;
    }

    // Remove edges
    void removeEdge(int source,int destination) {
        adjacencyMatrix[source][destination]=0;
        adjacencyMatrix[destination][source]=0;
    }

//    Dijkstra Algorithm
    public int[] shortestPath(int source) {
        boolean[] visited =new boolean[vertices];
        int[] minDistance =new int[vertices];
        int[] prevPath =new int[vertices];
        for(int i=0;i<vertices;i++) {
            //Initialize all the distance to max value
            minDistance[i]=Integer.MAX_VALUE;
            prevPath[i]=-1;
        }
        //start from the vertex 0
        minDistance[source]=0;

        //create SPT
        for(int i = 0; i< adjacencyMatrix.length; i++) {
            //get the vertex with the minimum distance
            int minVertex= findMinVertex(minDistance,visited);
            //include this vertex in SPT
            visited[minVertex]=true;
            //iterate through all the adjacent vertices of above vertex and update the keys
            for(int j = 0; j< adjacencyMatrix.length; j++) {
                //check of the edge between vertex_U and vertex_V
                if(adjacencyMatrix[minVertex][j]!=0 && !visited[j]) {
                    //check if this vertex 'vertex_V' already in spt and
                    // if distance[vertex_V]!=Infinity
                    int newDistance=minDistance[minVertex]+ adjacencyMatrix[minVertex][j];
                    if(newDistance<minDistance[j]) {
                        minDistance[j]=newDistance;
                        prevPath[j]=minVertex;

                    }
                }
            }
        }
        return prevPath;

    }
    public int distance(int source, int destination) {
        boolean[] visited =new boolean[vertices];
        int[] minDistance =new int[vertices];
        int[] prevPath =new int[vertices];
        for(int i=0;i<vertices;i++) {
            //Initialize all the distance to max value
            minDistance[i]=Integer.MAX_VALUE;
            prevPath[i]=-1;
        }
        //start from the vertex 0
        minDistance[source]=0;

        //create SPT
        for(int i = 0; i< adjacencyMatrix.length; i++) {
            //get the vertex with the minimum distance
            int minVertex= findMinVertex(minDistance,visited);
            //include this vertex in SPT
            visited[minVertex]=true;
            //iterate through all the adjacent vertices of above vertex and update the keys
            for(int j = 0; j< adjacencyMatrix.length; j++) {
                //check of the edge between vertex_U and vertex_V
                if(adjacencyMatrix[minVertex][j]!=0 && !visited[j]) {
                    //check if this vertex 'vertex_V' already in spt and
                    // if distance[vertex_V]!=Infinity
                    int newDistance=minDistance[minVertex]+ adjacencyMatrix[minVertex][j];
                    if(newDistance<minDistance[j]) {
                        minDistance[j]=newDistance;
                        prevPath[j]=minVertex;

                    }
                }
            }
        }
        int shortdistance=minDistance[destination];
        return shortdistance;
    }

    public int findMinVertex(int[] minDistance, boolean[] visited) {
        int minVertex=-1;
        for(int i=0;i<minDistance.length;i++) {

            if(!visited[i] && (minVertex==-1 || minDistance[i]<minDistance[minVertex])) {
                //check if distance needs an update or not
                //means check total weight from source to vertex_V is less than
                //the current distance value, if yes then update the distance
                minVertex=i;
            }
        }
        return minVertex;
    }

}