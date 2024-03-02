import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class Helper {

    // Compares two points and returns true if they are equal
    public static boolean arePointsEqual(Point a, Point b){
        if(a == null || b == null){
            return false;
        }
        return (a.getX() == b.getX()) && (a.getY() == b.getY());
    }

    // compares two arrays having two points(intended to check if both streets are same) and returns true if they have
    // same set of points in any order
    public static boolean pointObjectsSame(Point[] arr1, Point[] arr2) {
        if(Objects.equals(arr1[0], arr1[1]) || Objects.equals(arr2[0], arr2[1]) ){
            return false;
        }
        boolean[] found = {false, false};

        for (int i = 0; i < arr1.length; i++) {
            for(int j = 0; j < arr2.length; j++){
                if (arr1[i].getX() == arr2[j].getX() && arr1[i].getY() == arr2[j].getY()) {
                    found[i] = true;
                    break;
                }
            }
        }
        for(boolean b : found){
            if(!b){
              return false;
            }
        }

        return true;
    }

    // compares two arrays having two points(intended to check if two streets meet at an intersection) and returns true
    // if they have a common point
    public static Point onePointSame(Point[] arr1, Point[] arr2) {
        if(Objects.equals(arr1[0], arr1[1]) || Objects.equals(arr2[0], arr2[1]) ){
            return null;
        }
        boolean[] found = {false, false};

        for (int i = 0; i < arr1.length; i++) {
            for(int j = 0; j < arr2.length; j++){
                if (arr1[i].getX() == arr2[j].getX() && arr1[i].getY() == arr2[j].getY()) {
                    found[i] = true;
                    break;
                }
            }
        }
        int value = 0;
        for(boolean b: found){
            if(b){
                value +=1;
            }
        }

        if(value !=1){
            return null;
        }
        int index =0;
        for(int k =0 ; k < 2; k++){
            if(found[k]){
                return arr1[k];
            }
        }
        return null;

    }

    /* when a street is considered and if given that street side is left this method is used to determine
       at which vertex should the vehicle move towards
     */

    public static Point ifGoToRight(Point[] arr){
        if(arr[0].getX() == arr[1].getX() && arr[0].getY() == arr[1].getY()){
            return null;
        }
        if(arr[1].getX() > arr[0].getX()){
            return arr[1];
        }
        if(arr[0].getX() > arr[1].getX()){
            return arr[0];
        }
        if(arr[1].getY() > arr[0].getY()){
            return arr[1];
        }
        return arr[0];
    }

    /* when a street is considered and if given that street side is left this method is used to determine
       at which vertex should the vehicle move towards
     */

    public static Point ifGoToLeft(Point[] arr){
        if(arr[0].getX() == arr[1].getX() && arr[0].getY() == arr[1].getY()){
            return null;
        }
        if(arr[1].getX() < arr[0].getX()){
            return arr[1];
        }
        if(arr[0].getX() < arr[1].getX()){
            return arr[0];
        }
        if(arr[1].getY() < arr[0].getY()){
            return arr[1];
        }
        return arr[0];
    }

}
