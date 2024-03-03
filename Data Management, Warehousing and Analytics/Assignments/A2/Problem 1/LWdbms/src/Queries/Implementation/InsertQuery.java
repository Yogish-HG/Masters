package Queries.Implementation;

import HelperPackage.ArrayFilter;
import Queries.InsertQueryInterface;

import java.io.*;
import java.util.ArrayList;

/**
 * This class represents an Queries.Implementation.InsertQuery and provides a method to insert values into a table.
 */
public class InsertQuery implements InsertQueryInterface {

    /**
     * Inserts values into a table based on the provided query.
     * @param query2 the insert query with values to be inserted
     * @return true if the data is inserted successfully, false otherwise
     */
    public boolean Insert(String[] query2) {
        int lineCount = 0;
        boolean exceptionFlag = false;
        ArrayList<String> insertionArray = new ArrayList<>();

        // INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);
        String[] query3 = ArrayFilter.filterElements(query2);
        String[] query = ArrayFilter.splitElements(query3);
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        File file;

        try {
            file = new File(query[2]+"details");
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        int i = 3;
        int count = 0;
        while (true) {
            if (query[i].equalsIgnoreCase("VALUES")) {
                break;
            }
            columns.add(query[i++]);
            count++;
        }
        if(query[i].equalsIgnoreCase("values") && count == 0){
            try{
                BufferedReader bufferedReader3 = new BufferedReader(new FileReader(query[2]));
                String line;
                while((line = bufferedReader3.readLine()) != null){
                    columns.add(line.split(",")[0]);
                    count++;
                }
            } catch(Exception e){
                System.out.println(e.getMessage());
                return false;
            }


        }
        int valIndex = 0;
        for(String s: query){
            if(s.equalsIgnoreCase("values")){
                valIndex++;
                break;
            }
            valIndex++;
        }
        for (int j = 0; j < count; j++) {
            values.add(query[valIndex++]);
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(query[2]));
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(query[2]));

            while (bufferedReader.readLine() != null) {
                lineCount++;
            }

            // Loop through each line in the table
            for (int k = 0; k < lineCount; k++) {
                ArrayList<String> primaryKeyValues = new ArrayList<>();
                ArrayList<String> uniqueKeyValues = new ArrayList<>();
                String line = bufferedReader2.readLine();
                String[] lineSplit = line.split(",");

                if (line.contains("PRIMARY")) {
                    // Read existing primary key values from the file
                    BufferedReader primaryReader = new BufferedReader(new FileReader(file));
                    String primaryLine;
                    while ((primaryLine = primaryReader.readLine()) != null) {
                        String[] primarySplit = primaryLine.split(",");
                        primaryKeyValues.add(primarySplit[k]);
                    }
                    primaryReader.close();
                }

                if (line.contains("UNIQUE")) {
                    // Read existing unique key values from the file
                    BufferedReader primaryReader = new BufferedReader(new FileReader(file));
                    String primaryLine;
                    while ((primaryLine = primaryReader.readLine()) != null) {
                        String[] primarySplit = primaryLine.split(",");
                        uniqueKeyValues.add(primarySplit[k]);
                    }
                    primaryReader.close();
                }
                String col;

                col = lineSplit[0];
                int colIndex = columns.indexOf(col);

                if (colIndex == -1) {
                    // Column not found in the insert query
                    if (line.contains("PRIMARY")) {
                        throw new IllegalArgumentException("Null values not accepted as " + col + " is PRIMARY");
                    }
                    if (line.contains("NOT NULL")) {
                        throw new IllegalArgumentException("Null values not accepted as " + col + " is NOT NULL");
                    }
                    if (line.contains("UNIQUE")) {
                        throw new IllegalArgumentException("Null values not accepted as " + col + " is UNIQUE");
                    }
                    if (line.contains("DEFAULT")) {
                        insertionArray.add(lineSplit[3]);
                        continue;
                    }
                    insertionArray.add("Null");
                    continue;
                }

                if (line.contains("PRIMARY")) {
                    // Check for duplicate values in primary key
                    if (primaryKeyValues.contains(values.get(colIndex))) {
                        throw new RuntimeException("Values cannot be repeated in primary key");
                    }
                    insertionArray.add(values.get(colIndex));
                    continue;
                }

                if (line.contains("UNIQUE")) {
                    // Check for duplicate values in unique key
                    if (uniqueKeyValues.contains(values.get(colIndex))) {
                        throw new RuntimeException("Values cannot be repeated in unique key");
                    }
                    insertionArray.add(values.get(colIndex));
                    continue;
                }

                insertionArray.add(values.get(colIndex));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exceptionFlag = true;
        }

        int size = insertionArray.size();
        if (exceptionFlag) {
            size = -1;
            return false;
        }

        if (size == lineCount) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                // Append the values to the file
                for (int m = 0; m < insertionArray.size(); m++) {
                    if(m == insertionArray.size() -1){
                        writer.print(insertionArray.get(m));
                    }else{
                        writer.print(insertionArray.get(m) + ",");
                    }
                }

                if (insertionArray.size() > 0) {
                    writer.println();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return true;
    }
}
