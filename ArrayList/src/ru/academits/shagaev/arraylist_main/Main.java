package ru.academits.shagaev.arraylist_main;

import ru.academits.shagaev.arraylist.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        System.out.println("Является ли список на массиве пустым? : " + list.isEmpty());

        ArrayList<Integer> list2 = new ArrayList<>(25);
        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.trimToSize();

        ArrayList<Integer> list3 = new ArrayList<>(list2);

        System.out.println(list3);
    }
}
