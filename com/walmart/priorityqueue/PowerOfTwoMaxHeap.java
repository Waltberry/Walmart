package com.walmart.priorityqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PowerOfTwoMaxHeap {
    private static final Logger LOGGER = Logger.getLogger(PowerOfTwoMaxHeap.class.getName());
    private static final String LOG_MESSAGE = "Max value: {0}";

    private List<Integer> heap;
    private int k; // Number of children is 2^k

    public PowerOfTwoMaxHeap(int k) {
        this.heap = new ArrayList<>();
        this.k = k;
    }

    public void insert(int value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    public int popMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        int maxValue = heap.get(0);
        int lastValue = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastValue);
            siftDown(0);
        }

        return maxValue;
    }

    private void siftUp(int index) {
        int value = heap.get(index);
        while (index > 0) {
            int parentIndex = (index - 1) / (int) Math.pow(2, k);
            int parentValue = heap.get(parentIndex);

            if (value <= parentValue) {
                break;
            }

            heap.set(index, parentValue);
            index = parentIndex;
        }
        heap.set(index, value);
    }

    private void siftDown(int index) {
        int size = heap.size();
        int value = heap.get(index);

        while (true) {
            int maxChildIndex = -1;
            int maxChildValue = Integer.MIN_VALUE;

            for (int i = 1; i <= Math.pow(2, k); i++) {
                int childIndex = index * (int) Math.pow(2, k) + i;
                if (childIndex < size && heap.get(childIndex) > maxChildValue) {
                    maxChildValue = heap.get(childIndex);
                    maxChildIndex = childIndex;
                }
            }

            if (maxChildIndex == -1 || value >= maxChildValue) {
                break;
            }

            heap.set(index, maxChildValue);
            index = maxChildIndex;
        }
        heap.set(index, value);
    }

    public static void main(String[] args) {
        // Example usage and testing
        PowerOfTwoMaxHeap heap = new PowerOfTwoMaxHeap(2); // Each parent has 2^2 = 4 children

        heap.insert(10);
        heap.insert(20);
        heap.insert(15);
        heap.insert(30);
        heap.insert(40);

        LOGGER.log(Level.INFO, LOG_MESSAGE, heap.popMax()); // Should print 40
        LOGGER.log(Level.INFO, LOG_MESSAGE, heap.popMax()); // Should print 30
        LOGGER.log(Level.INFO, LOG_MESSAGE, heap.popMax()); // Should print 20
    }
}
