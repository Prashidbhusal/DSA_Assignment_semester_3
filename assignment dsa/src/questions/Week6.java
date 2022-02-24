package questions;

public class Week6 {
    int matrix[][];
    int size;
    Week6(int size){
        this.matrix = new int[size][size];
        this.size = size;
    }
    public void addEdges(int source, int destination){
        matrix[source][destination] = 1;
        matrix[destination][source]= 1;
    }
    public void getCriticalPath(){
        int sum=0;
        int source=0;
        int destination=0;
        for(int i = 0; i<size;i++){
            for(int j=0; j<size;j++){
                sum += matrix[i][j];
                if(matrix[i][j]==1) {
                    source = j;
                    destination = i;
                }
            }
            if(sum==1){
                System.out.println("{"+source+","+destination+"}");
            }
            sum=0;
        }

    }
    public static void main(String []Args){
        Week6 obj = new Week6(10);
        obj.addEdges(0,1);
        obj.addEdges(0,2);
        obj.addEdges(0,3);
        obj.addEdges(1,6);
        obj.addEdges(2,3);
        obj.addEdges(2,5);
        obj.getCriticalPath();
    }
}
