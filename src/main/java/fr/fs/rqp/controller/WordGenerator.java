package fr.fs.rqp.controller;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class WordGenerator {
    public static void main(String[] args) {
        String excelFilePath = "C:\\Temp\\RQP-excel.xlsx"; // Chemin correct du fichier Excel
        String wordFilePath = "C:\\Temp\\essaiRqp.docx"; // Chemin correct du fichier Word

        try {
            // Ouvrir le fichier Excel
            FileInputStream excelFile = new FileInputStream(excelFilePath);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet == null) {
                System.out.println("La feuille Excel est vide ou inaccessible.");
                return;
            }

            // Demander à l'utilisateur de choisir un critère pour filtrer
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez le Type_Mat à sélectionner : ");
            String selectedTypeMat = scanner.nextLine().trim();
            scanner.close();

            // Trouver l'index de la colonne "typeMat"
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                System.out.println("La première ligne (entête) est absente.");
                return;
            }

            int typeMatColumnIndex = -1;
            for (Cell cell : headerRow) {
                if (cellToString(cell).equalsIgnoreCase("Type_Mat")) {
                    typeMatColumnIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (typeMatColumnIndex == -1) {
                System.out.println("La colonne 'Type_Mat' est introuvable dans le fichier Excel.");
                return;
            }

            // Créer le document Word
            XWPFDocument document = new XWPFDocument();

            boolean hasData = false;

            // Parcourir les lignes et filtrer selon "typeMat"
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Commence à 1 pour ignorer l'entête
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell typeMatCell = row.getCell(typeMatColumnIndex);
                if (typeMatCell == null) continue;

                String typeMatValue = cellToString(typeMatCell);

                // Vérifier si la ligne correspond au typeMat sélectionné
                if (typeMatValue.equalsIgnoreCase(selectedTypeMat)) {
                    hasData = true;
                    Map<String, String> rowData = new LinkedHashMap<>(); // Utilisation d'un LinkedHashMap pour conserver l'ordre

                    for (Cell cell : row) {
                        Cell headerCell = headerRow.getCell(cell.getColumnIndex());
                        String header = (headerCell != null) ? cellToString(headerCell) : "Colonne_" + cell.getColumnIndex();
                        String value = (cell != null) ? cellToString(cell) : "";
                        rowData.put(header, value);
                    }

                    // Ajouter les données filtrées dans un tableau Word
                    XWPFTable table = document.createTable();
                    table.setWidth("80%");

                    // Ajouter une ligne au tableau avec les données
                    XWPFTableRow tableRow = table.createRow();
                    for (Map.Entry<String, String> entry : rowData.entrySet()) {
                        XWPFTableCell cell = tableRow.addNewTableCell();
                        cell.setText(entry.getKey() + ": " + entry.getValue());
                    }
                }
            }

            // Sauvegarder le document Word seulement si des données ont été ajoutées
            if (hasData) {
                FileOutputStream wordFile = new FileOutputStream(wordFilePath);
                document.write(wordFile);
                wordFile.close();
                System.out.println("Le fichier Word a été généré avec succès !");
            } else {
                System.out.println("Aucune donnée ne correspond au typeMat sélectionné.");
            }

            // Fermer les fichiers
            workbook.close();
            excelFile.close();

        } catch (IOException e) {
            System.out.println("Une erreur est survenue !");
            e.printStackTrace();
        }
    }

    // Méthode utilitaire pour convertir une cellule en String
    private static String cellToString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
