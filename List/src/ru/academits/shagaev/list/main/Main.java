package ru.academits.shagaev.list.main;

import ru.academits.shagaev.list.singlyLinkedList.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList myList = new SinglyLinkedList();

        myList.insertionToBeginning(5);
        myList.insertionToBeginning(6);
        myList.insertionToBeginning(9);
        myList.insertionElementByIndex(1, 4);
        myList.insertionElementByIndex(2, 5);
        myList.insertionElementByIndex(3, 4);

        int changedValue = myList.changeDataByIndex(3, 18);
        System.out.println("Значение до изменения = " + changedValue);

        int deletedValue = myList.deleteElementByIndex(4);
        System.out.println("Значение до удаления = " + deletedValue);

        System.out.println("Удаление по значению: " + myList.deleteByData(4));

        System.out.println("Первый элемент в списке = " + myList.getFirstElementData());

        for (int i = 0; i < myList.getListLength(); i++) {
            System.out.printf("%d | ", myList.getDataByIndex(i));
        }

        System.out.println();

        SinglyLinkedList myList2 = myList.copy();
        myList2.reverse();

        for (int i = 0; i < myList2.getListLength(); i++) {
            System.out.printf("%d | ", myList2.getDataByIndex(i));
        }
    }
}
