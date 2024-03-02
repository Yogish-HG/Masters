import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FillInPuzzle {

    private int numberOfRows = 0;
    private int numberOfColumns = 0;
    private int numberOfWords = 0;
    private int numberOfBacktracks = 0;
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<String> wordsDescription = new ArrayList<>();

    private String[][] puzzle;

    public FillInPuzzle() {
    }

    /*
    Reads the data from the BufferReader object and stores it.
     */
    Boolean loadPuzzle(BufferedReader stream) throws IOException {
        try {
            if (stream == null) {
                return false;
            }

            // reading and splitting the first line to store row, column, numberOfWords values.
            String mainIntegers = stream.readLine();
            String[] splitMainIntegers = mainIntegers.split(" ");
            numberOfColumns = Integer.parseInt(splitMainIntegers[0]);
            numberOfRows = Integer.parseInt(splitMainIntegers[1]);
            numberOfWords = Integer.parseInt(splitMainIntegers[2]);
            puzzle = new String[numberOfRows][numberOfColumns];

            // store the positions or description of the words in arraylist namely "wordsDescription"
            for (int i = 0; i < numberOfWords; i++) {
                String description = stream.readLine();
                if(description == null){
                    continue;
                }
                wordsDescription.add(description);
            }

            // store the words in arraylist namely "words"
            for (int i = 0; i < numberOfWords; i++) {
                String Words = stream.readLine();
                if(Words == null){
                    continue;
                }
                words.add(Words.toLowerCase());
            }
            if(wordsDescription.size() != words.size()){
                return false;
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    // Solve the puzzle using the data stored.
    Boolean solve() {
        // fill period where characters are expected.
        Helper.fillPeriods(puzzle, numberOfWords, numberOfRows, wordsDescription);

        // The array is used as a verification if the word present at the index in words arraylist is available to use.
        boolean[] wordAvailabilityArray = new boolean[numberOfWords];
        Arrays.fill(wordAvailabilityArray, true);

        // call the puzzleSolver method to solve the puzzle
        puzzle = puzzleSolver(wordsDescription, puzzle, words, wordAvailabilityArray, 0);
        ArrayList<String> resStrings = new ArrayList<>();

        // check if the puzzle is solved
        for (int m = 0; m < numberOfRows; m++) {
            for (int n = 0; n < numberOfColumns; n++) {
                resStrings.add(puzzle[m][n]);
            }
        }
        // if the puzzle still has dots, it means it cannot be solved, therefore returns false. Else, returns true
        if (!resStrings.contains(".")) {
            return true;
        } else {
            return false;
        }

    }

    // This method acts like a helper method for solve to fill and solve the puzzle
    String[][] puzzleSolver(ArrayList<String> wordDetails, String[][] puzzleArray, ArrayList<String> actualWord, boolean[] WAA, int place) {
        // backing up the puzzle so that it is useful while backtracking
        String[][] backUp = Helper.deepCopy(puzzleArray);

        // get the information about the index of the space the puzzle is trying to get filled in.
        String details = wordDetails.get(place);
        String[] detailsSplit = details.split(" ");
        String direction = detailsSplit[3];

        // traversing through the words and check which word suits in the space
        for (int j = 0; j < numberOfWords; j++) {
            boolean backTrack = false;
            boolean arrayUpdated = false;
            // checking if the length of the word and length of the to be filled space(period) are same
            if(Integer.parseInt(detailsSplit[2]) == actualWord.get(j).length()){

                // checking if the direction is horizontal or vertical
                if(Objects.equals(direction, "h")){
                    char[] wordChar = actualWord.get(j).toCharArray();
                    int count = 0;
                    boolean flag = false;
                    // checking if the word can fit into the space
                    for (int k = Integer.parseInt(detailsSplit[0]); k < Integer.parseInt(detailsSplit[0]) + Integer.parseInt(detailsSplit[2]); k++) {
                        if(Objects.equals(puzzleArray[(numberOfRows - 1) - Integer.parseInt(detailsSplit[1])][k], ".") ||
                                Objects.equals(puzzleArray[(numberOfRows - 1) - Integer.parseInt(detailsSplit[1])][k], String.valueOf(wordChar[count]))){
                            count++;
                        }
                        else{
                            flag = true;
                            break;
                        }
                    }
                    count = 0;
                    // if the word can fit, fill it in the puzzle
                    if(!flag && WAA[j]) {
                        for (int k = Integer.parseInt(detailsSplit[0]); k < Integer.parseInt(detailsSplit[0]) + Integer.parseInt(detailsSplit[2]); k++) {
                            puzzleArray[(numberOfRows - 1) - Integer.parseInt(detailsSplit[1])][k] = String.valueOf(wordChar[count]);
                            count++;
                        }
                        WAA[j] = false;
                        arrayUpdated = true;

                    }
                }
                // checking if the direction is horizontal or vertical
                if(Objects.equals(direction, "v")){
                    char[] wordChar = actualWord.get(j).toCharArray();
                    int count = 0;
                    boolean flag = false;
                    // checking if the word can fit into the space
                    for (int k = (numberOfRows - 1) - Integer.parseInt(detailsSplit[1]); k < (numberOfRows - 1) - Integer.parseInt(detailsSplit[1]) + Integer.parseInt(detailsSplit[2]); k++) {
                        if(Objects.equals(puzzleArray[k][Integer.parseInt(detailsSplit[0])], String.valueOf(wordChar[count]))
                                || Objects.equals(puzzleArray[k][Integer.parseInt(detailsSplit[0])], ".")){
                            count++;
                        }
                        else{
                            flag = true;
                            break;
                        }
                    }

                    // if the word can fit, fill it in the puzzle
                    count = 0;
                    if(!flag && WAA[j]) {
                        for (int k = (numberOfRows - 1) - Integer.parseInt(detailsSplit[1]); k < (numberOfRows - 1) - Integer.parseInt(detailsSplit[1]) + Integer.parseInt(detailsSplit[2]); k++) {
                            puzzleArray[k][Integer.parseInt(detailsSplit[0])] = String.valueOf(wordChar[count]);
                            count++;
                        }
                        WAA[j] = false;
                        arrayUpdated = true;
                    }
                }
            }
            // if the array is updated and puzzle is unsolved, recursively call the method with updated parameters
            if(arrayUpdated) {
                ArrayList<String> resStrings = new ArrayList<>();
                for(int m =0 ;m< numberOfRows;m++){
                    for(int n =0 ;n< numberOfColumns;n++){
                        resStrings.add(puzzleArray[m][n]);
                    }
                }
                if(resStrings.contains(".")) {
                    // call recursively
                    puzzleArray = puzzleSolver(wordDetails, puzzleArray, actualWord, WAA, place+1);
                    // if the control flow comes here, it means that backtracking is necessary
                    backTrack = true;
                }
                else{
                    return puzzleArray;
                }
            }
            // checking if puzzle is solved
            ArrayList<String> resStrings = new ArrayList<>();
            for(int m =0 ;m< numberOfRows;m++){
                for(int n =0 ;n< numberOfColumns;n++){
                    resStrings.add(puzzleArray[m][n]);
                }
            }
            if(!resStrings.contains(".")) {
                return puzzleArray;
            }
            // backtracking the puzzle and increment the number of backtracks the program is making.
            if(backTrack) {
                numberOfBacktracks++;
                puzzleArray = Helper.deepCopy(backUp);
                WAA[j] = true;
            }
        }
        // return the puzzle
        return puzzleArray;
    }

    Void print(PrintWriter outstream) {
        if(outstream == null){
            return null;
        }
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if(puzzle[i][j] == null){
                    outstream.print(" ");
                }
                else {
                    outstream.print(puzzle[i][j]);
                }
            }
            outstream.println();
        }
        outstream.flush();
        outstream.close();
        return null;
    }

    int choices(){
        return numberOfBacktracks;
    }
}

