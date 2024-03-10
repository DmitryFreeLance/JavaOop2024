package ru.academits.shagaev.list_main;

import ru.academits.shagaev.list.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> List1 = new SinglyLinkedList<>();

        List1.addFirst(5);
        List1.addFirst(6);
        List1.addFirst(9);
        List1.addByIndex(2, 4);

        Integer oldData = List1.set(1, 18);
        System.out.println("Значение до изменения = " + oldData);

        int deletedData = List1.remove(1);
        System.out.println("Значение до удаления = " + deletedData);

        System.out.println("Удаление по значению: " + List1.removeByData(4));

        System.out.println("Первый элемент в списке = " + List1.getFirst());

        System.out.println(List1 + ", его длина: " + List1.getCount());

        SinglyLinkedList<Integer> List2 = List1.copy();
        List2.reverse();

        System.out.println(List2 + ", Элемент под индексом 1 = " + List2.get(1));
    }
}