import java.util.Arrays;
import java.util.Objects;

public class Helper {

    // Below method checks if the array contains is full, the elements is unique to array and
    // returns the index of the array to which the element should be added.
    public static int spaceInArray(String[] arr, String element){

        for(int i=0; i< arr.length; i++){
            if(Objects.equals(arr[i], element)){
                return -1;
            }
            if(arr[i] == null){
                return i;
            }
        }
        return -1;
    }

    // Below method sorts the array based on the idea "insert the middle element first and then the middle
    // elements of what is left to either side of that element".
    public static String[] middleSortArray(String[] arr) {
        if (arr.length <= 2) {
            return arr;
        }
        int mid = arr.length / 2;

        String[] left = Arrays.copyOfRange(arr, 0, mid);
        String[] right = Arrays.copyOfRange(arr, mid + 1, arr.length);

        String[] sortedLeft = middleSortArray(left);
        String[] sortedRight = middleSortArray(right);
        String[] result = new String[sortedLeft.length + sortedRight.length + 1];
        result[0] = arr[mid];
        System.arraycopy(sortedLeft, 0, result, 1, sortedLeft.length);
        System.arraycopy(sortedRight, 0, result, sortedLeft.length+1, sortedRight.length);
        return result;
    }
    // Below method returns the ceiling of the logarithm (base 2) of the number of values in the unbalanced tree
    // where the tree size is passed as the parameter.
    public static int ceilLog2(int n) {
        return (int) Math.ceil(Math.log(n) / Math.log(2));
    }


}
