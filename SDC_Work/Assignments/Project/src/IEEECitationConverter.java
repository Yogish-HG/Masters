import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class IEEECitationConverter {

    /**
     * This method reads input from the user and processes the input file to replace citations with references
     */
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter name of the Input file");
            String inputFileName = scanner.next();
            System.out.println("Enter name of the Output file");
            String outputFileName = scanner.next();
            // Initialize reference map and reference number
            Map<String, Integer> referenceMap = new HashMap<>();

            // Read input file into list of lines
            List<String> lines = Files.readAllLines(Paths.get(inputFileName));

            // Process each line
            List<String> outputLines = new ArrayList<>();
            int count = 1;
            for (String line : lines) {
                // Split line into subStrings at each occurrence of "\cite{"
                String[] subStrings = line.split("\\\\cite\\{");
                StringBuilder builder = new StringBuilder(subStrings[0]);
                for (int i = 1; i < subStrings.length; i++) {
                    // Extract article keys from the substring between the curly braces
                    String[] articleKeys = subStrings[i].substring(0, subStrings[i].indexOf("}")).split(",");
                    StringBuilder referenceBuilder = new StringBuilder();
                    for (String articleKey : articleKeys) {
                        // If article key has been referenced before, use existing reference number
                        if (referenceMap.containsKey(articleKey)) {
                            referenceBuilder.append(", ").append(referenceMap.get(articleKey));
                        } else {
                            // Otherwise, create a new reference number and add it to the reference map
                            referenceMap.put(articleKey, count);
                            referenceBuilder.append(", ").append(count);
                            count++;
                        }
                    }
                    // Append reference string and text after citation to output line
                    builder.append("[").append(referenceBuilder.substring(2)).append("]").append(subStrings[i].substring(subStrings[i].indexOf("}")+1));
                }
                // Add line to output
                outputLines.add(builder.toString());
            }


            // Write output to file
                BufferedWriter bWriter = new BufferedWriter(new FileWriter(outputFileName));
                for (String line : outputLines) {
                    bWriter.write(line);
                    bWriter.newLine();
                }
                bWriter.newLine();
                bWriter.write("References");
                bWriter.newLine();
                int mapCount = 1;
                // Making an object of publication library
                PublicationLibrary pl = new PublicationLibrary();

                // traversing the reference map and check for values in ascending order(1, 2, 3...)
                while (mapCount <= referenceMap.size()) {
                    for (Map.Entry<String, Integer> entry : referenceMap.entrySet()) {
                        if (entry.getValue() == mapCount) {
                            // getting the publications details for the key of above value retrieved.
                            Map<String, String> referenceListMap = pl.getPublications(entry.getKey());
                            // looping inside if the publication is journal.
                            if (referenceListMap.containsKey("journal")) {
                                String jTitle = referenceListMap.get("title");
                                String jJournal = referenceListMap.get("journal");
                                String jPages = referenceListMap.get("pages");
                                String jVolume = referenceListMap.get("volume");
                                String jIssue = referenceListMap.get("issue");
                                String jMonth = referenceListMap.get("month");
                                String jYear = referenceListMap.get("year");
                                StringBuilder sb = new StringBuilder();
                                String authors = referenceListMap.get("authors");
                                String[] authorsSplit = authors.split(",");

                                // looping into author names and changing it into required IEEE format
                                for (String s : authorsSplit) {
                                    String[] singleNameSplit = s.split(" ");
                                    for (int i = 0; i < singleNameSplit.length; i++) {
                                        if (i != singleNameSplit.length - 1) {
                                            String bigAlpha = singleNameSplit[i].toUpperCase();
                                            sb.append(bigAlpha.charAt(0));
                                            sb.append(". ");
                                        }
                                        if (i == singleNameSplit.length - 1) {
                                            sb.append(singleNameSplit[i]);
                                        }
                                    }
                                    sb.append(", ");
                                }
                                // writing publications data into output file
                                bWriter.write("[" + mapCount + "]    " + sb + "\"" + jTitle + "\", " + jJournal + ", " + jVolume +
                                        ", " + jIssue + ", " + jPages + ", " + jMonth + " " + jYear);
                            }
                            // looping inside if the publication is conference.
                            if (referenceListMap.containsKey("conference")) {
                                String jTitle = referenceListMap.get("title");
                                String jConference = referenceListMap.get("conference");
                                String jLocation = referenceListMap.get("location");
                                String jYear = referenceListMap.get("year");
                                String jPages = referenceListMap.get("pages");
                                StringBuilder sb = new StringBuilder();
                                String authors = referenceListMap.get("authors");
                                String[] authorsSplit = authors.split(",");

                                // looping into author names and changing it into required IEEE format
                                for (String s : authorsSplit) {
                                    String[] singleNameSplit = s.split(" ");
                                    for (int i = 0; i < singleNameSplit.length; i++) {
                                        if (i != singleNameSplit.length - 1) {
                                            String bigAlpha = singleNameSplit[i].toUpperCase();
                                            sb.append(bigAlpha.charAt(0));
                                            sb.append(". ");
                                        }
                                        if (i == singleNameSplit.length - 1) {
                                            sb.append(singleNameSplit[i]);
                                        }
                                    }
                                    sb.append(", ");

                                }// writing publications data into output file
                                bWriter.write("[" + mapCount + "]    " + sb + "\"" + jTitle + "\", " + jConference + ", " + jLocation +
                                        ", " + jYear + ", " + jPages);
                            }
                            mapCount++;
                            bWriter.newLine();
                        }
                    }
                }
                bWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
