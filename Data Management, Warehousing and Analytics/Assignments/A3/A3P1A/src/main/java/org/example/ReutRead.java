package org.example;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReutRead {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String newsFile1 = "reut2-009.sgm";
        String newsFile2 = "reut2-014.sgm";
        String databaseName = "ReuterDb";
        String collectionName = "newsArticles";

        MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        try {
            parseFileAndInsertIntoDb(newsFile1, collection);
            parseFileAndInsertIntoDb(newsFile2, collection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }

    private static void parseFileAndInsertIntoDb(String filePath, MongoCollection<Document> collection) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder titleBuilder = new StringBuilder();
        StringBuilder textBuilder = new StringBuilder();
        String line;
        boolean isReutersTagOpen = false;
        boolean isBodyTagOpen = false;

        while ((line = reader.readLine()) != null) {
            if (line.contains("<REUTERS") && !isReutersTagOpen) {
                isReutersTagOpen = true;
                continue;
            }

            if(line.contains("<TITLE>")){
                titleBuilder.append(line.replaceAll("<TITLE>", "").replaceAll("</TITLE>", ""));
                continue;
            }

            if(line.contains("<BODY") && line.contains("/BODY")){
                int bodyTagIndex = line.indexOf("<BODY");
                int bodyTagIndexend  = line.indexOf("</BODY");
                textBuilder.append(line, bodyTagIndex+5, bodyTagIndexend);
            }

            else if(line.contains("<BODY") && !isBodyTagOpen){
                isBodyTagOpen =true;
                int bodyTagIndex = line.indexOf("<BODY");
                textBuilder.append(line.substring(bodyTagIndex+5));
            }

            else if(isBodyTagOpen && line.contains("</BODY")){
                int bodyTagIndexend = line.indexOf("</BODY");
                textBuilder.append(line, 0, bodyTagIndexend);
                isBodyTagOpen = false;
            }

            else if(isBodyTagOpen && !line.contains("</BODY")){
                textBuilder.append(line);
            }

            if (line.contains("</REUTERS") && isReutersTagOpen) {
                isReutersTagOpen = false;
                String title;
                String text;
                if(titleBuilder.isEmpty()){
                    title = " ";
                }
                else{
                    titleBuilder = removePattern(titleBuilder, "\\*{6}");
                    titleBuilder = removePattern(titleBuilder, "&lt;");
                    titleBuilder = removePattern(titleBuilder, "[<>]");
                    title = titleBuilder.toString();
                }

                if(textBuilder.isEmpty()){
                    text = " ";
                }
                else{
                    textBuilder = removePattern(textBuilder, "Reuter&#3;");
                    textBuilder = removePattern(textBuilder, "&lt;");
                    textBuilder = removePattern(textBuilder, "[<>]");
                    text = textBuilder.toString();
                }
                Document newsArticle = new Document("title", title).append("text", text);
                collection.insertOne(newsArticle);
                textBuilder.setLength(0);
                titleBuilder.setLength(0);
            }
        }
    }

    private static StringBuilder removePattern(StringBuilder stringBuilder, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringBuilder);
        return new StringBuilder(matcher.replaceAll(""));
    }
}

