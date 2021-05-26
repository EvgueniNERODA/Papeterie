package fr.eni.papeterie.bo;

import java.util.ArrayList;
import java.util.List;

public class Panier {

    //attributs d'instance
    private float montant;
    private List<Ligne> lignesPanier;


    //constructeurs

    public Panier() {
        lignesPanier = new ArrayList<>();
    }

    //getters

    public float getMontant() {
        return montant;
    }


    public List<Ligne> getLignesPanier() {
        return lignesPanier;
    }

    //méthode ajouter ligne au panier
    public void addLigne(Article article, int qte) {
        Ligne ligneAdding = new Ligne(article, qte);
        this.lignesPanier.add(ligneAdding);
    }

    //méthode pour retourner une ligne séléctionnée du panier ou null si pas trouvé, c'est pas un getter
    public Ligne getLigne(int index) {
        return lignesPanier.get(index);
    }

    //méthode mise à jour d'une ligne(modifier la quantité en stock, augmente
    //ou diminue en fonction de cette nouvelle qté)


    public void updateLigne(int index, int newQte) {
        this.getLigne(index).setQte(newQte);
        //lignesPanier.add(newQte, lignesPanier.get(index)); erreur
        //this.lignesPanier.get(index).setQte(newQte); autre méthode
    }

    //méthode suppression ligne
    public void removeLigne(int index) {
        this.lignesPanier.remove(index);
    }

    //affichage


    @Override
    public String toString() {
        return "Panier{" +
                "montant=" + montant +
                ", lignesPanier=" + lignesPanier +
                '}';
    }
}
