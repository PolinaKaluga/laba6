package workWithFile;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

    /**
     * Метод, производящий чтение данных из указанного файла. В случае критических ошибок программа завершает свою работу.
     *
     * @param filePath файл, из которого следует читать данные
     * @return строка, которая хранит все содержимое данного файла
     */
    public String readFromFile(String filePath) {
        try {
            FileReader reader = new FileReader(filePath);

            Scanner scanner = new Scanner(reader);
            StringBuilder content = new StringBuilder();

            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }

            reader.close();
            scanner.close();

            return content.toString();

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, производящий запись данных в указанный файл.
     *
     * @param filePath файл, куда следует записывать данные
     * @param values   строка, которую следует записать в файл
     */
    public void writeToFile(String values, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(values);
            writer.close();
            System.out.println("Содержимое успешно записано в файл.");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл.");
            e.printStackTrace();
        }
    }

}