package ru.academits.shagaev.arraylisthome;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArrayListHome {
    public static void main(String[] args) {
        List<Integer> numbers = readNumbersFromFile("inputArrayListHome");
        System.out.println("Исходный список:");
        System.out.println(numbers);

        removeEvenNumbers(numbers);
        System.out.println("Список после удаления четных чисел:");
        System.out.println(numbers);

        removeDuplicates(numbers);
        System.out.println("Список без повторяющихся элементов:");
        System.out.println(numbers);
    }

    public static List<Integer> readNumbersFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] nums = line.trim().split("\\s+");

                for (String num : nums) {
                    numbers.add(Integer.parseInt(num));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return numbers;
    }

    public static void removeEvenNumbers(List<Integer> numbers) {
        numbers.removeIf(num -> num % 2 == 0);
    }

    public static void removeDuplicates(List<Integer> numbers) {
        List<Integer> uniqueList = new ArrayList<>();

        for (Integer num : numbers) {
            if (!uniqueList.contains(num)) {
                uniqueList.add(num);
            }
        }

        numbers.clear();
        numbers.addAll(uniqueList);
    }
}