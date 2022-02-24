package questions;
import java.util.Arrays;

public class week5 {

    //sorting matrix in ascending order and storing the elements in a one dimensional array
    public static int[] sortMartix(int mat[][]) {

//pos to keep track of elements in the array
        int pos=0;
//creating an array of specific length to fit exactly all the elements of the matrix
        int temp[]=new int[(mat.length)*(mat[0].length)];

//filling the array with elements of matrix
        for(int i=0;i<mat.length;i++) {
            for(int j=0;j<mat[0].length;j++) {
                temp[pos] = mat[i][j];
                pos++;

            }
        }

//sorting the array
        Arrays.sort(temp);





        return temp;
    }

    public static int[][] assignRank(int mat[][],int sortedMatrixArray[]) {
//creating rankMatrix of same size as that of the input matrix
        int rankMat[][]=new int[mat.length][mat[0].length];
//giving default value of 0 'zero' to all the elements of rankMatrix since the rank starts with 1
//in order to increment later
        for(int i=0;i<rankMat.length;i++) {
            for(int j=0;j<rankMat[0].length;j++) {
                rankMat[i][j]=0;
            }
        }

//looping through each element in the sorted array one by one
        for(int x=0;x<sortedMatrixArray.length;x++) {

//for each element in the sorted array we will fine the rank for it
            int rank= calculateRank(sortedMatrixArray[x],rankMat,mat);

//after finding the rank of a certain number we will fill the rank in its correct position in the rankMatrix
//exactly corresponding to the position of the value in the input matrix
            for(int i=0;i<rankMat.length;i++) {
                for(int j=0;j<rankMat[0].length;j++) {
                    if(sortedMatrixArray[x]==mat[i][j]) {
                        rankMat[i][j]=rank;
                    }
                }
            }


        }

        return rankMat;
    }

    public static int calculateRank(int value,int rankMat[][],int mat[][]) {
//giving default values of count and max to zero
        int count=0;
        int max=0;

//looping through the input matrix to find compare with value and calculate its rank
        for(int i=0;i<mat.length;i++) {
            for(int j=0;j<mat[0].length;j++) {


//if value is found in the input matrix and the existing rank is zero then it means
// we are encountering that specific value for the first time
                if(value==mat[i][j] && rankMat[i][j]==0) {

//looping through that associated column only
                    for(int col=0;col<rankMat[0].length;col++) {

//finding the max rank in the column
                        if(max<=rankMat[i][col]) {
                            max=rankMat[i][col];
                        }

                    }

//looping through that associated row only
                    for(int row=0;row<rankMat.length;row++) {

//finding the max rank in that row
                        if(max<=rankMat[row][j]) {
                            max=rankMat[row][j];
                        }
                    }

//in case of multiple numbers of same value the count value will increase
//this will prevent the max rank from increasing and it will only increase once and stop incresing
// this helps us in giving the same rank to the similar values in the same row and column
                    if(count==0) {




                        max++;
                    }
                }
//if value is found in the input matrix but the existing rank is not zero then it means
// we have already encountered that specific value
                else if(value==mat[i][j] && rankMat[i][j]>0){

//in that case we simply find the max value in the related row and column which will
//be the rank of that duplicate number we encountered
                    for(int col=0;col<rankMat[0].length;col++) {


                        if(max<=rankMat[i][col]) {
                            max=rankMat[i][col];
                        }

                    }

                    for(int row=0;row<rankMat.length;row++) {
                        if(max<=rankMat[row][j]) {
                            max=rankMat[row][j];
                        }
                    }
                }


            }

        }

//finally return the calculated maximum rank value
        return max;
    }

//a function to loop through a matrix and systematically print its elements

    public static void print(int mat[][]) {
        for(int i=0;i<mat.length;i++) {
            for(int j=0;j<mat[0].length;j++) {

                System.out.print(" "+mat[i][j]);
            }
            System.out.println();
        }



    }


    public static void main(String[] args) {

        int arr[][] = new int[][]{{2,3},{4,5}};

        int sorted[] = sortMartix(arr);

        int rankMat[][]=assignRank(arr, sorted);
        print(rankMat);
    }



}