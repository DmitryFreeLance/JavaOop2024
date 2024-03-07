package ru.academits.shagaev.arraylist.arraylist;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private Object[] items;
    private int size;

    public MyArrayList() {
        this.items = new Object[10];
    }

    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Вместимость не должна быть отрицательной: " + capacity);
        }
        this.items = new Object[capacity];
    }

    public MyArrayList(Collection<? extends T> collection) {
        this.items = collection.toArray();
        this.size = items.length;
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
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(items, size, a.getClass());
        }

        System.arraycopy(items, 0, a, 0, size);

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(T t) {
        ensureCapacity(size + 1);
        items[size++] = t;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++) {
                if (items[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (o.equals(items[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        Object[] a = c.toArray();
        int numNew = a.length;

        ensureCapacity(size + numNew);
        System.arraycopy(a, 0, items, size, numNew);
        size += numNew;

        return numNew != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        rangeCheckForAdd(index);
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacity(size + numNew);

        int numMoved = size - index;

        if (numMoved > 0) {
            System.arraycopy(items, index, items, index + numNew, numMoved);
        }

        System.arraycopy(a, 0, items, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;

        for (Object obj : c) {
            if (remove(obj)) {
                modified = true;
            }
        }

        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        for (int i = 0; i < size; i++) {
            if (!c.contains(items[i])) {
                remove(i);
                i--;
                modified = true;
            }
        }

        return modified;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }

        size = 0;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return (T) items[index];
    }

    @Override
    public T set(int index, T element) {
        rangeCheck(index);
        T oldValue = (T) items[index];
        items[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(items, index, items, index + 1, size - index);

        items[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        T oldValue = (T) items[index];
        int numMoved = size - index - 1;

        if (numMoved > 0) {
            System.arraycopy(items, index + 1, items, index, numMoved);
        }

        items[--size] = null;
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (items[i] == null)
                    return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(items[i]))
                    return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (items[i] == null)
                    return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(items[i]))
                    return i;
            }
        }

        return -1;
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
    }

    private String outOfBoundsMessage(int index) {
        return "Индекс: " + index + ", Размер: " + size;
    }

    private void ensureCapacity(int minCapacity) {
        int oldCapacity = items.length;

        if (minCapacity > oldCapacity) {
            int newCapacity = (oldCapacity * 3) / 2 + 1;

            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }

            items = Arrays.copyOf(items, newCapacity);
        }
    }

    public void trimToSize() {
        if (size < items.length) {
            items = Arrays.copyOf(items, size);
        }
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;

        if (numMoved > 0) {
            System.arraycopy(items, index + 1, items, index, numMoved);
        }

        items[--size] = null;
    }

    private class MyIterator implements Iterator<T> {
        int cursor;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            int i = cursor;

            if (i >= size) {
                throw new NoSuchElementException();
            }

            Object[] elementData = MyArrayList.this.items;
            cursor = i + 1;

            return (T) elementData[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new IndexOutOfBoundsException("Выход за пределы");
            }
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}