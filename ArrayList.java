public class ArrayList<T> implements List<T> {
    private Object[] elements;
    private int size;

    public ArrayList() {
        this.elements = new Object[10];
        this.size = 0;
    }

    public void add(T element) {
        if (size == elements.length) {
            // Increase the size of the array by creating a new array with double the capacity
            Object[] newElements = new Object[elements.length * 2];
            // Copy the elements from the old array to the new array
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
        elements[size++] = element;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) elements[index];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void grow_array() {
        Object[] newElements = new Object[elements.length * 2];
        // Copy the elements from the old array to the new array
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == elements.length) {
            // Increase the size of the array by creating a new array with double the capacity
            grow_array();
        }
        // Shift elements to the right to make space for the new element
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        // Insert the new element at the specified index
        elements[index] = element;
        size++;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }
    
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T removedElement = (T) elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null; // Set the last element to null
        size--;
        return removedElement;
    }
    
    
}


