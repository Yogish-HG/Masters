import Queries.*;
import Queries.Implementation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            ArrayList<String[]> TransactionArray = new ArrayList<>();
            File file = new File("Logger.yhg");
            file.createNewFile();

            System.out.println("Enter 1 for signUp and 0 to Login to DBMS");
            Scanner scanner = new Scanner(System.in);
            String number = scanner.nextLine();
            if (Objects.equals(number, "1")) {
                UserPackage.UserLoginSignUp.SignUp();
            }
            if (Objects.equals(number, "0")) {
                String LoginRes = UserPackage.UserLoginSignUp.Login();
                if (LoginRes != null) {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                    while (true) {
                        System.out.println("Enter your query\n");
                        String query = scanner.nextLine();
                        String[] queryRes = query.split("\\s+");
                        // data, user information and logs
                        if (queryRes[0].equalsIgnoreCase("create")) {
                            CreateQueryInterface createQueryInterface = new CreateQuery();
                            if (createQueryInterface.createTable(queryRes)) {
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("User " + LoginRes + " has created a new table called " + queryRes[2] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush(); // Flushes the content to the file
                            }
                            else{
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("FAILED : User " + LoginRes + " tried  a new table called " + queryRes[2] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush();
                            }
                        } else if (queryRes[0].equalsIgnoreCase("delete")) {
                            DeleteQueryInterface deleteQueryInterface = new DeleteQuery();
                            if (deleteQueryInterface.delete(queryRes)) {
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("User " + LoginRes + " has deleted a row from table " + queryRes[2] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush(); // Flushes the content to the file
                            }
                            else{
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("FAILED: User " + LoginRes + " tried deleting  a new table called " + queryRes[2] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush();
                            }
                        } else if (queryRes[0].equalsIgnoreCase("drop")) {
                            DropQueryInterface dropQueryInterface = new DropQuery();
                            if (dropQueryInterface.drop(queryRes)) {
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("User " + LoginRes + " has dropped the table " + queryRes[2] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush(); // Flushes the content to the file
                            }
                            else{
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("FAILED : User " + LoginRes + " tried dropping table called " + queryRes[2] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush();
                            }
                        } else if (queryRes[0].equalsIgnoreCase("insert")) {
                            InsertQueryInterface insertQueryInterface = new InsertQuery();
                            if (insertQueryInterface.Insert(queryRes)) {
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("User " + LoginRes + " has inserted a row to the table " + queryRes[2] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush(); // Flushes the content to the file
                            }
                            else{
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("FAILED : User " + LoginRes + " tried inserting a new row into " + queryRes[2] +
                                        " at " + currentTimestamp + "n");
                                bufferedWriter.flush();
                            }
                        } else if (queryRes[0].equalsIgnoreCase("select")) {
                            SelectQueryInterface selectQueryInterface = new SelectQuery();
                            if (selectQueryInterface.SelectTable(queryRes)) {
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("User " + LoginRes + " has performed a select operation to the table " + queryRes[3] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush(); // Flushes the content to the file
                            }
                            else{
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("FAILED : User " + LoginRes + " tried selecting from " + queryRes[2] +
                                        " at " + currentTimestamp + "n");
                                bufferedWriter.flush();
                            }
                        } else if (queryRes[0].equalsIgnoreCase("update")) {
                            UpdateQueryInterface updateQueryInterface = new UpdateQuery();
                            if (updateQueryInterface.Update(queryRes)) {
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("User " + LoginRes + " has updated a row in table " + queryRes[1] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush(); // Flushes the content to the file
                            }
                            else{
                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                bufferedWriter.write("FAILED : User " + LoginRes + " tried to update " + queryRes[2] +
                                        " at " + currentTimestamp + "\n");
                                bufferedWriter.flush();
                            }
                        } else if (queryRes[0].equalsIgnoreCase("exit")) {
                            break;
                        } else if (queryRes[1].equalsIgnoreCase("transaction")) {
                            String[] query2;
                            String tempq = null;
                            Timestamp currentTimestamp2 = new Timestamp(System.currentTimeMillis());
                            bufferedWriter.write("Transaction started at " + currentTimestamp2 + "\n");
                            bufferedWriter.flush(); // Flushes the content to the file
                            while ((tempq = scanner.nextLine()) != null) {
                                query2 = tempq.split("\\s+");
                                TransactionArray.add(query2);
                                if (query2[0].equalsIgnoreCase("rollback")) {
                                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                    bufferedWriter.write("Transaction rolled back");
                                    bufferedWriter.flush();
                                    break;
                                }
//                            start transaction
//                            CREATE TABLE employees2 ( id INT PRIMARY KEY, name VARCHAR(100) NOT NULL, age INT, salary INT);
//                            insert into employees2 values (1, helloName, 22, 2222222);
//                            commit
                                else if (query2[0].equalsIgnoreCase("commit")) {
                                    TransactionArray.remove(TransactionArray.size() - 1);
                                    for (String[] s : TransactionArray) {
                                        if (s[0].equalsIgnoreCase("create")) {
                                            CreateQueryInterface createQueryInterface = new CreateQuery();
                                            if (createQueryInterface.createTable(s)) {
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("User " + LoginRes + " has created a new table called " + s[2] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush(); // Flushes the content to the file
                                            }
                                            else{
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("FAILED : User " + LoginRes + " tried  a new table called " + queryRes[2] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush();
                                            }
                                        } else if (s[0].equalsIgnoreCase("delete")) {
                                            DeleteQueryInterface deleteQueryInterface = new DeleteQuery();
                                            if (deleteQueryInterface.delete(s)) {
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("User " + LoginRes + " has deleted a row from table " + s[2] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush(); // Flushes the content to the file
                                            }
                                            else{
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("FAILED: User " + LoginRes + " tried deleting  a new table called " + queryRes[2] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush();
                                            }
                                        } else if (s[0].equalsIgnoreCase("drop")) {
                                            DropQueryInterface dropQueryInterface = new DropQuery();
                                            if (dropQueryInterface.drop(s)) {
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("User " + LoginRes + " has dropped the table " + s[2] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush(); // Flushes the content to the file
                                            }
                                            else{
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("FAILED : User " + LoginRes + " tried dropping table called " + queryRes[2] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush();
                                            }
                                        } else if (s[0].equalsIgnoreCase("insert")) {
                                            InsertQueryInterface insertQueryInterface = new InsertQuery();
                                            if (insertQueryInterface.Insert(s)) {
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("User " + LoginRes + " has inserted a row to the table " + s[2] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush(); // Flushes the content to the file
                                            }
                                            else{
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("FAILED : User " + LoginRes + " tried inserting a new row into " + queryRes[2] +
                                                        " at " + currentTimestamp + "n");
                                                bufferedWriter.flush();
                                            }
                                        } else if (s[0].equalsIgnoreCase("select")) {
                                            SelectQueryInterface selectQueryInterface = new SelectQuery();
                                            if (selectQueryInterface.SelectTable(s)) {
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("User " + LoginRes + " has performed a select operation to the table " + s[3] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush(); // Flushes the content to the file
                                            }
                                            else{
                                                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                    bufferedWriter.write("FAILED : User " + LoginRes + " tried selecting from " + queryRes[2] +
                                                            " at " + currentTimestamp + "n");
                                                    bufferedWriter.flush();
                                            }
                                        } else if (s[0].equalsIgnoreCase("update")) {
                                            UpdateQueryInterface updateQueryInterface = new UpdateQuery();
                                            if (updateQueryInterface.Update(s)) {
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("User " + LoginRes + " has updated a row in table " + s[1] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush(); // Flushes the content to the file
                                            }
                                            else{
                                                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                                                bufferedWriter.write("FAILED : User " + LoginRes + " tried to update " + queryRes[2] +
                                                        " at " + currentTimestamp + "\n");
                                                bufferedWriter.flush();
                                            }
                                        }
                                    }
                                    bufferedWriter.write("Transaction ended at" + currentTimestamp2);
                                    bufferedWriter.flush();
                                    break;
                                }
                            }

                        } else {
                            System.out.println("Something wrong in the syntax. Please write the syntax correctly or write" +
                                    "exit to come out of the program");
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}