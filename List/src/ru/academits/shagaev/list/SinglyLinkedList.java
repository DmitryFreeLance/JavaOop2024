package ru.academits.shagaev.list;

import java.util.NoSuchElementException;

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
        Node<E> node = getNode(index);
        return node.getData();
    }

    public E set(int index, E data) {
        Node<E> node = getNode(index);
        E oldData = node.getData();
        node.setData(data);

        return oldData;
    }

    public E remove(int index) {
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
        head = new Node<>(data, head);
        count++;
    }

    public void addByIndex(int index, E data) {
        checkIndex(index);

        if (index == 0) {
            addFirst(data);
            return;
        }

        Node<E> previousNode = getNode(index - 1);
        previousNode.setNext(new Node<>(data, previousNode.getNext()));
        count++;
    }

    public boolean removeByData(E data) {
        if (head == null) {
            return false;
        }

        if (data == null && head.getData() == null) {
            head = head.getNext();
            count--;
            return true;
        }

        if (data != null && data.equals(head.getData())) {
            head = head.getNext();
            count--;
            return true;
        }

        Node<E> previousNode = head;

        while (previousNode.getNext() != null) {
            if (data == null && previousNode.getNext().getData() == null) {
                previousNode.setNext(previousNode.getNext().getNext());
                count--;
                return true;
            }

            if (data != null && data.equals(previousNode.getNext().getData())) {
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
        SinglyLinkedList<E> newSinglyLinkedList = new SinglyLinkedList<>();

        if (head == null) {
            return newSinglyLinkedList;
        }

        Node<E> currentNode = head;
        Node<E> newTail;
        newSinglyLinkedList.count = this.count;

        Node<E> newHead = new Node<>(currentNode.getData(), null);
        newSinglyLinkedList.head = newHead;
        newTail = newHead;

        currentNode = currentNode.getNext();

        while (currentNode != null) {
            Node<E> newNode = new Node<>(currentNode.getData(), null);
            newTail.setNext(newNode);
            newTail = newNode;
            currentNode = currentNode.getNext();
        }

        return newSinglyLinkedList;
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

        Node<E> node = head;
        sb.append(node.getData());

        while (node.getNext() != null) {
            sb.append(", ");
            node = node.getNext();
            sb.append(node.getData());
        }

        sb.append(']');
        return sb.toString();
    }
}
