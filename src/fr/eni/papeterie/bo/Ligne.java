package fr.eni.papeterie.bo;

public class Ligne {
    protected int qte;
    protected Article article;


    //constructeur

    public Ligne(Article article, int qte) {
        this.qte = qte;
        this.article = article;
    }

    //getters

    public Article getArticle() {
        return article;
    }
    public int getQte() {
        return qte;
    }

    //va chercher le prix unitaire d'un article
    public float getPrix (){
        return article.getPrixUnitaire();
    }

    //setters


    private void setArticle(Article article) {
        this.article = article;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    //affichage

    @Override
    public String toString() {
        return "Ligne{" +
                "qte=" + qte +
                ", article=" + article +
                '}';
    }
}
