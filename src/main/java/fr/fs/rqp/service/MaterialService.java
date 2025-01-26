package fr.fs.rqp.service;

import fr.fs.rqp.model.Material;
import fr.fs.rqp.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {
//    @Autowired
//    private MaterialRepository materialRepository;
//
//    public List<Material> getAllMaterials() {
//        return materialRepository.findAll();
//    }
//
//    public Material saveMaterial(Material material) {
//        return materialRepository.save(material);
//    }
private List<Material> materials = new ArrayList<>();

    public List<Material> getAllMaterials() {
        return materials;
    }

    public Material saveMaterial(Material material) {
        materials.add(material);
        return material;
    }
    // Autres méthodes de service
}
