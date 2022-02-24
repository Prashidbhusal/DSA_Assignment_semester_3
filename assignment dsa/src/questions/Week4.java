package questions;

import java.math.BigInteger;
import java.util.*;
import java.lang.Math;

class acoord {
        int[][] points;

        public acoord(int[][] p){
                this.points = p;
        }

        public String coprimeSlope(int i, int j){
                Integer delX = this.points[i][0] - this.points[j][0];
                Integer delY = this.points[i][1] - this.points[j][1];
                if(delX == 0)
                        return "inf";
                else if(delY==0)
                        return "zero";
                if(delX < 0){
                        delX = -delX;
                        delY = -delY;
                }
                Integer gcd = BigInteger.valueOf(delX).gcd(BigInteger.valueOf(delY)).intValue();
                delX = delX/gcd;
                delY = delY/gcd;
                String key = delX.toString()+'.'+delY.toString();
                return key;
        }

        public ArrayList<Integer> maxPointsThrough(int i){
                HashMap<String,ArrayList<Integer>> slopes = new HashMap<String,ArrayList<Integer>>();
                ArrayList<Integer> duplicates = new ArrayList<Integer>();

                for(int j=i+1;j<this.points.length;j++){
                        if(points[i] == points[j]){
                                duplicates.add(j);
                                continue;
                        }
                        String slope = coprimeSlope(i,j);
                        if(slopes.containsKey(slope)){
                                slopes.get(slope).add(j);
                        }
                        else{
                                slopes.put(slope,new ArrayList<>(Arrays.asList(i,j)));
                        }
                }

                //Finding the array with maximum points in it
                int maxSize = 0;
                ArrayList<Integer> maxArr = new ArrayList<Integer>();
                for(ArrayList<Integer> a : slopes.values()){
                        if(a.size() > maxSize){
                                maxSize = a.size();
                                maxArr = a;
                        }
                }
                maxArr.addAll(duplicates);
                return maxArr;
        }

        public double lengthOfLine(ArrayList<Integer> coords){
    /*We have an array of points of a line. We have to find two extreme points of the line and
    calculate the distance between them. For that, we search for min and max value of x-coordinate.
    If the line is vertical, all x-coordinates are same, so we search for min and max value of y-coordinate
    and calculate the distance between the corresponding coordinates*/
                int min = this.points[coords.get(0)][0];
                int max = this.points[coords.get(1)][0];
                int c = 0; //0 for x, 1 for y
                if(min == max)
                        c = 1;

                int minInd = 0;
                int maxInd = 0;
                for(int i : coords){
                        int[] p = points[i];
                        if(p[c] < min){
                                min = p[c];
                                minInd = i;
                        }
                        if(p[c] > max){
                                max = p[c];
                                maxInd = i;
                        }
                }
                int x1,x2,y1,y2;
                x1 = this.points[minInd][0];
                y1 = this.points[minInd][1];
                x2 = this.points[maxInd][0];
                y2 = this.points[maxInd][1];
                return Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2)));
        }

        public double solution(){
                ArrayList<Integer> maxPointsInd = new ArrayList<Integer>();
                int l = this.points.length;
                for(int i=0;i<l-1;i++){
                        ArrayList<Integer> pointsInd = maxPointsThrough(i);
                        if(pointsInd.size() > maxPointsInd.size()){
                                maxPointsInd = pointsInd;
                        }
                }
                double length = lengthOfLine(maxPointsInd);
                return length;
        }
}

//Running program from here
public class Week4{
        public static void main(String[] arg){
                int[][] points = {{1,1},{1,2},{1,3},{4,4}};
                acoord my = new acoord(points);
                double length = my.solution();
                System.out.println(length);
        }
}