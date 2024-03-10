package ru.academits.shagaev.list;

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> {
    private Node<T> head;
    private int count;

    public SinglyLinkedList() {
    }

    public int getCount() {
        return count;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Индекс (" + index + ") вне диапазона [0, " + (count - 1) + "]");
        }
    }

    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст!");
        }

        return head.getData();
    }

    public T get(int index) {
        checkIndex(index);

        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
    }

    public T set(int index, T data) {
        checkIndex(index);

        Node<T> node = head;

        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }

        T oldData = node.getData();
        node.setData(data);

        return oldData;
    }

    public T remove(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        Node<T> previousNode = head;

        for (int i = 0; i < index - 1; i++) {
            previousNode = previousNode.getNext();
        }

        T deletedData = previousNode.getNext().getData();
        previousNode.setNext(previousNode.getNext().getNext());
        count--;

        return deletedData;
    }

    public void addFirst(T data) {
        head = new Node<>(data, head);
        count++;
    }

    public void addByIndex(int index, T data) {
        checkIndex(index);

        if (index == 0) {
            addFirst(data);
            return;
        }

        Node<T> previousNode = getNodeByIndex(index - 1);
        previousNode.setNext(new Node<>(data, previousNode.getNext()));
        count++;
    }

    public boolean removeByData(T data) {
        if (head != null && head.getData().equals(data)) {
            removeFirst();
            return true;
        }

        Node<T> previousNode = head;

        while (previousNode != null && previousNode.getNext() != null) {
            if (previousNode.getNext().getData().equals(data)) {
                previousNode.setNext(previousNode.getNext().getNext());
                count--;
                return true;
            }

            previousNode = previousNode.getNext();
        }

        return false;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст!");
        }

        T deletedData = head.getData();
        head = head.getNext();
        count--;

        return deletedData;
    }

    public void reverse() {
        Node<T> previousNode = null;
        Node<T> currentNode = head;

        while (currentNode != null) {
            Node<T> nextNode = currentNode.getNext();
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        head = previousNode;
    }

    public SinglyLinkedList<T> copy() {
        SinglyLinkedList<T> newSinglyLinkedList = new SinglyLinkedList<>();
        Node<T> currentNode = head;
        Node<T> tail = null;

        while (currentNode != null) {
            Node<T> newNode = new Node<>(currentNode.getData());
            if (tail == null) {
                newSinglyLinkedList.head = newNode;
            } else {
                tail.setNext(newNode);
            }
            tail = newNode;
            newSinglyLinkedList.count++;
            currentNode = currentNode.getNext();
        }

        return newSinglyLinkedList;
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Индекс (" + index + ") вне диапазона [0, " + (count - 1) + "]");
        }

        Node<T> currentNode = head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }

        return currentNode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node<T> currentNode = head;

        while (currentNode != null) {
            sb.append(currentNode.getData());
            if (currentNode.getNext() != null) {
                sb.append(", ");
            }
            currentNode = currentNode.getNext();
        }

        sb.append(']');

        return sb.toString();
    }
}
