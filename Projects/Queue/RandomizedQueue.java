port edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
 
import java.util.Iterator;
import java.util.NoSuchElementException;
 
public class RandomizedQueue<Item> implements Iterable<Item> {
    /* @citation Adapted from: Algorithms, 4th Edition Essential Information
    about Algorithms and Data Structures (Robert Sedgewick, Kevin Wayne)
    2011 Pearson Education, Inc. Accessed 9/22/2022. */
 
    private Item[] a;     // resizeable array of objects
    private int size;     // number of items in resizeable array
 
 
    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[1]; 
        size = 0;
    }
 
    // is the randomized queue empty?
    public boolean isEmpty() {
        if (size == 0) { 
            return true; 
        } 
        return false; 
    }
 
    // return the number of items on the randomized queue
    public int size() {
        return size;
    }
 
    // resizes array when full or three fourths empty
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
 
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
 
        if (size == a.length) {
            resize(2 * a.length);
        }
        a[size++] = item;
    }
 
    // remove and return a random item
    public Item dequeue() {
 
        if (size == 0) {
            throw new NoSuchElementException();
        }
 
        int i = StdRandom.uniformInt(0, size);
        Item itemreturn = a[i];
        a[i] = a[size - 1];
        a[size - 1] = null;
        size--;
        if (size > 0 && size == a.length / 4) {
            resize(a.length / 2);
        }
 
        return itemreturn;
 
    }
 
 
    // return a random item (but do not remove it)
    public Item sample() {
 
        if (size == 0) {
            throw new NoSuchElementException();
        }
 
        int i = StdRandom.uniformInt(0, size);
        Item itemreturn = a[i];
        return itemreturn;
    }
 
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }
 
    // iterator over items in random order
    private class RandomQueueIterator implements Iterator<Item> {
 
        int current = size;     // places start of iterator at the end of array
        Item[] b;               // array to be iterated
 
 
        // shuffles copied array in uniform random order
        public RandomQueueIterator() {
 
            b = (Item[]) new Object[size];
            for (int j = 0; j < size; j++) {
                b[j] = a[j];
            }
            StdRandom.shuffle(b);
        }
 
        // returns true if another element exists
        public boolean hasNext() {
            if (current > 0) {
                return true;
            }
            return false;
        }
 
        // returns element in array and advacnes
        public Item next() {
 
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
 
            Item itemreturn = b[--current];
            return itemreturn;
        }
    }
 
 
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
 
        StdOut.println(queue.isEmpty());
        StdOut.println(queue.size());
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        StdOut.println(queue.size);
        StdOut.println(queue.isEmpty());
        StdOut.println(queue.sample());
        StdOut.println(queue.dequeue());
 
        for (Integer i : queue) {
            StdOut.print(i);
        }
 
 
    }
}
 
 
