package Queries.Implementation;

import HelperPackage.ArrayFilter;
import Queries.DeleteQueryInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;

public class DeleteQuery implements DeleteQueryInterface {
    /**
     * Deletes records from a table based on the given query.
     *
     * @param query2 the delete query
     * @return true if the deletion is successful, false otherwise
     */
    public boolean delete(String[] query2){
//        DELETE FROM employees WHERE id = 123;
        String[] query3 = ArrayFilter.filterElements(query2);
        String[] query = ArrayFilter.splitElements(query3);
        ArrayList<String> colName =new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(query[2]));
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(query[2]+"details"))){
            String line;
            int totalLines=0;
            while((line = bufferedReader.readLine()) != null){
                String[] lineSplit = line.split(",");
                colName.add(lineSplit[0]);
                totalLines++;
            }
            String afterWhere = query[4];
            int index = colName.indexOf(afterWhere);
            String val = query[6];
            StringBuilder content = new StringBuilder();
            while((line = bufferedReader2.readLine()) != null){
                String[] lineSplit = line.split(",");
                if(Objects.equals(lineSplit[index], val)){
                    continue;
                }
                content.append(line).append(System.lineSeparator());
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(query[2]+"details"));
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
