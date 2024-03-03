package HelperPackage;

import java.util.ArrayList;
import java.util.List;

public class ArrayFilter {

    public static String[] filterElements(String[] inputArray) {
        List<String> outputList = new ArrayList<>();

        for (String str : inputArray) {
            String trimmed = str.replaceAll("[();]", "").trim();
            if (!trimmed.isEmpty()) {
                outputList.add(trimmed);
            }
        }

        return outputList.toArray(new String[0]);
    }

    public static String[] splitElements(String[] array) {
        List<String> resultList = new ArrayList<>();

        for (String element : array) {
            if (element.contains(",")) {
                String[] splitElements = element.split(",");
                for (String splitElement : splitElements) {
                    resultList.add(splitElement.trim());
                }
            } else {
                resultList.add(element.trim());
            }
        }

        return resultList.toArray(new String[0]);
    }

}
