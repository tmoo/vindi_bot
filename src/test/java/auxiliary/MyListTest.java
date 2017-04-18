package auxiliary;

import java.util.Iterator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Explicitly tested mainly for cases where the list contains an int-array, as
 * that is used in my program, and for integers, as those are fast to type.
 * However, most test should generalize to other cases.
 *
 * @author tuomo
 */
public class MyListTest {

    List list;
    List list2;

    @Before
    public void setUp() {
        list = new MyList<>();

        // Initialized with initial capacity to see it doesn't crash
        list2 = new MyList<>(5);
        list2.add(2);
        list2.add(6);
        list2.add(3);
    }

    @Test
    public void isEmptyWorksWhenNotEmpty() {
        list.add("ads");

        assertFalse(list.isEmpty());
    }

    @Test
    public void sizeAndIsEmptyWorkWhenEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void sizeWorksWhenNotEmpty() {
        assertEquals(3, list2.size());
    }

    @Test
    public void addingAndGettingWorksForInteger() {
        int result = (Integer) list2.get(0);

        assertEquals(2, result);
    }

    @Test
    public void addingAndGettingWorksForArray() {
        int[] array = {2, 5, 3};
        list.add(array);

        assertArrayEquals(array, (int[]) list.get(0));
    }

    @Test
    public void containsWorksForInteger() {
        assertTrue(list2.contains(6));
    }

    @Test
    public void containsWorksForArrayWhenContains() {
        int[] array = {2, 5, 3};
        list.add(array);

        assertTrue(list.contains(array));
    }

    @Test
    public void containsKnowsNullNotIn() {
        assertFalse(list2.contains(null));
    }

    @Test
    public void containsWorksForArrayWhenDoesNotContain() {
        int[] array = {2, 5, 3};
        int[] array2 = {2, 5, 3};
        list.add(array);

        assertFalse(list.contains(array2));
    }

    @Test
    public void containsWorksWhenAskingForNull() {
        int[] array = {2, 5, 3};
        assertTrue(list.add(array));

        assertFalse(list.contains(null));
    }

    @Test
    public void containsWorksWhenAskingForNullAndListContainsNull() {
        list.add(2);
        list.add(null);

        assertTrue(list.contains(null));
    }

    @Test
    public void containsDoesNotCrashWhenListContainsNull() {
        assertTrue(list.add(2));
        assertTrue(list.add(null));

        assertFalse(list.contains(5));
    }

    @Test
    public void toArrayWorksWithoutParam() {
        Object[] array = list2.toArray();

        assertEquals(3, array.length);
        assertEquals(2, array[0]);
    }

    @Test
    public void toArrayWorksWithoutParamForEmptyList() {
        Object[] array = list.toArray();

        assertEquals(0, array.length);
    }

    @Test
    public void iteratorWorks() {
        Iterator iter = list2.iterator();

        assertTrue(iter.hasNext());
        assertEquals(2, iter.next());
        assertEquals(6, iter.next());
        assertEquals(3, iter.next());
        assertFalse(iter.hasNext());
    }

    @Test
    public void addingWorksAfterGoingOverCapacity() {
        list2.add(5);
        list2.add(32);
        list2.add(4);
        list2.add(34);
        list2.add(352);
        list2.add(35);
        assertEquals(9, list2.size());
    }

    @Test
    public void addingNullWorks() {
        assertTrue(list2.add(null));

        assertTrue(list2.contains(null));
    }

    @Test
    public void removeWorksWhenListContainsElement() {
        assertTrue(list2.remove((Integer) 2));

        assertEquals(2, list2.size());
        assertEquals(6, list2.get(0));
        assertEquals(3, list2.get(1));
    }

    @Test
    public void removeWorksWhenListContainsElement2() {
        assertTrue(list2.remove((Integer) 3));

        assertEquals(2, list2.size());
        assertEquals(2, list2.get(0));
        assertEquals(6, list2.get(1));
    }

    @Test
    public void removeWorksWhenListContainsElement3() {
        assertTrue(list2.remove((Integer) 6));

        assertEquals(2, list2.size());
        assertEquals(2, list2.get(0));
        assertEquals(3, list2.get(1));
    }

    @Test
    public void removeWorksWhenListDoesNotContainElement() {
        assertFalse(list2.remove((Integer) 10));

        assertEquals(3, list2.size());
    }

    @Test
    public void canRemoveNullWhenListContainsNull() {
        assertTrue(list.add(null));
        assertTrue(list.remove(null));
        assertTrue(list.isEmpty());
    }

    @Test
    public void doesNotBreakWhenTryingToRemoveNullWhenListDoesNotContainNull() {
        assertFalse(list2.remove(null));

        assertEquals(3, list2.size());
    }

    @Test
    public void doesNotBreakWhenTryingToRemoveFromEmptyList() {
        assertFalse(list.remove("asd"));
        assertEquals(0, list.size());
    }

    @Test
    public void removingByIndexWorks() {
        assertEquals(3, list2.remove(2));

        assertEquals(2, list2.size());
        assertEquals(2, list2.get(0));
        assertEquals(6, list2.get(1));
    }
}
