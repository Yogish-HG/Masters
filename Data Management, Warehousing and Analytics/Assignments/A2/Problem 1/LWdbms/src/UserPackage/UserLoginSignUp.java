package UserPackage;

import HelperPackage.hashMD5;

import java.io.*;
import java.util.Scanner;

public class UserLoginSignUp {
    private static final String FILE_PATH = "users.ydb";
    public static void SignUp(){
        File file;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter your username");
        String username = scanner.nextLine();
        System.out.println("\nEnter your password");
        String Password = scanner.nextLine();
        System.out.println("\nThis is a Security question!! Remember your answer.\n" +
                "Enter your Favourite word, number or their combination");
        String answer = scanner.nextLine();
        try{
                file = new File(FILE_PATH);
                file.createNewFile();
        }catch (IOException e) {
            System.out.println("Error creating the file: " + e.getMessage());
            return;
        }
        // Append user details to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                Password = hashMD5.hashPassword(Password);
                String userDetails = String.format("%s,%s,%s", username, Password, answer);
                writer.println(userDetails);
                System.out.println("User signed up successfully!");
            } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }

    }
    public static String Login(){

        // Append user details to the file
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter your username");
                String username = scanner.nextLine();
                System.out.println("Enter your password");
                String Password = scanner.nextLine();
                Password = hashMD5.hashPassword(Password);
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    String firstValue = values[0].trim();
                    if (firstValue.equals(username)) {
                        if(Password.equals(values[1].trim())){
                            System.out.println("Enter your Favourite number, word or any combination");
                            String number = scanner.nextLine();
                            if(number.equals(values[2])){
                                return username;
                            }
                        }
                    }
            }
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage()+". Maybe No one is signed Up to the database.");
        }
        return null;
    }

    public static void main(String[] args) {
        // Example usage
        //SignUp("user1", "p1", "red");
        System.out.println(Login());
    }
}
