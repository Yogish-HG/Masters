package Queries.Implementation;

import HelperPackage.ArrayFilter;
import Queries.CreateQueryInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateQuery implements CreateQueryInterface {

    /**
     * Creates a table based on the given query.
     *
     * @param query2 the query for table creation
     * @return true if the table is created successfully, false otherwise
     */
    public boolean createTable(String[] query2){

        String[] query3 = ArrayFilter.filterElements(query2);
        String[] query = ArrayFilter.splitElements(query3);
        String databaseName = query[2];
        File file;
        file = new File(databaseName);
        if(file.exists()){
            return false;
        }
        //            CREATE TABLE employees ( id INT PRIMARY KEY, name VARCHAR(100) NOT NULL, age INT, salary INT);
        try(PrintWriter writer = new PrintWriter(new FileWriter(file, true))){
            int size = query.length;
            int i =2;
            while(i+2 < size){
                    if(query[i+2].contains("VARCHAR") || query[i+2].contains("varchar")){
                        query[i+2] = "VARCHAR";
                    }
                    if(query[i+2].contains("DECIMAL") || query[i+2].contains("decimal")){
                        query[i+2] = "DECIMAL";
                    }
                    if(i+3 < size){
                        if(query[i+3].equalsIgnoreCase("PRIMARY")){
                            writer.println(query[i+1]+","+query[i+2]+","+"PRIMARY");
                            i=i+4;
                            continue;
                        }
                        if((query[i+3].equalsIgnoreCase("AI") || query[i+3].equalsIgnoreCase("Auto_increment"))
                            && query[i+4].equalsIgnoreCase("PRIMARY")){
                            writer.println(query[i+1]+","+query[i+2]+","+ "AI" +","+ "PRIMARY");
                            i=i+5;
                            continue;
                        }

                        if(query[i+3].equalsIgnoreCase("DEFAULT")){
                            writer.println(query[i+1]+","+query[i+2]+","+ "DEFAULT," + query[i+4]);
                            i=i+4;
                            continue;
                        }
                        if(query[i+3].equalsIgnoreCase("UNIQUE")){
                            writer.println(query[i+1]+","+query[i+2]+","+"UNIQUE");
                            i=i+3;
                            continue;
                        }
                        if(query[i+3].equalsIgnoreCase("NOT")){
                            writer.println(query[i+1]+","+query[i+2]+","+"NOT NULL");
                            i=i+4;
                            continue;
                        }
                    }
                writer.println(query[i+1]+","+query[i+2]);
                i =i+2;
            }
            writer.flush();

        }catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
