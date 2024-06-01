package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public interface FileUtils {

    public static String getFileName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome do arquivo contendo os dados:");
        String fileName = scanner.nextLine();
        return fileName;
    }
    public static void saveToFile(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
