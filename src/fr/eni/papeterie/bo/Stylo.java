package fr.eni.papeterie.bo;

public class Stylo extends Article{
    private String couleur;

    //constructeurs

    public Stylo() {

    }


    public Stylo(int idArticle, String marque, String reference, String designation, float prixUnitaire, int qteStock, String couleur) {
        super(idArticle, marque, reference, designation, prixUnitaire, qteStock);
        this.couleur = couleur;
    }

    public Stylo(String marque, String reference, String designation, float prixUnitaire, int qteStock, String couleur) {
        super(marque, reference, designation, prixUnitaire, qteStock);
        this.couleur = couleur;
    }

    //getter

    public String getCouleur() {
        return couleur;
    }

    //setter

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    //affichage


    @Override
    public String toString() {
        return "Stylo{" +
                "idArticle=" + idArticle +
                ", marque='" + marque + '\'' +
                ", reference='" + reference + '\'' +
                ", designation='" + designation + '\'' +
                ", prixUnitaire=" + prixUnitaire +
                ", qteStock=" + qteStock +
                ", couleur='" + couleur + '\'' +
                '}';
    }
}
