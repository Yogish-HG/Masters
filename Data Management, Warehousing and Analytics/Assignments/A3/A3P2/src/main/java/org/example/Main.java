package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        String newsFile1 = "reut2-009.sgm";
        String newsFile2 = "reut2-014.sgm";

        parseFileAndInsertIntoTitleFile(newsFile1);
        parseFileAndInsertIntoTitleFile(newsFile2);

        String line;
        int count = 1;
        BufferedReader reader = new BufferedReader(new FileReader("titleFile"));

        while((line = reader.readLine()) != null){
            if(line.equals("") || line.equals(" ")){
                break;
            }
            outputFileCreator(count, line);
            count++;
        }


    }

    private static boolean isWordPresentInFile(String filePath, String targetWord) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(targetWord)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void outputFileCreator(int count, String line ) throws IOException {
        ArrayList<String> resultList = new ArrayList<>();
        String PWFIlePath = "PositiveWords";
        String NWFIlePath = "NegativeWords";
        String[] newsSplit = line.split("\\s+");
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
        if(count == 1){
            writer.write("News#   ");
            writer.write("Title " + " ".repeat(99));
            writer.write("Word  " + " ".repeat(24));
            writer.write("Frequency ");
            writer.write("Polarity ");
        }
        writer.newLine();
        Map<String, Integer> wordOccurances = new HashMap<>();
        for(int i=0; i<newsSplit.length; i++){
            if(!wordOccurances.containsKey(newsSplit[i].toLowerCase())){
                wordOccurances.put(newsSplit[i].toLowerCase(), 1);
            }
            else{
                int newValue = wordOccurances.get(newsSplit[i].toLowerCase()) + 1;
                wordOccurances.put(newsSplit[i].toLowerCase(), newValue);
            }
        }
        int totalScore = 0;
        String polarity;
        for(String s: wordOccurances.keySet()){
            resultList.add(String.valueOf(count)); //news#
            resultList.add(line); // Title
            resultList.add(s); // word
            resultList.add(String.valueOf(wordOccurances.get(s))); // Frequency

            if(isWordPresentInFile(PWFIlePath, s.toLowerCase())){
                int sentiment = wordOccurances.get(s);
                totalScore += sentiment;
            }
            else if(isWordPresentInFile(NWFIlePath, s.toLowerCase())){
                int sentiment =  wordOccurances.get(s);
                totalScore -= sentiment;
            }
            if(totalScore > 0){
                polarity = "Positive";
                resultList.add(polarity);
            }
            else if(totalScore < 0){
                polarity = "Negative";
                resultList.add(polarity);
            }
            else{
                polarity = "Neutral";
                resultList.add(polarity);
            }
            for(int i= 0; i<resultList.size(); i+= 5){
                writer.write(resultList.get(i) + " ".repeat(7));
                int totalChar = 105 - resultList.get(i+1).length();
                writer.write(resultList.get(i+1) + " ".repeat(totalChar));
                int Char = 30 - resultList.get(i+2).length();
                writer.write(resultList.get(i+2)+ " ".repeat(Char));
                writer.write(resultList.get(i+3)+ " ".repeat(9));
                writer.write(resultList.get(i+4));
            }
            writer.newLine();
            resultList.clear();
        }
        writer.close();

    }

    private static void parseFileAndInsertIntoTitleFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String titleFilePath = "titleFile";
        BufferedWriter writer = new BufferedWriter(new FileWriter(titleFilePath, true));
        StringBuilder titleBuilder = new StringBuilder();
        String line;
        boolean isReutersTagOpen = false;

        while ((line = reader.readLine()) != null) {
            if (line.contains("<REUTERS") && !isReutersTagOpen) {
                isReutersTagOpen = true;
                continue;
            }

            if(line.contains("<TITLE>")){
                titleBuilder.append(line.replaceAll("<TITLE>", "").replaceAll("</TITLE>", ""));
                continue;
            }

            if (line.contains("</REUTERS") && isReutersTagOpen) {
                isReutersTagOpen = false;
                String title;
                if(titleBuilder.isEmpty()){
                    titleBuilder.setLength(0);
                    continue;
                }
                else{
                    titleBuilder = removePattern(titleBuilder, "\\*{6}");
                    titleBuilder = removePattern(titleBuilder, "&lt;");
                    titleBuilder = removePattern(titleBuilder, "[<>]");
                    title = titleBuilder.toString();
                }

                writer.write(title);
                writer.newLine();
                titleBuilder.setLength(0);
            }
        }
        reader.close();
        writer.close();
    }

    private static StringBuilder removePattern(StringBuilder stringBuilder, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringBuilder);
        return new StringBuilder(matcher.replaceAll(""));
    }
}
