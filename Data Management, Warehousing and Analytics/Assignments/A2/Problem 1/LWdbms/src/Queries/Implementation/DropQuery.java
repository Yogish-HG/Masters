package Queries.Implementation;

import HelperPackage.ArrayFilter;
import Queries.DropQueryInterface;

import java.io.File;

public class DropQuery implements DropQueryInterface {
    /**
     * Drops a table and its associated details file based on the given query.
     *
     * @param query2 the drop query
     * @return true if the table is dropped successfully, false otherwise
     */
    public boolean drop(String[] query2){
        try{
            String[] query3 = ArrayFilter.filterElements(query2);
            String[] query = ArrayFilter.splitElements(query3);
            String filename = query[2];
            File file= new File(filename);
            File file2= new File(filename+"details");
            file.delete();
            file2.delete();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
