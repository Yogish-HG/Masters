import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class Main {

            public static void main(String[] args) {
                String[] authorsSplit = {"tim john David ryan", "Yogish Hon"};
                StringBuilder sb = new StringBuilder();
                for(String s: authorsSplit){
                    String[] singleNameSplit = s.split(" ");
                    for(int i =0; i<singleNameSplit.length; i++){
                        if(i != singleNameSplit.length-1){
                            String bigAlpha = singleNameSplit[i];
                            sb.append(bigAlpha.charAt(0));
                            sb.append(". ");
                        }
                        if(i == singleNameSplit.length-1){
                            sb.append(singleNameSplit[i]);
                        }
                    }
                    if(authorsSplit.length == 2){
                        sb.append(" and ");
                    }
                    else{
                        sb.append(", ");
                    }
                }
                System.out.println(sb);
            }

        }


