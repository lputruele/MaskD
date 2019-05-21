package maskingDistance;
import java.util.*;

public class DynamicMatrix2D<E> {
    private ArrayList<ArrayList<E>> matrix;

    public DynamicMatrix2D(){
        matrix = new ArrayList<ArrayList<E>>(100);
        for (int i=0;i<100;i++){
            matrix.add(i,new ArrayList<E>(1000));
            for (int j=0;j<1000;j++){
                matrix.get(i).add(j,null);  
            }
        }
    }

    public void set(int x, int y, E value) {
        /*if (x >= matrix.size()) {
            ArrayList<ArrayList<E>> tmp = copyMatrix();
            matrix = new ArrayList<ArrayList<E>>(x + 10);
            matrix.addAll(0,tmp);
            for (int i = x; i < x + 10; i++) {
                matrix.add(i,new ArrayList<E>(x+10));
            }
        }

        if (y >= matrix.get(0).size()) {
            for (int i = 0; i < matrix.size(); i++) {
                ArrayList<E> tmp = (ArrayList<E>)matrix.get(i).clone();
                matrix.add(i, new ArrayList<E>(y + 10));
                matrix.get(i).addAll(0,tmp);
            }
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

    private ArrayList<ArrayList<E>> copyMatrix(){
        ArrayList<ArrayList<E>> res = new ArrayList<ArrayList<E>>(matrix.size());
        for (ArrayList<E> e : matrix){
            ArrayList<E> tmp = new ArrayList<E>(e.size());
            for (E e_ : e){
                tmp.add(e_);
            }
            res.add(tmp);
        }
        return res;
    }

}