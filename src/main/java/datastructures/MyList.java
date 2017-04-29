package auxiliary;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * My implementation of list.
 *
 * @author tuomo
 */
public class MyList<E> implements List<E> {

    private static final int INITIAL_CAPACITY = 20;
    private int cursor;
    private Object[] array;

    public MyList(int capacity) {
        cursor = 0;
        this.array = new Object[capacity];
    }

    public MyList() {
        this(INITIAL_CAPACITY);
    }

    @Override
    public int size() {
        return cursor;
    }

    @Override
    public boolean isEmpty() {
        return cursor < 1;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < cursor; i++) {
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
        Iterator<E> iter;
        iter = new Iterator<E>() {

            private int index;

            @Override
            public boolean hasNext() {
                return index < cursor;
            }

            @Override
            public E next() {
                return (E) array[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        return iter;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, cursor);
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(E e) {
        if (cursor >= array.length) {
            Object[] newArray = new Object[cursor * 2];
            System.arraycopy(array, 0, newArray, 0, cursor);
            array = newArray;
        }
        array[cursor] = e;
        cursor++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < cursor; i++) {
            Object e = array[i];
            if (o == null) {
                if (e == null) {
                    // Creates a tiny overhead by unnecesasrily fetching the element
                    // and casting the type, but this should be no problem
                    remove(i);
                    return true;
                }
            } else if (o.equals(e)) {
                // see comment above
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            return false;
        }
        for (Object object : c) {
            if (!this.contains(object)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
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

    @Override
    public E get(int index) {
        return (E) array[index];
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        E e = (E) array[index];
        System.arraycopy(array, index + 1, array, index, cursor - index - 1);
        cursor--;
        return e;
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
