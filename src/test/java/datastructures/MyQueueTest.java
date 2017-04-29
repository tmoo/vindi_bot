package datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author tuomo
 */
public class MyQueueTest {
    
    MyQueue que;
    
    @Before
    public void setUp() {
        que = new MyQueue();
    }
    
    @Test
    public void offerWorks() {
        que.add(5);
        assertEquals(5, que.element());
        assertEquals(1, que.size());
    }
    
    @Test
    public void addingWorks() {
        que.add(2);
        que.add(34);
        assertEquals(2, que.element());
        assertEquals(2, que.size());
    }

    @Test
    public void isEmptyWorks() {
        assertTrue(que.isEmpty());
    }
    
    @Test
    public void peekWorksWhenEmpty() {
        assertEquals(null, que.peek());
    }
    
    @Test
    public void elementDoesntRemove() {
        que.add(2);
        que.add(34);
        assertEquals(2, que.element());
        assertEquals(2, que.element());
    }
    
    @Test
    public void pollWorks() {
        que.add(2);
        que.add(34);
        assertEquals(2, que.poll());
        assertEquals(34, que.poll());
    }
    
    @Test
    public void pollWorksForEmpty() {
        assertEquals(null, que.poll());
    }
    
    @Test
    public void worksAfterRemoving() {
        que.add(2);
        que.add(34);
        assertEquals(2, que.poll());
        que.add(15);
        assertEquals(34, que.poll());
    }
    
    @Test
    public void worksWhenBecomesFull() {
        for (int i = 0; i < 22; i++) {
            que.add(i);
        }
        for (int i = 0; i < 21; i++) {
            que.poll();
        }
        assertEquals(21, que.poll());
    }

    @Test
    public void containsWorksWhenDoesContain() {
        que.add(2);
        que.add(34);
        assertTrue(que.contains(34));
        assertTrue(que.contains(2));
    }
    
    @Test
    public void containsWorksWhenDoesntContain() {
        assertTrue(!que.contains(34));
    }
    
    @Test
    public void containsWorksWhenFull() {
        for (int i = 0; i < 22; i++) {
            que.add(i);
        }
        assertTrue(que.contains(15));
        assertTrue(que.contains(21));
    }
    
    @Test
    public void queIsEmptyAfterRemovingElements() {
        que.add(54);
        que.poll();
        assertTrue(que.isEmpty());
    }
    
    @Test
    public void pollingDoesntOverflowTheCursor() {
        for (int i = 0; i < 23; i++) {
            que.poll();
        }
        que.add(5);
        assertEquals(5, que.poll());
    }
    
    @Test
    public void offeringAndPolling() {
        
    }

}
