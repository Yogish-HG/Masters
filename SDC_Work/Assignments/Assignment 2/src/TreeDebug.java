public interface TreeDebug extends Searchable {
    public String printTree();
    public String[] awaitingInsertion();
    public String[] treeValues();
    public int depth( String key );
}
