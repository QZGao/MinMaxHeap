import junit.framework.TestCase;
import com.google.common.collect.MinMaxPriorityQueue;
import java.util.Random;

public class MinMaxHeapTest extends TestCase {

    public void testBuild() {
        MinMaxHeap<Integer> heap = new MinMaxHeap<>(34, 12, 28, 9, 30, 19, 1, 40);
        assertEquals("[1, 40, 34, 9, 30, 19, 28, 12]", heap.toString());

        heap = new MinMaxHeap<>(8, 71, 41, 31, 10, 11, 16, 46, 51, 31, 21, 13);
        assertEquals("[8, 71, 41, 31, 10, 11, 16, 46, 51, 31, 21, 13]", heap.toString());
    }

    public void testInsert() {
        MinMaxHeap<Integer> heap = new MinMaxHeap<>();
        assertNull(heap.peekMin());
        assertNull(heap.peekMax());
        for (int i = 1; i <= 10; i++) {
            heap.add(i);
            assertEquals(Integer.valueOf(1), heap.peekMin());
            assertEquals(Integer.valueOf(i), heap.peekMax());
        }
        assertEquals(Integer.valueOf(1), heap.popMin());
        assertEquals(Integer.valueOf(10), heap.popMax());
        assertEquals(Integer.valueOf(2), heap.popMin());
        assertEquals(Integer.valueOf(9), heap.popMax());
        assertEquals(Integer.valueOf(3), heap.popMin());
        assertEquals(Integer.valueOf(4), heap.popMin());
        assertEquals(Integer.valueOf(5), heap.popMin());
        assertEquals(Integer.valueOf(8), heap.popMax());
        assertEquals(Integer.valueOf(7), heap.popMax());
        assertEquals(Integer.valueOf(6), heap.popMax());
        assertNull(heap.popMin());
        assertNull(heap.popMax());
    }

    public void testRandom() {
        Random rand = new Random();
        for (int round = 0; round < 10000; round++) {
            MinMaxHeap<Integer> heap = new MinMaxHeap<>();
            MinMaxPriorityQueue<Integer> queue = MinMaxPriorityQueue.create();
            for (int i = 0; i < 1000; i++) {
                int value = rand.nextInt(100000);
                heap.add(value);
                queue.add(value);
            }
            for (int i = 0; i < 1000; i++) {
                if (rand.nextBoolean()) {
                    assertEquals(queue.pollFirst(), heap.popMin());
                } else {
                    assertEquals(queue.pollLast(), heap.popMax());
                }
            }
        }
    }
}
