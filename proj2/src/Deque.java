/* --------------------------------------------
 * File Name : Deque.java
 * Purpose :
 * Creation Date : 06-11-2017
 * Last Modified : Mon Jun 12 18:32:12 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node prev, next;
        public Node(Item item){
            this.item = item;
        }
    }
    private Node first, last;
    private int size;
    
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)   throw new NullPointerException();
        Node node = new Node(item);
        if (isEmpty()) {
            first = node;
            last  = node;
        }
        else {
            node.next = first;
            first.prev = node;
            first = node;
        }
        size++;
    }
    
    // add the item to the end
    public void addLast(Item item) {
        if (item == null)   throw new NullPointerException();
        Node node = new Node(item);
        if (isEmpty()) {
            first = node;
            last  = node;
        }
        else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        size++;
        
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())  throw new NoSuchElementException();
        Item item = first.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return item;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())  throw new NoSuchElementException();
        Item item = last.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return item;
    }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<Item>() {
            private Node curr = first;
            public boolean hasNext(){
                return curr != null;
            }
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item item = (Item) curr.item;
                curr = curr.next;
                return item;
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
    }
    
}
