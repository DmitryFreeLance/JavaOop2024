package ru.academits.shagaev.range_main;

import ru.academits.shagaev.arraylist.MyArrayList;

public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        System.out.println("Является ли список на массиве пустым? : " + list.isEmpty());

        MyArrayList<Integer> list2 = new MyArrayList<>(25);
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.trimToSize();

        MyArrayList<Integer> list3 = new MyArrayList<>(list2);

        for (int item : list3) {
            System.out.printf("%d ", item);
        }
    }
}
