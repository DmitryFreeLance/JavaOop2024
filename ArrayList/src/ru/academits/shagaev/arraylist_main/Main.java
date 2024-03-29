package ru.academits.shagaev.arraylist_main;

import ru.academits.shagaev.arraylist.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);

        ArrayList<Integer> list2 = new ArrayList<>(25);
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.trimToSize();

        ArrayList<Integer> list3 = new ArrayList<>(list2);

        System.out.println(list1);
        System.out.println(list3);
    }
}
