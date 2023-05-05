public interface List<T> {
    
    void add(T element);

    void add(int index, T element);
    
    T get(int index);
    
    T remove(int index);
    
    int size();
    
    // void grow_array(int newSize);

    boolean contains(T element);

    
}
