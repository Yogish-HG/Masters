import java.util.Arrays;
import java.util.Objects;

public class AmortizedTree implements Searchable, TreeDebug {
private TreeNode root;
private String[] valuesToBeAdded;

    public AmortizedTree() {
        root = null;
        valuesToBeAdded = new String[1];
    }
    public AmortizedTree(TreeNode n) {
        root = n;
        valuesToBeAdded = new String[1];
    }
    @Override
    public boolean add(String key) {
        if(key == null || key.equals("")){
            return false;
        }
        // Converting every value added into the AmortizedTree into lowercase
        key = key.toLowerCase();

        // checking whether the key is present in the array
        for(String s: valuesToBeAdded){
            if(Objects.equals(s, key)){
                return false;
            }
        }
        // checking whether the key is present in the array, returns false if present in tree or array.
        if(TreeOperations.searchInTreeOnly(root, key)){
            return false;
        }

        // checking if the array is full, if its full all the values are inserted into the tree
        if(valuesToBeAdded[valuesToBeAdded.length-1] != null){
            Arrays.sort(valuesToBeAdded);
            Helper.middleSortArray(valuesToBeAdded);
            for(String j: valuesToBeAdded) {
                root = TreeOperations.insertIntoTree(root, j);
            }

            // reinitializing the array size on the basis of ceiling of the logarithm (base 2) of values in array.
            int size = Helper.ceilLog2(TreeOperations.treeSize(root));
            if(size <=1){
                valuesToBeAdded = new String[1];
            }
            else{
                valuesToBeAdded = new String[size];
            }
            // the new key is now added to the 0th index of the newly initialized array
            valuesToBeAdded[0] = key;
            return true;
        }
        // if there is space(null values) in the tree, find the first null value index and put the key string
        // into that respective index
        int index = Helper.spaceInArray(valuesToBeAdded, key);
        valuesToBeAdded[index] = key;
        return true;
    }

    // Below method is used to find the key is present in the AmortizedTree
    @Override
    public boolean find(String key) {

        if(key == null || key.equals("")){
            return false;
        }
        key = key.toLowerCase();
        // Checking if the key is present in the tree
        if(TreeOperations.searchInTreeOnly(root, key)){
            return true;
        }
        // when the key is not in the tree, checking whether it is in the array
        for(String s: valuesToBeAdded){
            if(Objects.equals(s, key)){
                return true;
            }
        }
        return false;
    }

    // Below method removes the key from the AmortizedTree if the key is present and returns true, else returns false.
    @Override
    public boolean remove(String key) {
        if(key == null || key.equals("")){
            return false;
        }
        key = key.toLowerCase();
        TreeNode parent = null;
        TreeNode current = root;
        while (current != null) {
            if (key.compareTo(current.data) < 0){
                parent = current;
                current = current.left;
            } else if (key.compareTo(current.data) > 0) {
                parent = current;
                current = current.right;
            } else {
                // if key is found in the tree, remove it.
                TreeOperations.removeNode(parent, current);
                return true;
            }
        }
        /* Below for loop searches for the key in the array, if found,
           it traverses through the array and remove the element by replacing the key by the elements to its right
           and this shifting process starts from the index where key is found and done until the end of the array.
         */
        for(int i=0; i< valuesToBeAdded.length; i++){
            if(Objects.equals(key, valuesToBeAdded[i])){
                for(int j = i+1; j< valuesToBeAdded.length; j++ ){
                    valuesToBeAdded[i] = valuesToBeAdded[j];
                    i++;
                }
                valuesToBeAdded[i] = null;
                return true;
            }
        }
        return false;
    }
    // Below method returns the number of elements in the Amortized tree.
    @Override
    public int size() {
        int SizeOfTree = TreeOperations.treeSize(root);
        int SizeOfArray = 0;
        for(String s: valuesToBeAdded){
            if(s != null){
                SizeOfArray += 1;
            }
        }
        return SizeOfTree + SizeOfArray;
    }

    // Below method rebalances the tree
    @Override
    public boolean rebalance() {
        try {
            String[] bal = treeValues();
            bal = Helper.middleSortArray(bal);
            root = null;
            for (String s : bal) {
                TreeOperations.insertIntoTree(root, s); //
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    // below method rebalances the tree but the key will be the root node of the tree
    @Override
    public boolean rebalanceValue(String key) {
        try {
            if (key == null || key.equals("")) {
                return false;
            }
            key = key.toLowerCase();
            if (!TreeOperations.searchInTreeOnly(root, key)) {
                return false;
            }
            String[] bal = treeValues();
            bal = Helper.middleSortArray(bal);
            root = new TreeNode(key);
            for (String s : bal) {
                if (Objects.equals(s, key)) {
                    continue;
                }
                TreeOperations.insertIntoTree(root, s);
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    // Below method is used to print the contents of the tree along with their depth level
    @Override
    public String printTree() {
        if (root == null) {
            return null;
        }
        StringBuilder nodeAndDepthString = new StringBuilder();
        TreeOperations.printValueAndDepth(root, nodeAndDepthString, 1);
        return nodeAndDepthString.toString();
}

    // Below method returns an array of all the values that are waiting to be
    // added into the unbalanced tree.
    @Override
    public String[] awaitingInsertion() {
        int count = 0;
        for(String s: valuesToBeAdded){
            if(s != null){
                count += 1;
            }
        }
        String[] arr = new String[count];
        System.arraycopy(valuesToBeAdded, 0, arr, 0, count);
        Arrays.sort(arr);
        return arr;
    }

    // Below method returns an array of all the values that are in the unbalanced tree.
    // The values should be in ascending sorted order.
    @Override
    public String[] treeValues() {
        StringBuilder treeValueBuilder = new StringBuilder();
        TreeOperations.ArrayOfValue(root, treeValueBuilder);
        return treeValueBuilder.toString().split(" ");
    }

    // Below method searches for the key passed as the parameter in the AmortizedTree,
    // if present in the tree, it returns depth,
    // if found in the array, it returns its index*-1, if not found returns sizeOfTree * 2
    @Override
    public int depth(String key) {
        key = key.toLowerCase();
        int found = TreeOperations.searchKeyDepthInTreeOnly(root, key, 1);
        if(found != -1){
            return found;
        }
        for(int i = 0; i< valuesToBeAdded.length; i++){
            if(valuesToBeAdded[i] == key){
                return i * -1;
            }
        }
        return TreeOperations.treeSize(root) * 2;
    }

    public static void main(String[] args) {
        AmortizedTree a = new AmortizedTree(new TreeNode("10"));
        a.add("20");
        a.add("30");
        a.add("40");
        System.out.println(a.printTree());

        String[] arr = new String[5];
        arr[0] = "hello";
        String s1 = "hello";
        String s2 = new String("hello");
        if(arr[2] == s2){

        }
    }

}
