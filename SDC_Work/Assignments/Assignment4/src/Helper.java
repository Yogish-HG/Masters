import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Helper {

    // This method is used to fill period.
    public static String[][] fillPeriods(String[][] dotFill, int words, int rows, ArrayList<String> WordsDes ){
        for(int i=0; i<words;i++){
            String WD = WordsDes.get(i);
            String[] WDSplit = WD.split(" ");
            // check for the direction
            if(Objects.equals(WDSplit[3], "h")) {
                for (int j = Integer.parseInt(WDSplit[0]); j < Integer.parseInt(WDSplit[0])+ Integer.parseInt(WDSplit[2]); j++) {
                    dotFill[(rows-1)-Integer.parseInt(WDSplit[1])][j] = ".";
                }
            }
            if(Objects.equals(WDSplit[3], "v")) {
                for (int j = (rows-1)-Integer.parseInt(WDSplit[1]); j < (rows-1)-Integer.parseInt(WDSplit[1])+ Integer.parseInt(WDSplit[2]); j++) {
                    dotFill[j][Integer.parseInt(WDSplit[0])] = ".";
                }
            }
        }
        return dotFill;
    }

    // deep copy an array so that it is useful in backtracking.
    public static String[][] deepCopy(String[][] original) {
        if (original == null) {
            return null;
        }
        int numRows = original.length;
        int numCols = original[0].length;
        String[][] copy = new String[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, numCols);
        }
        return copy;
    }


}
