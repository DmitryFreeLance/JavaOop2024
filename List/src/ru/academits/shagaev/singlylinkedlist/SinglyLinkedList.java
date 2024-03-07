package ru.academits.shagaev.singlylinkedlist;

import ru.academits.shagaev.node.Node;

public class SinglyLinkedList {
    private Node head;
    private int count;

    public SinglyLinkedList() {
        this.head = null;
        this.count = 0;
    }

    public int getListLength() {
        return count;
    }

    public int getFirstElementData() {
        if (head == null) {
            throw new IllegalStateException("Список пуст!");
        }

        return head.data;
    }

    public int getDataByIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Элемента с таким индексом не существует!");
        }

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    public int changeDataByIndex(int index, int data) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Элемента с таким индексом не существует!");
        }

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        int oldData = current.data;
        current.data = data;

        return oldData;
    }

    public int deleteElementByIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Элемента с таким индексом не существует!");
        }

        if (index == 0) {
            return removeFirstElement();
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        int removedData = current.next.data;
        current.next = current.next.next;
        count--;

        return removedData;
    }

    public void insertionToBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        count++;
    }

    public void insertionElementByIndex(int index, int data) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Элемента с таким индексом не существует!");
        }

        if (index == 0) {
            insertionToBeginning(data);
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        Node newNode = new Node(data);
        newNode.next = current.next;
        current.next = newNode;
        count++;
    }

    public boolean deleteByData(int data) {
        if (head != null && head.data == data) {
            removeFirstElement();
            return true;
        }

        Node current = head;
        boolean isDeleted = false;

        for (int i = 0; i < count; i++) {
            assert current != null;

            if (current.next.data == data) {
                current.next = current.next.next;
                count--;
                isDeleted = true;
            }
        }

        return isDeleted;
    }

    public int removeFirstElement() {
        if (head == null) {
            throw new IllegalStateException("Лист пуст!");
        }

        int oldHeadData = head.data;
        head = head.next;
        count--;

        return oldHeadData;
    }

    public void reverse() {
        Node previous = null;
        Node current = head;
        Node next;

        while (current != null) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }

        head = previous;
    }

    public SinglyLinkedList copy() {
        SinglyLinkedList newSinglyLinkedList = new SinglyLinkedList();
        Node current = head;

        while (current != null) {
            newSinglyLinkedList.insertionToBeginning(current.data);
            current = current.next;
        }

        return newSinglyLinkedList;
    }
}