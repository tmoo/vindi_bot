package datastructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * My implementation of queue. Only most commonly used methods implemented, in
 * particular the ones used in TheBot.
 *
 * @author tuomo
 */
public class MyQueue<E> implements Queue<E> {

    private static final int INITIAL_CAPACITY = 20;
    Object[] array;
    private int cursor;
    private int count;

    public MyQueue(int capacity) {
        cursor = 0;
        count = 0;
        this.array = new Object[capacity];
    }

    public MyQueue() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Add e to the end of this queue.
     *
     * @param e Element to be added.
     * @return True in any case.
     */
    @Override
    public boolean add(E e) {
        return offer(e);
    }

    /**
     * Add e to the end of this queue, unless e is null.
     *
     * @param e Element to be added.
     * @return False if e is null, true otherwise.
     */
    @Override
    public boolean offer(E e) {
        if (e == null) {
            return false;
        }
        if (count >= array.length) {
            /* Create a new array and straigthen the already existing inputs
            so that cursor starts at 0.
            */
            Object[] newArray = new Object[count * 2];
            System.arraycopy(array, cursor, newArray, 0, array.length - cursor);
            System.arraycopy(array, 0, newArray, array.length - cursor, cursor);
            array = newArray;
            cursor = 0;
        }

        int nextPos = (cursor + count) % array.length;
        array[nextPos] = e;
        count++;

        return true;
    }

    /**
     * Remove and return the first element or throw exception if empty.
     *
     * @return The element in the first position (before being removed) of the
     * queue.
     */
    @Override
    public E remove() {
        E e = poll();
        if (e != null) {
            return e;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Remove and return the first element. Null if empty.
     *
     * @return first element in the queue, null if empty
     */
    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E e = (E) array[cursor];
        array[cursor] = null;
        cursor = (cursor + 1) % array.length;
        count = Math.max(0, count - 1);
        return e;
    }

    /**
     * Return but don't remove the first element in the queue, throw exception
     * if empty.
     *
     * @return First element in the queue.
     */
    @Override
    public E element() {
        E e = peek();
        if (e != null) {
            return e;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Return but don't remove the first element in the queue.
     *
     * @return First element in the queue, null if empty.
     */
    @Override
    public E peek() {
        return (E) array[cursor];
    }

    /**
     *
     * @return Number of element in the queue.
     */
    @Override
    public int size() {
        return count;
    }

    /**
     *
     * @return True if there are no elements in the queue, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return count <= 0;
    }

    /**
     *
     * @param o Object whose containment is checked.
     * @return True if o is contained in the queue, false otherwise.
     */
    @Override
    public boolean contains(Object o) {
        int j = cursor;
        while (j < count + cursor) {
            int i = j % array.length;
            j++;
            Object e = array[i];
            if (o == null) {
                if (e == null) {
                    return true;
                } else {
                    continue;
                }
            }
            if (o.equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Not supported.
     *
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Not supported.
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Not supported.
     *
     * @param <T>
     * @param arg0
     * @return
     */
    @Override
    public <T> T[] toArray(T[] arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Not supported.
     *
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Not supported.
     *
     * @param c
     * @return
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Not supported.
     *
     * @param c
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Not supported.
     *
     * @param c
     * @return
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Not supported.
     * @param c
     * @return 
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Not supported.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
