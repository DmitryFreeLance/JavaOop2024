package ru.academits.shagaev;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class ArrayListHome {
    public static void main(String[] args) {
        ArrayList<Integer> myArrayList1 = new ArrayList<>();

        //1
        try {
            Scanner scanner = new Scanner(new FileInputStream("input"));
            while (scanner.hasNextInt()) {
                myArrayList1.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        for (int e : myArrayList1) {
            System.out.printf("%d ", e);
        }

        System.out.println();

        //2
        myArrayList1.removeIf(text -> text % 2 == 0);

        for (int e : myArrayList1) {
            System.out.printf("%d ", e);
        }

        System.out.println();

        //3
        List<Integer> myArrayList2 = new ArrayList<>(new LinkedHashSet<>(myArrayList1));

        for (int e : myArrayList2) {
            System.out.printf("%d ", e);
        }
    }
}

