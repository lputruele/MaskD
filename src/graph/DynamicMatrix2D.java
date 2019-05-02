package graph;
import java.util.*;

public class DynamicMatrix2D<E> {
    private ArrayList<ArrayList<E>> matrix;

    public DynamicMatrix2D(){
        matrix = new ArrayList<ArrayList<E>>(10);
        for (int i=0;i< 10;i++){
            matrix.add(i,new ArrayList<E>(10));
            for (int j=0;j< 10;j++){
                matrix.get(i).add(j,null);  
            }
        }
    }

    public void set(int x, int y, E value) {
        /*if (x >= matrix.size()) {
            E[][] tmp = matrix;
            matrix = new E[x + 1][];
            System.arraycopy(tmp, 0, matrix, 0, tmp.length);
            for (int i = x; i < x + 1; i++) {
                matrix[i] = new E[y];
            }
        }

        if (y >= matrix[x].length) {
            E[] tmp = matrix[x];
            matrix[x] = new E[y + 1];
            System.arraycopy(tmp, 0, matrix[x], 0, tmp.length);
        }*/

        matrix.get(x).add(y,value);
    }

    public E get(int x, int y) {
        return x >= matrix.size() || y >= matrix.get(x).size() ? null : matrix.get(x).get(y);
    }

    public int getRowLength(){
        return matrix.size();
    }

    public int getColLength(){
        return matrix.get(0).size();
    }

    public String toString(){
        return matrix.toString();
    }

}