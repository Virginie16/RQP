package fr.fs.rqp.service;
import fr.fs.rqp.model.Material;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelReaderService {

    public List<Material> getMaterialsFromExcel(String filePath, String typeMatFilter) {
        List<Material> materials = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Supposons que les données sont sur la première feuille

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Ignorer l'en-tête

                Material material = new Material();
                material.setId((long) row.getCell(0).getNumericCellValue());
                material.setTypeMat(row.getCell(1).getStringCellValue());
                material.setNom(row.getCell(2).getStringCellValue());
                material.setCodeArticle(row.getCell(3).getStringCellValue());

                if (material.getTypeMat().equalsIgnoreCase(typeMatFilter)) {
                    materials.add(material);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return materials;
    }
}

