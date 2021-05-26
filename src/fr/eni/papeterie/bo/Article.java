package fr.eni.papeterie.bo;

public abstract class Article {
    public int idArticle;
    public String marque;
    public String reference;
    public String designation;
    public float prixUnitaire;
    public int qteStock;

    //constructeurs


    public Article() {

    }

    public Article(int idArticle, String marque, String reference, String designation, float prixUnitaire, int qteStock) {
        this.idArticle = idArticle;
        this.marque = marque;
        this.reference = reference;
        this.designation = designation;
        this.prixUnitaire = prixUnitaire;
        this.qteStock = qteStock;
    }

    public Article(String marque, String reference, String designation, float prixUnitaire, int qteStock) {
        this.marque = marque;
        this.reference = reference;
        this.designation = designation;
        this.prixUnitaire = prixUnitaire;
        this.qteStock = qteStock;
    }

    //getters


    public int getIdArticle() {
        return idArticle;
    }

    public String getMarque() {
        return marque;
    }

    public String getReference() {
        return reference;
    }

    public String getDesignation() {
        return designation;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public int getQteStock() {
        return qteStock;
    }

    //setters

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    //affichage

    @Override
    public String toString() {
        return "Article{" +
                "idArticle=" + idArticle +
                ", marque='" + marque + '\'' +
                ", reference='" + reference + '\'' +
                ", designation='" + designation + '\'' +
                ", prixUnitaire=" + prixUnitaire +
                ", qteStock=" + qteStock +
                '}';
    }
}
