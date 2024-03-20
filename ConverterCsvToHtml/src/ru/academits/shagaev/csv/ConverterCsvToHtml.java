package ru.academits.shagaev.csv;

import java.io.*;

public class ConverterCsvToHtml {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Ошибка: неправильное количество аргументов.");
            System.out.println("Использование: java ConverterCsvToHtml <входной файл CSV> <выходной файл HTML>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));
             PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)))) {
            writer.println("<!DOCTYPE html>");
            writer.println(" <html>");
            writer.println(" <head>");
            writer.println(" <meta charset=\"UTF-8\">");
            writer.println(" </head>");
            writer.println(" <body>");
            writer.println(" <table>");

            char cellSeparator = ',';
            char quote = '"';
            char less = '<';
            char more = '>';
            char amp = '&';

            int quotesCount = 0;

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                int lineLength = line.length();

                if (lineLength == 0) {
                    continue;
                }

                if (quotesCount % 2 == 0) {
                    writer.print(" <tr> <td>");
                }

                for (int i = 0; i < lineLength; i++) {
                    char currentCharacter = line.charAt(i);

                    if (currentCharacter == quote) {
                        ++quotesCount;

                        if (quotesCount % 2 == 0) {
                            writer.print(quote);
                        }

                        continue;
                    }

                    if (currentCharacter == less) {
                        writer.print("&lt;");
                        continue;
                    }

                    if (currentCharacter == more) {
                        writer.print("&gt;");
                        continue;
                    }

                    if (currentCharacter == amp) {
                        writer.print("&amp;");
                        continue;
                    }

                    if (currentCharacter == cellSeparator && quotesCount % 2 == 0) {
                        writer.print(" </td> <td>");

                        quotesCount = 0;

                        continue;
                    }

                    writer.print(currentCharacter);
                }

                if (quotesCount % 2 == 0) {
                    writer.println(" </td> </tr>");

                    quotesCount = 0;

                    continue;
                }

                writer.println(" <br />");
            }

            writer.println(" </table>");
            writer.println(" </body>");
            writer.println(" </html>");
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
    }
}