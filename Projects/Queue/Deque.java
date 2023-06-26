import edu.princeton.cs.algs4.StdOut;
 
import java.util.Iterator;
import java.util.NoSuchElementException;
 
public class Deque<Item> implements Iterable<Item> {
 
    /* @citation Adapted from: https://algs4.cs.princeton.edu/13stacks
    /LinkedStack.java.html Accessed 9/22/2022. */
 
    private Node first;       // top of the deque
    private Node last;        // bottom of the deque
    private int size;         // number of items in deque
 
 
    // private Node helper class
    private class Node {
 
        private Item item;     // reference to node object
        private Node next;     // reference to next
        private Node prev;     // reference to prev
 
    }
 
    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }
 
    // is the deque empty?
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
 
    // return the number of items on the deque
    public int size() {
        return size;
    }
 
    // add the item to the front
    public void addFirst(Item item) {
 
        if (item == null) {
            throw new IllegalArgumentException();
        }
 
        if (first == null) {
            first = new Node();
            first.item = item;
            last = first;
            size += 1;
        }
 
        else {
            Node temp = first;
            first = new Node();
            first.item = item;
            first.next = temp;
            temp.prev = first;
            size += 1;
        }
 
    }
 
    // add the item to the back
    public void addLast(Item item) {
 
        if (item == null) {
            throw new IllegalArgumentException();
        }
 
        if (last == null) {
            last = new Node();
            last.item = item;
            first = last;
        }
        else {
 
            Node oldlast = last;
            last = new Node();
            last.item = item;
            oldlast.next = last;
            last.prev = oldlast;
 
        }
        size += 1;
    }
 
    // remove and return the item from the front
    public Item removeFirst() {
 
 
        if (size == 0) {
            throw new NoSuchElementException();
        }
 
        if (size == 1) {
            Item itemreturn = first.item;
            first = null;
            last = null;
            size -= 1;
            return itemreturn;
        }
 
        else {
            Item itemreturn = first.item;
            first = first.next;
            first.prev = null;
            size -= 1;
            return itemreturn;
        }
 
    }
 
    // remove and return the item from the back
    public Item removeLast() {
 
        if (size == 0) {
            throw new NoSuchElementException();
        }
 
        if (size == 1) {
            Item itemreturn = last.item;
            last = null;
            first = null;
            size -= 1;
            return itemreturn;
        }
        else {
            Item itemreturn = last.item;
            last = last.prev;
            last.next = null;
            size -= 1;
            return itemreturn;
        }
    }
 
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
 
 
    // iterator over items from front to back 
    private class DequeIterator implements Iterator<Item> { 
        private Node current;      // current node iterator is on 
  
  
        // Starts iterator at first of list 
        public DequeIterator() { 
            current = first; 
        } 
  
        // returns true if next() exists 
        public boolean hasNext() { 
            if (current != null) { 
                return true; 
            } 
            return false; 
        } 
  
  
        // returns item in current node and advances 
        public Item next() { 
            if (!hasNext()) { 
                throw new NoSuchElementException(); 
            } 
            Item itemreturn = current.item; 
            current = current.next; 
            return itemreturn; 
        } 
  
    } 
 
 
    // unit testing (required)
    public static void main(String[] args) {
 
        Deque<Integer> deque = new Deque<Integer>();
 
        deque.addFirst(1);
        deque.addLast(2);
        StdOut.print(deque.removeFirst());
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.removeLast());
        deque.addFirst(3);
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.size());
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
 
        for (Integer i : deque) {
            StdOut.print(i);
        }
 
 
    }
}
 
