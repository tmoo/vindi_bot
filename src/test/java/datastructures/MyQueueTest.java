package datastructures;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author tuomo
 */
public class MyQueueTest {

    MyQueue queue;

    @Before
    public void setUp() {
        queue = new MyQueue();
    }

    @Test
    public void offerWorks() {
        queue.add(5);
        assertEquals(5, queue.element());
        assertEquals(1, queue.size());
    }

    @Test
    public void addingWorks() {
        queue.add(2);
        queue.add(34);
        assertEquals(2, queue.element());
        assertEquals(2, queue.size());
    }

    @Test
    public void isEmptyWorks() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void peekWorksWhenEmpty() {
        assertEquals(null, queue.peek());
    }

    @Test
    public void elementDoesntRemove() {
        queue.add(2);
        queue.add(34);
        assertEquals(2, queue.element());
        assertEquals(2, queue.element());
    }

    @Test
    public void pollWorks() {
        queue.add(2);
        queue.add(34);
        assertEquals(2, queue.poll());
        assertEquals(34, queue.poll());
    }

    @Test
    public void pollWorksForEmpty() {
        assertEquals(null, queue.poll());
    }

    @Test
    public void worksAfterRemoving() {
        queue.add(2);
        queue.add(34);
        assertEquals(2, queue.poll());
        queue.add(15);
        assertEquals(34, queue.poll());
    }

    @Test
    public void worksWhenBecomesFull() {
        for (int i = 0; i < 22; i++) {
            queue.add(i);
        }
        for (int i = 0; i < 21; i++) {
            queue.poll();
        }
        assertEquals(21, queue.poll());
    }

    @Test
    public void containsWorksWhenDoesContain() {
        queue.add(2);
        queue.add(34);
        assertTrue(queue.contains(34));
        assertTrue(queue.contains(2));
    }

    @Test
    public void containsWorksWhenDoesntContain() {
        assertTrue(!queue.contains(34));
    }

    @Test
    public void containsWorksWhenFull() {
        for (int i = 0; i < 22; i++) {
            queue.add(i);
        }
        assertTrue(queue.contains(15));
        assertTrue(queue.contains(21));
    }

    @Test
    public void queIsEmptyAfterRemovingElements() {
        queue.add(54);
        queue.poll();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void pollingDoesntOverflowTheCursor() {
        for (int i = 0; i < 23; i++) {
            queue.poll();
        }
        queue.add(5);
        assertEquals(5, queue.poll());
    }

    @Test
    public void cantAddNull() {
        queue.add(null);
        assertTrue(queue.isEmpty());
        assertFalse(queue.contains(null));
    }

    @Test
    public void doesntContainNull() {
        assertFalse(queue.contains(null));
    }

    @Test
    public void resizingDoesntMessUpWithNulls() {
        for (int i = 0; i < 12; i++) {
            queue.add(i);
        }

        queue.poll();
        queue.poll();
        queue.poll();

        for (int i = 0; i < 22; i++) {
            queue.add(i);
        }

        queue.poll();
        queue.poll();

        assertFalse(queue.contains(null));
        assertEquals(29, queue.size());
    }

    @Test
    public void resizingDoesntMessUpWithNulls2() {
        for (int i = 0; i < 12; i++) {
            queue.add(i);
        }
        queue.poll();
        queue.poll();
        queue.poll();

        for (int i = 0; i < 12; i++) {
            queue.add(i);
        }

        queue.poll();
        queue.poll();

        assertFalse(queue.contains(null));
        assertEquals(19, queue.size());
    }

    @Test
    public void resizingDoesntMessUpOrder() {
        for (int i = 0; i < 12; i++) {
            queue.add(i);
        }
        queue.poll();
        queue.poll();
        queue.poll();
        
        for (int i = 0; i < 15; i++) {
            queue.add(i);
        }
        
        for (int i = 3; i < 12; i++) {
            assertEquals(i, queue.poll());
        }
        for (int i = 0; i < 15; i++) {
            assertEquals(i, queue.poll());
        }
        
        queue.add(5);
        queue.add(554);
        queue.add(45);
        assertEquals(5, queue.poll());
        assertEquals(554, queue.poll());
        assertEquals(45, queue.poll());

    }

}
