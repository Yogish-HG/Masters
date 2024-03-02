public interface Searchable {
    public boolean add( String key );
    public boolean find( String key );
    public boolean remove( String key );
    public int size();
    public boolean rebalance();
    public boolean rebalanceValue( String key );
}
