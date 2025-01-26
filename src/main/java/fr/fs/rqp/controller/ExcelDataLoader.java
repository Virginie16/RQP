package fr.fs.rqp.controller;

import fr.fs.rqp.model.Material;
import fr.fs.rqp.service.MaterialService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

@Component
public class ExcelDataLoader implements CommandLineRunner {
    @Autowired
    private MaterialService materialService;

    @Override
    public void run(String... args) throws Exception {
        loadExcelData("RQP-excel.xlsx");
    }

    private void loadExcelData(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Erreur : Le fichier spécifié est introuvable (" + filePath + ")");
            return;
        }
        try (FileInputStream excelFile = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(excelFile)) {

            XSSFSheet sheet = workbook.getSheetAt(0);

            sheet.forEach(row -> {
                Material material = new Material();
                material.setTypeMat(getCellValueAsString(row.getCell(1)));
                material.setNom(getCellValueAsString(row.getCell(2)));
                material.setCodeArticle(getCellValueAsString(row.getCell(3)));
                material.setNoLot(getCellValueAsString(row.getCell(4)));
                material.setNoLotFournisseur(getCellValueAsString(row.getCell(5)));
                material.setFournisseur(getCellValueAsString(row.getCell(6)));
                material.setFabricant(getCellValueAsString(row.getCell(7)));
                material.setAcceptationLot(getCellValueAsDate(row.getCell(8)));
                material.setStatut(getCellValueAsString(row.getCell(10)));
                material.setDeviations(getCellValueAsString(row.getCell(11)));
                materialService.saveMaterial(material);
            });

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier Excel : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return null;
    }

    private LocalDate getCellValueAsDate(Cell cell) {
        if (cell == null) {
            System.err.println("Cellule vide.");
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue().toLocalDate();
            } else {
                System.err.println("La cellule contient un nombre mais n'est pas formatée comme une date valide.");
            }
        } else if (cell.getCellType() == CellType.STRING) {
            System.err.println("La cellule contient du texte au lieu d'une date : " + cell.getStringCellValue());
        } else {
            System.err.println("Type de cellule inattendu : " + cell.getCellType());
        }
        return null;
    }

}

