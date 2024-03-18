package ru.academits.shagaev.arraylisthome;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArrayListHome {
    public static void main(String[] args) {
        List<String> linesFromFile = readLinesFromFile();
        printList(linesFromFile);

        List<String> uniqueLines = removeDuplicates(linesFromFile);
        printList(uniqueLines);
    }

    private static List<String> readLinesFromFile() {
        List<String> linesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("input"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                linesList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return linesList;
    }

    private static List<String> removeDuplicates(List<String> linesList) {
        List<String> uniqueList = new ArrayList<>();

        for (String line : linesList) {
            if (!uniqueList.contains(line)) {
                uniqueList.add(line);
            }
        }

        return uniqueList;
    }

    private static void printList(List<String> list) {
        System.out.println("Результат распечатки: ");

        for (String item : list) {
            System.out.println(item);
        }
    }
}