package ru.academits.shagaev.list_main;

import ru.academits.shagaev.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list1 = new SinglyLinkedList<>();

        list1.addFirst(5);
        list1.addFirst(6);
        list1.addFirst(9);
        list1.addByIndex(2, 4);

        Integer oldData = list1.set(1, 18);
        System.out.println("Значение до изменения = " + oldData);

        int deletedData = list1.remove(1);
        System.out.println("Значение до удаления = " + deletedData);

        System.out.println("Удаление по значению: " + list1.removeByData(4));

        System.out.println("Первый элемент в списке = " + list1.getFirst());

        System.out.println(list1 + ", его длина: " + list1.getCount());

        SinglyLinkedList<Integer> list2 = list1.copy();
        list2.reverse();

        System.out.println(list2 + ", Элемент под индексом 1 = " + list2.get(1));
    }
}