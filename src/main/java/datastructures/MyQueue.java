package datastructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 *
 * @author tuomo
 */
public class MyQueue<E> implements Queue<E> {

    private static final int INITIAL_CAPACITY = 20;
    private Object[] array;
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

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean offer(E e) {
        if (count >= array.length) {
            Object[] newArray = new Object[count * 2];
            System.arraycopy(array, 0, newArray, 0, count);
            array = newArray;
        }
        
        int nextPos = (cursor + count) % array.length;
        array[nextPos] = e;
        count++;

        return true;
    }

    @Override
    public E remove() {
        E e = poll();
        if (e != null) {
            return e;
        } else {
            throw new NoSuchElementException();
        }
    }

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

    @Override
    public E element() {
        E e = peek();
        if (e != null) {
            return e;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public E peek() {
        return (E) array[cursor];
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean contains(Object o) {
        int j = 0;
        while (j < count) {
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

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
