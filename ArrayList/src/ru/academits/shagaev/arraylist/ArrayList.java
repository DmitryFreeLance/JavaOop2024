package ru.academits.shagaev.arraylist;

import java.util.*;

@SuppressWarnings("unchecked")
public class ArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] items;
    private int size;

    public ArrayList() {
        items = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость не должна быть отрицательной: " + capacity);
        }

        items = (E[]) new Object[capacity];
    }

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
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

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
        if (size == items.length) {
            increaseCapacity();
        }

        items[size++] = item;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(items[i], null)) {
                    remove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(o, items[i])) {
                    remove(i);
                    return true;
                }
            }
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
    public boolean addAll(Collection<? extends E> itemsToAdd) {
        return addAll(size, itemsToAdd);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> itemsToAdd) {
        if (itemsToAdd.isEmpty()) {
            return false;
        }

        checkIndexForAdd(index);

        ensureCapacity(size + itemsToAdd.size());

        int i = index;

        for (E element : itemsToAdd) {
            add(i++, element);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean wasModified = false;

        for (int i = 0; i < size; i++) {
            if (collection.contains(items[i])) {
                remove(i);
                i--;
                wasModified = true;
            }
        }

        return wasModified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Коллекция не должна быть null");
        }

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
        Arrays.fill(items, 0, size, null);
        size = 0;
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

        E deletedItem = items[index];
        int movedElementsCount = size - index - 1;

        if (movedElementsCount > 0) {
            System.arraycopy(items, index + 1, items, index, movedElementsCount);
        }

        items[size - 1] = null;
        return deletedItem;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(items[i], null)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(o, items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (Objects.equals(items[i], null)) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (Objects.equals(o, items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " выходит за пределы допустимого диапазона [0, " + size + ")");
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
        int newCapacity = items.length * 2;
        items = Arrays.copyOf(items, newCapacity);
    }

    public void trimToSize() {
        if (size < items.length) {
            items = Arrays.copyOf(items, size);
        }
    }

    private class MyIterator implements Iterator<E> {
        private int currentIndex = -1;
        private int lastRetIndex = -1;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Следующий элемент не найден");
            }

            ++currentIndex;
            lastRetIndex = currentIndex;
            return items[currentIndex];
        }

        @Override
        public void remove() {
            if (lastRetIndex == -1) {
                throw new IllegalStateException("Метод next еще не был вызван, или элемент уже был удален");
            }

            ArrayList.this.remove(lastRetIndex);
            currentIndex--;
            lastRetIndex = -1;
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

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

        for (int i = 0; i < size - 1; i++) {
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

        ArrayList<?> that = (ArrayList<?>) o;

        if (size != that.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!Objects.equals(items[i], that.items[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(items);
        return result;
    }
}