public class LinkedList<T> implements List<T> {
    private class Node<T> {
        T data;
        Node<T> next;
    
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<T>(data);
        if (tail == null) {
            head = newNode;
        } 
        else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public int size() {
        return size;
    }

    public void print() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public boolean contains(T element) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        Node<T> current = head;
        Node<T> previous = null;
        for (int i = 0; i < index; i++) {
            previous = current;
            current = current.next;
        }
        T removed = current.data;
        if (previous == null) {
            head = current.next;
        } else {
            previous.next = current.next;
        }
        if (current == tail) {
            tail = previous;
        }
        size--;
        return removed;
    }

    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(element);
            return;
        }
        Node<T> newNode = new Node<T>(element);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> previous = null;
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                previous = current;
                current = current.next;
            }
            previous.next = newNode;
            newNode.next = current;
        }
        size++;
    }
    
    
}
