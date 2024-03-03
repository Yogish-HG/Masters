package Queries.Implementation;

import HelperPackage.ArrayFilter;
import Queries.SelectQueryInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class SelectQuery implements SelectQueryInterface {

    /**
     * Selects records from a table based on the given query.
     *
     * @param query2 the select query
     * @return true if the selection is successful, false otherwise
     */
    public boolean SelectTable(String[] query2) {

        ArrayList<String> colName = new ArrayList<>();
        ArrayList<String[]> valueArraylist = new ArrayList<>();

        String[] query3 = ArrayFilter.filterElements(query2);
        String[] query = ArrayFilter.splitElements(query3);

        if(query[1].equalsIgnoreCase( "*") && query.length == 4){
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(query[3]));
                String line;
                while((line = bufferedReader.readLine()) != null){
                    String[] lineSplit = line.split(",");
                    colName.add(lineSplit[0]);
                }
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(query[3]+"details"));
                while((line = bufferedReader2.readLine()) != null){
                    String[] lineSplit = line.split(",");
                    valueArraylist.add(lineSplit);
                }
                for(String st: colName){
                    System.out.printf("%-10s", st);
                }
                System.out.println();
                System.out.println("==".repeat(20));
                int k=0;
                for(String[] st: valueArraylist){
                    for(String st2: st){
                        System.out.printf("%-10s", st2);
                    }
                    System.out.println();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }


        }
        if(query.length > 4){
            if(query[1].equalsIgnoreCase( "*") && query[4].equalsIgnoreCase("where")){

                try{
                    BufferedReader bufferedReader4 = new BufferedReader(new FileReader(query[3]));
                    String line;
                    while((line = bufferedReader4.readLine()) != null){
                        String[] lineSplit = line.split(",");
                        colName.add(lineSplit[0]);
                    }
                    String cond = query[5];
                    String val = query[7];
                    ArrayList<String> columns = new ArrayList<>();
                    ArrayList<String> values = new ArrayList<>();
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(query[3]));
                    while((line = bufferedReader.readLine()) != null){
                        columns.add(line.split(",")[0]);
                    }
                    int index = columns.indexOf(cond);
                    if(index == -1){
                        throw new RuntimeException("Attribute not present in the database");
                    }
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(query[3]+"details"));
                    while((line = bufferedReader2.readLine())!=null){
                        String[] lineSplit = line.split(",");

                        if(Objects.equals(lineSplit[index], val)){
                            for(String st: colName){
                                System.out.printf("%-10s", st);
                            }
                            System.out.println();
                            System.out.println("==".repeat(20));
                            for(String st: lineSplit){
                                System.out.printf("%-10s", st);
                            }
                            System.out.println();
                        }
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    return false;
                }

            }
        }

    return true;
    }
}
