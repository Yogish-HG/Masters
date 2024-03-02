import java.util.LinkedList;

public class Map {
    private LinkedList<String> mapKeys;
    private LinkedList<String> mapValues;

    public Map() {
        mapKeys = new LinkedList<String>(); // initializing the key and value Linked lists in the constructor
        mapValues = new LinkedList<String>();
    }
    public void put(String key, String value){

        /* This method will first verify if the key passed as the parameter is already present in the map.
             If present, it will update its respective value. Otherwise, if the key passed is unique,
             then additional (key,value) pair element will be added to the Map */
        try {
            int update = 0;
            int size = mapKeys.size();
            for (int i = 0; i < size; i++) { // traversing through the Linked list of keys to find whether the key is unique
                if (mapKeys.get(i) == key) {
                    mapValues.set(i, value); // updating the value of the repeated key
                    update = 1;
                    break;
                }
            }
            if (update == 0) {
                mapKeys.add(key); // adding a new (key, value) pair to the map
                mapValues.add(value);
            }
        }

        catch (Exception E){
            System.out.println("Please check the code. It has a "+ E);
        }
    }

    public void get(String key){

        /* This method will take the key as the argument, outputs its respective value as the output.
           If the key is absent, then "Key not present in the map" message is displayed */
        try {
            int flag = 0;
            int size = mapKeys.size();
            for (int i = 0; i < size; i++) { // traversing through the Linked list of keys to find the key
                if (mapKeys.get(i) == key) {
                    System.out.println(mapValues.get(i));
                    flag = 1;
                    break;
                }
            }

            if (flag == 0) {
                System.out.println("Key named \"" + key + "\" not present in the map");
            }
        }
        catch (Exception E){
            System.out.println("Please check the code. It has a "+ E);
        }
    }

    public static void main(String[] args) {

        Map m = new Map();
        m.put("K1", "V1");
        m.put("K2", "V2");
        m.put("K3", "V3");
        m.put("K2", "V4");
        m.put("K5", "V2");
        m.get("K2");
        m.get("k");
        m.get("K3");
        m.get("K5");
    }
    }

