package Queries.Implementation;

import HelperPackage.ArrayFilter;
import Queries.UpdateQueryInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;

public class UpdateQuery implements UpdateQueryInterface {

    /**
     * Updates records in a table based on the given query.
     *
     * @param query2 the update query
     * @return true if the update is successful, false otherwise
     */
    public boolean Update(String[] query2){
//        UPDATE table_name SET column1 = value1 WHERE id = 12;
        String[] query3 = ArrayFilter.filterElements(query2);
        String[] query = ArrayFilter.splitElements(query3);
        ArrayList<String> colName =new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(query[1]));
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(query[1]+"details"));
            BufferedReader bufferedReader3 = new BufferedReader(new FileReader(query[1]+"details"))){
            String line;
            int totalLines=0;
            while((line = bufferedReader.readLine()) != null){
                String[] lineSplit = line.split(",");
                colName.add(lineSplit[0]);
                totalLines++;
            }
            String afterWhere = query[7];
            int index = colName.indexOf(afterWhere);
            String beforeWhere = query[3];
            int index2 = colName.indexOf(beforeWhere);
            String[] resArray = new String[0];
            int countRes=0;
            // Modify the content with the updated record
            while((line = bufferedReader2.readLine()) != null){
                String[] lineSplit = line.split(",");
                if(Objects.equals(lineSplit[index], query[9])){
                    lineSplit[index2] = query[5];
                    resArray = lineSplit;
                    break;
                }
                countRes++;
            }
            String resString = String.join(",", resArray);
            int resCount = 0;
            StringBuilder content = new StringBuilder();
            while((line = bufferedReader3.readLine()) != null){
                if(resCount == countRes){
                    content.append(resString).append(System.lineSeparator());
                }
                else{
                    content.append(line).append(System.lineSeparator());
                }
                resCount++;
            }
            // Write the modified content back to the details file
            BufferedWriter writer = new BufferedWriter(new FileWriter(query[1]+"details"));
            writer.write(content.toString());
            writer.flush();
            writer.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    return true;
    }
}
