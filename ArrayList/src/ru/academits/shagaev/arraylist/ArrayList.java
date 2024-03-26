package ru.academits.shagaev.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] items;
    private int size;
    private int modCount;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        items = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость не должна быть отрицательной, переданная вместимость:  " + capacity);
        }

        items = (E[]) new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public ArrayList(Collection<? extends E> collection) {
        items = (E[]) collection.toArray();
        size = items.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new Iterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy" })
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(items, size, a.getClass());
        }

        System.arraycopy(items, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E item) {
        add(size, item);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index != -1) {
            remove(index);
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object e : collection) {
            if (!contains(e)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collectionToAdd) {
        return addAll(size, collectionToAdd);
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    @Override
    public boolean addAll(int index, Collection<? extends E> collectionToAdd) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " выходит за пределы допустимого диапазона [0, " + size + "]");
        }

        Object[] arrayToAdd = collectionToAdd.toArray();
        int numNew = arrayToAdd.length;
        ensureCapacity(size + numNew);

        int numMoved = size - index;

        if (numMoved > 0) {
            System.arraycopy(items, index, items, index + numNew, numMoved);
        }

        System.arraycopy(arrayToAdd, 0, items, index, numNew);
        size += numNew;
        modCount++;
        return numNew != 0;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Objects.requireNonNull(collection);

        if (collection.isEmpty()) {
            return false;
        }

        boolean modified = false;

        for (int i = size - 1; i >= 0; i--) {
            if (collection.contains(items[i])) {
                remove(i);
                modified = true;
            }
        }

        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean wasModified = false;

        for (int i = size - 1; i >= 0; i--) {
            if (!collection.contains(items[i])) {
                remove(i);
                wasModified = true;
            }
        }

        return wasModified;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        Arrays.fill(items, 0, size, null);
        size = 0;
        modCount++;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E item) {
        checkIndex(index);

        E oldItem = items[index];
        items[index] = item;
        return oldItem;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);

        if (size == items.length) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedItem = items[index];

        int numMoved = size - index - 1;

        if (numMoved > 0) {
            System.arraycopy(items, index + 1, items, index, numMoved);
        }

        items[size - 1] = null;
        modCount++;

        return removedItem;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, items[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, items[i])) {
                return i;
            }
        }

        return -1;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " выходит за пределы допустимого диапазона [0, " + (size - 1) + "]");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " выходит за пределы допустимого диапазона [0, " + size + "]");
        }
    }

    public void ensureCapacity(int minCapacity) {
        if (items.length < minCapacity) {
            items = Arrays.copyOf(items, minCapacity);
        }
    }

    private void increaseCapacity() {
        items = Arrays.copyOf(items, Math.max(items.length * 2, 1));
    }

    public void trimToSize() {
        if (size < items.length) {
            items = Arrays.copyOf(items, size);
        }
    }

    private class Iterator implements java.util.Iterator<E> {
        private int currentIndex = -1;
        private int lastReturnedIndex = -1;
        private int expectedModCount;

        Iterator() {
            expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            checkForModification();
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            checkForModification();

            if (!hasNext()) {
                throw new NoSuchElementException("Следующий элемент не найден");
            }

            currentIndex++;
            lastReturnedIndex = currentIndex;
            return items[currentIndex];
        }

        @Override
        public void remove() {
            checkForModification();

            if (lastReturnedIndex == -1) {
                throw new IllegalStateException("Метод next еще не был вызван, или элемент уже был удален");
            }

            ArrayList.this.remove(lastReturnedIndex);
            currentIndex--;
            lastReturnedIndex = -1;
            expectedModCount++;
        }

        private void checkForModification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("Коллекция была изменена во время обхода");
            }
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        int lastItemIndex = size - 1;

        for (int i = 0; i < lastItemIndex; i++) {
            sb.append(items[i]).append(", ");
        }

        sb.append(items[size - 1]).append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArrayList<?> currentList = (ArrayList<?>) o;

        if (size != currentList.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!Objects.equals(items[i], currentList.items[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        for (int i = 0; i < size; i++) {
            result = prime * result + Objects.hashCode(items[i]);
        }

        return result;
    }
}