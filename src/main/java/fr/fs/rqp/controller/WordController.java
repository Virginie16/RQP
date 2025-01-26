package fr.fs.rqp.controller;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/word")
public class WordController {

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload-form"; // Une page HTML qui permet à l'utilisateur de charger un fichier et d'entrer le typeMat
    }

    @PostMapping("/process")
    public String processWordFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("typeMat") String typeMat,
            RedirectAttributes redirectAttributes) {
        if (file.isEmpty() || typeMat == null || typeMat.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Veuillez fournir un fichier et un critère valide !");
            return "redirect:/word/upload";
        }

        try {
            // Charger le document Word
            InputStream inputStream = file.getInputStream();
            XWPFDocument document = new XWPFDocument(inputStream);

            // Créer un nouveau fichier Word pour les résultats
            XWPFDocument filteredDocument = new XWPFDocument();

            // Parcourir les paragraphes du document
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                if (paragraph.getText().contains(typeMat)) {
                    // Ajouter ce paragraphe au nouveau document Word
                    XWPFParagraph newParagraph = filteredDocument.createParagraph();
                    XWPFRun run = newParagraph.createRun();
                    run.setText(paragraph.getText());
                }
            }

            // Sauvegarder le document filtré
            String outputPath = "filtered-word-file.docx";
            try (FileOutputStream out = new FileOutputStream(outputPath)) {
                filteredDocument.write(out);
            }
            redirectAttributes.addFlashAttribute("message", "Le fichier a été traité avec succès !");
            redirectAttributes.addFlashAttribute("outputPath", outputPath);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors du traitement du fichier : " + e.getMessage());
        }

        return "redirect:/word/upload";
    }
}