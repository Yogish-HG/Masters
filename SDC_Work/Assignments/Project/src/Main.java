import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        PublicationLibrary p = new PublicationLibrary();
        Map<String, String> pass=new HashMap<>();

//        pass.put("authors", "kumar Hello,peter parker spider Man");
//        pass.put("title", "mkkns");
//        pass.put("conference", "VHS");
//        pass.put("location", "USA");
//        pass.put("year", "2021");
//        pass.put("pages", "27");
        pass.put("authors", "kvp");
        pass.put("title", "ssss");
        pass.put("journal", "ES");
        pass.put("volume", "8");
        pass.put("issue", "17th");
        pass.put("month", "May");
        pass.put("year", "2920");
        pass.put("pages", "8");

//        System.out.println(p.addPublication("A4", pass));


        Map<String, String> vI=new HashMap<>();

        vI.put("publisher", "IEEE");
        vI.put("editor", "nobody");
        vI.put("editor_contact", "17");
        vI.put("location", "Canada");
        vI.put("conference_year", "2022");

        Set<String> areas = new HashSet<>();
        areas.add("ML");
        areas.add("AI");
        areas.add("DSA");
        System.out.println(p.addArea("LL", areas));
//        System.out.println(p.addVenue("lancet", vI, areas));




        // *************************
        Set<String> ref = new HashSet<>();
        ref.add("A2");
//        ref.add("C3");
        ref.add("A4");
//        System.out.println(p.addReferences("A3", ref));

        Map<String, String> pI=new HashMap<>();

        pI.put("contact_name", "IEEE");
        pI.put("contact_email", "nobody");
        pI.put("location", "india");

//        System.out.println(p.addPublisher("P1", pI));

//        Map<String, String> m= p.getPublications("A4");
//        for (Map.Entry<String, String> entry : m.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

//        IEECitationConverter("input.txt", "output.txt");
        System.out.println(p.authorCitations("kv"));
//        System.out.println(p.authorResearchAreas("kvp", 0));
    }
}
