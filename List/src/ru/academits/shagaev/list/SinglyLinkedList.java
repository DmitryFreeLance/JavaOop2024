package ru.academits.shagaev.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class SinglyLinkedList<E> {
    private Node<E> head;
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

    public E getFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст!");
        }

        return head.getData();
    }

    public E get(int index) {
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException("Узла с таким индексом не существует");
        }

        return getNode(index).getData();
    }

    public E set(int index, E data) {
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException("Узла с таким индексом не существует");
        }

        Node<E> node = getNode(index);

        if (node == null) {
            throw new NullPointerException("Узел с индексом " + index + " равен null");
        }

        E oldData = node.getData();
        node.setData(data);

        return oldData;
    }

    public E removeByIndex(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        Node<E> previousNode = getNode(index - 1);
        Node<E> removedNode = previousNode.getNext();
        previousNode.setNext(removedNode.getNext());
        count--;

        return removedNode.getData();
    }

    public void addFirst(E data) {
        head = new Node<>(data);
        count++;
    }

    public void addByIndex(int index, E data) {
        checkIndex(index);

        if (index == 0) {
            addFirst(data);
            return;
        }

        if (index == count) {
            addLast(data);
            return;
        }

        Node<E> currentNode = head;

        for (int i = 0; i < index - 1; i++) {
            if (currentNode == null) {
                throw new IndexOutOfBoundsException("Индекс (" + index + ") вне диапазона [0, " + count + "]");
            }
            currentNode = currentNode.getNext();
        }

        currentNode.setNext(new Node<>(data));
        count++;
    }

    public void addLast(E data) {
        if (head == null) {
            addFirst(data);
            return;
        }

        Node<E> currentNode = head;

        while (currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
        }

        currentNode.setNext(new Node<>(data));
        count++;
    }

    public boolean removeByData(E data) {
        if (head == null) {
            return false;
        }

        if (Objects.equals(head.getData(), data)) {
            head = head.getNext();
            count--;
            return true;
        }

        Node<E> previousNode = head;

        while (previousNode.getNext() != null) {
            if (Objects.equals(previousNode.getNext().getData(), data)) {
                previousNode.setNext(previousNode.getNext().getNext());
                count--;
                return true;
            }

            previousNode = previousNode.getNext();
        }

        return false;
    }

    public E removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Список пуст!");
        }

        E removedData = head.getData();
        head = head.getNext();
        count--;

        return removedData;
    }

    public void reverse() {
        Node<E> previousNode = null;
        Node<E> currentNode = head;

        while (currentNode != null) {
            Node<E> nextNode = currentNode.getNext();
            currentNode.setNext(previousNode);
            previousNode = currentNode;
            currentNode = nextNode;
        }

        head = previousNode;
    }

    public SinglyLinkedList<E> copy() {
        SinglyLinkedList<E> copiedList = new SinglyLinkedList<>();

        if (head == null) {
            return copiedList;
        }

        Node<E> currentNode = head;
        Node<E> copiedTail = new Node<>(currentNode.getData());
        copiedList.head = copiedTail;

        while (currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
            copiedTail.setNext(new Node<>(currentNode.getData()));
            copiedTail = copiedTail.getNext();
        }

        copiedList.count = this.count;
        return copiedList;
    }

    private Node<E> getNode(int index) {
        Node<E> node = head;

        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }

        return node;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(head.getData());

        Node<E> node = head.getNext();

        while (node != null) {
            sb.append(", ");
            sb.append(node.getData());
            node = node.getNext();
        }

        sb.append(']');
        return sb.toString();
    }
}