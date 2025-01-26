package fr.fs.rqp.controller;

import fr.fs.rqp.model.Material;
import fr.fs.rqp.service.ExcelReaderService;
import fr.fs.rqp.service.WordGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/word")
public class WordGeneratorController {

    @Autowired
    private ExcelReaderService excelReaderService;

    @Autowired
    private WordGeneratorService wordGeneratorService;

    @GetMapping("/generate")
    public String generateWord(@RequestParam String excelPath,
                               @RequestParam String wordPath,
                               @RequestParam String typeMat,
                               @RequestParam String additionalText) {

        List<Material> materials = excelReaderService.getMaterialsFromExcel(excelPath, typeMat);
        if (materials.isEmpty()) {
            return "Aucun matériau trouvé pour le type : " + typeMat;
        }

        wordGeneratorService.generateWordFile(wordPath, materials, additionalText);
        return "Fichier Word généré avec succès : " + wordPath;
    }
}
