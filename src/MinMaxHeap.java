import java.util.ArrayList;
import java.util.List;

public class MinMaxHeap<T extends Comparable<T>> {
    private final List<T> array;

    @SafeVarargs
    public MinMaxHeap(T... elements) {
        array = new ArrayList<>();
        if (elements.length > 0) {
            array.addAll(List.of(elements));
            build();
        }
    }

    private void build() {
        for (int i = parent(array.size()); i > 0; --i) {
            bubbleDown(i);
        }
    }

    private void bubbleDown(int i) {
        if (onMinLevel(i)) {
            bubbleDownMin(i);
        } else {
            bubbleDownMax(i);
        }
    }

    private void bubbleDownMin(int i) {
        if (hasLeftChild(i)) {
            int m = smallestDescendant(i);
            if (isGrandchild(i, m)) {
                if (compare(m, i) < 0) {
                    swap(m, i);
                    if (compare(m, parent(m)) > 0) {
                        swap(m, parent(m));
                    }
                    bubbleDown(m);
                }
            } else if (compare(m, i) < 0) {
                swap(m, i);
            }
        }
    }

    private void bubbleDownMax(int i) {
        if (hasLeftChild(i)) {
            int m = largestDescendant(i);
            if (isGrandchild(i, m)) {
                if (compare(m, i) > 0) {
                    swap(m, i);
                    if (compare(m, parent(m)) < 0) {
                        swap(m, parent(m));
                    }
                    bubbleDown(m);
                }
            } else if (compare(m, i) > 0) {
                swap(m, i);
            }
        }
    }

    public void add(T element) {
        array.add(element);
        bubbleUp(array.size());
    }

    private void bubbleUp(int i) {
        if (i != 1) {
            if (onMinLevel(i)) {
                if (compare(i, parent(i)) > 0) {
                    swap(i, parent(i));
                    bubbleUpMax(parent(i));
                } else {
                    bubbleUpMin(i);
                }
            } else {
                if (compare(i, parent(i)) < 0) {
                    swap(i, parent(i));
                    bubbleUpMin(parent(i));
                } else {
                    bubbleUpMax(i);
                }
            }
        }
    }

    private void bubbleUpMin(int i) {
        if (hasGrandparent(i) && compare(i, grandparent(i)) < 0) {
            swap(i, grandparent(i));
            bubbleUpMin(grandparent(i));
        }
    }

    private void bubbleUpMax(int i) {
        if (hasGrandparent(i) && compare(i, grandparent(i)) > 0) {
            swap(i, grandparent(i));
            bubbleUpMax(grandparent(i));
        }
    }

    private int toIndex(int i) {
        return i - 1;
    }

    private int compare(int i, int j) {
        return array.get(toIndex(i)).compareTo(array.get(toIndex(j)));
    }

    private void swap(int i, int j) {
        T temp = array.get(toIndex(i));
        array.set(toIndex(i), array.get(toIndex(j)));
        array.set(toIndex(j), temp);
    }

    private int parent(int i) {
        return i >> 1;
    }

    private int grandparent(int i) {
        return i >> 2;
    }

    private boolean hasGrandparent(int i) {
        return grandparent(i) > 0;
    }

    private int leftChild(int i) {
        return i << 1;
    }

    private int rightChild(int i) {
        return (i << 1) + 1;
    }

    private int firstGrandchild(int i) {
        return i << 2;
    }

    private int lastGrandchild(int i) {
        return (i << 2) + 3;
    }

    private boolean hasLeftChild(int i) {
        return leftChild(i) <= array.size();
    }

    private List<Integer> children(int i) {
        List<Integer> children = new ArrayList<>();
        if (leftChild(i) <= array.size()) children.add(leftChild(i));
        if (rightChild(i) <= array.size()) children.add(rightChild(i));
        return children;
    }

    private List<Integer> grandchildren(int i) {
        List<Integer> grandchildren = new ArrayList<>();
        for (int d = firstGrandchild(i); d <= lastGrandchild(i); ++d) {
            if (d > array.size()) break;
            grandchildren.add(d);
        }
        return grandchildren;
    }

    private List<Integer> descendants(int i) {
        List<Integer> descendants = new ArrayList<>();
        descendants.addAll(children(i));
        descendants.addAll(grandchildren(i));
        return descendants;
    }

    private int smallestDescendant(int i) {
        int m = leftChild(i);
        for (Integer d : descendants(i)) {
            if (compare(d, m) < 0) m = d;
        }
        return m;
    }

    private int largestDescendant(int i) {
        int m = leftChild(i);
        for (Integer d : descendants(i)) {
            if (compare(d, m) > 0) m = d;
        }
        return m;
    }

    private boolean isGrandchild(int p, int d) {
        return p == grandparent(d);
    }

    private int bitLength(int i) {
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    private boolean onMinLevel(int i) {
        return (bitLength(i) & 1) == 0;
    }

    private int minI() {
        return array.isEmpty() ? 0 : 1;
    }

    private int maxI() {
        if (array.isEmpty()) return 0;
        if (array.size() == 1) return 1;
        if (array.size() > 2 && compare(3, 2) > 0) return 3;
        else return 2;
    }

    private T peekI(int i) {
        return i == 0 ? null : array.get(toIndex(i));
    }

    private T popI(int i) {
        if (i == 0) return null;
        T element = peekI(i);
        swap(i, array.size());
        array.removeLast();
        bubbleDown(i);
        return element;
    }

    public T peekMin() {
        return peekI(minI());
    }

    public T popMin() {
        return popI(minI());
    }

    public T peekMax() {
        return peekI(maxI());
    }

    public T popMax() {
        return popI(maxI());
    }

    @Override
    public String toString() {
        return array.toString();
    }
}