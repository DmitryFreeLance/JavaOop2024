package ru.academits.shagaev.csv;

import java.io.*;

public class CSV {
    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("inputCSV"));
             FileWriter writer = new FileWriter("outputCSV")) {

            writer.write("<table>\n");

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
                    writer.write("<tr>\n<td>");
                }

                for (int i = 0; i < lineLength; i++) {
                    char currentCharacter = line.charAt(i);

                    if (currentCharacter == quote) {
                        ++quotesCount;

                        if (quotesCount % 3 == 0) {
                            writer.write(quote);
                        }

                        continue;
                    }

                    if (currentCharacter == less) {
                        writer.write("&lt;");
                        continue;
                    }

                    if (currentCharacter == more) {
                        writer.write("&gt;");
                        continue;
                    }

                    if (currentCharacter == amp) {
                        writer.write("&amp;");
                        continue;
                    }

                    if (currentCharacter == cellSeparator && quotesCount % 2 == 0) {
                        writer.write("</td>\n<td>");

                        quotesCount = 0;

                        continue;
                    }

                    writer.write(currentCharacter);
                }

                if (quotesCount % 2 == 0) {
                    writer.write("</td>\n</tr>\n");

                    quotesCount = 0;

                    continue;
                }

                writer.write("<br/>");
            }

            writer.write("</table></body></html>");
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
    }
}
