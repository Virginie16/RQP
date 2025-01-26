package fr.fs.rqp.service;

import fr.fs.rqp.model.Material;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class WordGeneratorService {

    public void generateWordFile(String filePath, List<Material> materials, String additionalText) {
        try (XWPFDocument document = new XWPFDocument()) {
            // Ajouter un titre
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Rapport des Matériaux");
            titleRun.setBold(true);
            titleRun.setFontSize(14);

            // Ajouter un texte supplémentaire
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(additionalText);
            run.addBreak();

            // Créer un tableau
            XWPFTable table = document.createTable();
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("ID");
            headerRow.addNewTableCell().setText("Nom");
            headerRow.addNewTableCell().setText("Code Article");

            for (Material material : materials) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(material.getId()));
                row.getCell(1).setText(material.getNom());
                row.getCell(2).setText(material.getCodeArticle());
            }

            // Sauvegarde du document
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

