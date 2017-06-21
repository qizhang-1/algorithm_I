/* --------------------------------------------
 * File Name : RandomizedQueue.java
 * Purpose :
 * Creation Date : 06-11-2017
 * Last Modified : Mon Jun 12 19:06:57 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size  = 0;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return size;
    }
    
    private void resize(int N) {
        Item[] temp = (Item[]) new Object[N];
        for (int i = 0; i < size; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)   throw new NullPointerException();
        items[size++] = item;
        if (size == items.length) {
            resize(2 * size);
        }
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())    throw new NoSuchElementException();
        int id = (int) (StdRandom.uniform() * size);
        Item item = items[id];
        items[id] = items[--size];
        items[size] = null;
        if (size <= items.length/4) {
            resize(items.length/2);
        }
        return item;
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty())    throw new NoSuchElementException();
        int id = (int) (StdRandom.uniform() * size);
        return items[id];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] temp;
        public RandomizedQueueIterator() {
            temp = (Item[]) new Object[size];
            for (int j = 0; j < size; j++) {
                temp[j] = items[j];
            }
        }
        
        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            if (!hasNext())    throw new NoSuchElementException();
            int id = (int) (StdRandom.uniform()) * (size - 1);
            Item item = temp[id];
            temp[id] = temp[size - (++i)];
            temp[size - i] = null;
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
    }
}
