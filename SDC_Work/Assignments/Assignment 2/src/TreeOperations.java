public class TreeOperations {

    // Below method finds the number of nodes in the tree
    public static int treeSize(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + treeSize(node.left) + treeSize(node.right);
    }

    // Below method is used to insert the data into the Tree by traversing through the tree.
    public static TreeNode insertIntoTree(TreeNode Node, String data) {
        if (Node == null) {
            Node = new TreeNode(data);
            return Node;
        }

        TreeNode current = Node;
        while (true) {
            /* if data(String value) is less than the current node refers to its left node if it's not null,
               if its null it creates a new node to its left.*/
            if (data.compareTo(current.data) < 0) {
                if (current.left == null) {
                    current.left = new TreeNode(data);
                    return Node;
                }
                current = current.left;

            /* if data(String value) is grater than the current node refers to its right node if it's not null,
               if its null it creates a new node to its right.*/
            } else if (data.compareTo(current.data) > 0) {
                if (current.right == null) {
                    current.right = new TreeNode(data);
                    return Node;
                }
                current = current.right;
            }
        }
    }

//    public static void insertIntoTree(TreeNode Node, String data) {
//
//        TreeNode current = Node;
//        while (true) {
//            /* if data(String value) is less than the current node refers to its left node if it's not null,
//               if its null it creates a new node to its left.*/
//            if (data.compareTo(current.data) < 0) {
//                if (current.left == null) {
//                    current.left = new TreeNode(data);
//                    return;
//                }
//                current = current.left;
//
//            /* if data(String value) is grater than the current node refers to its right node if it's not null,
//               if its null it creates a new node to its right.*/
//            } else if (data.compareTo(current.data) > 0) {
//                if (current.right == null) {
//                    current.right = new TreeNode(data);
//                    return;
//                }
//                current = current.right;
//            }
//        }
//    }

    // Below method traverses through the tree and returns true if the "value" is in the tree.
    public static boolean searchInTreeOnly(TreeNode root, String value) {
        while (root != null) {
            int result = value.compareTo(root.data);
            if (result == 0) {
                return true;
            } else if (result < 0) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return false;
    }

    // finds and returns the depth of the value in the tree.

    public static int searchKeyDepthInTreeOnly(TreeNode root, String value, int count) {
        while (root != null) {
            int result = value.compareTo(root.data);
            if (result == 0) {
                return count;
            } else if (result < 0) {
                count += 1;
                root = root.left;
            } else {
                count += 1;
                root = root.right;
            }
        }
        return -1;
    }

    // This method is called from AmortizedTree when a node which is found is to be removed from the tree
    public static void removeNode(TreeNode parentOfFound, TreeNode found) {
        if (found.left == null && found.right == null) {
            if (parentOfFound.left == found) {
                parentOfFound.left = null;
            } else if(parentOfFound.right == found){
                parentOfFound.right = null;
            }
        }
         else if (found.right == null) {
            if (parentOfFound.left == found) {
                parentOfFound.left = found.left;
            } else {
                parentOfFound.right = found.left;
            }
        } else if (found.left == null) {
            if (parentOfFound.left == found) {
                parentOfFound.left = found.right;
            } else {
                parentOfFound.right = found.right;
            }
        } else {
            TreeNode smallestParent = found;
            TreeNode smallest = found.right;
            while (smallest.left != null) {
                smallestParent = smallest;
                smallest = smallest.left;
            }
            found.data = smallest.data;
            if (smallestParent.left == smallest) {
                smallestParent.left = smallest.right;
            } else {
                smallestParent.right = smallest.right;
            }
        }
    }

    // Below method uses a stringBuilder and returns the value and its depth in the tree in their ascending order
    // (in-order traversal).
    public static void printValueAndDepth(TreeNode node, StringBuilder builder, int depth) {
        if (node == null) {
            return;
        }
        printValueAndDepth(node.left, builder, depth + 1);
        builder.append(node.data).append(" ").append(depth).append("\n");
        printValueAndDepth(node.right, builder, depth + 1);
    }

    // Below method uses a stringBuilder and returns the value in the ascending order(in-order traversal).

    public static void ArrayOfValue(TreeNode node, StringBuilder builder) {
        if (node == null) {
            return;
        }
        ArrayOfValue(node.left, builder);
        builder.append(node.data).append(" ");
        ArrayOfValue(node.right, builder);
    }
}
