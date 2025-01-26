package fr.fs.rqp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeMat;
    private String nom;
    private String codeArticle;
    private String noLot;
    private String noLotFournisseur;
    private String fournisseur;
    private String fabricant;
    private LocalDate acceptationLot;
    private String colonne1;
    private String statut;
    private String deviations;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTypeMat() {
        return typeMat;
    }
    public String getNom() {
        return nom;
    }
    public String getCodeArticle() {
        return codeArticle;
    }
    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }
    public String getNoLot() {
        return noLot;
    }
    public void setNoLot(String noLot) {
        this.noLot = noLot;
    }
    public String getNoLotFournisseur() {
        return noLotFournisseur;
    }
    public void setNoLotFournisseur(String noLotFournisseur) {
        this.noLotFournisseur = noLotFournisseur;
    }
    public String getFournisseur() {
        return fournisseur;
    }
    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }
    public String getFabricant() {
        return fabricant;
    }
    public void setFabricant(String fabricant) {
        this.fabricant = fabricant;
    }
    public LocalDate getAcceptationLot() {
        return acceptationLot;
    }
    public void setAcceptationLot(LocalDate acceptationLot) {
        this.acceptationLot = acceptationLot;
    }
    public String getColonne1() {
        return colonne1;
    }
    public void setColonne1(String colonne1) {
        this.colonne1 = colonne1;
    }
    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }
    public String getDeviations() {
        return deviations;
    }
    public void setDeviations(String deviations) {
        this.deviations = deviations;
    }

    public void setTypeMat(String stringCellValue) {

    }
    public void setNom(String stringCellValue) {
    }
}
