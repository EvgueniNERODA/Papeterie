package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDaoJdbcImpl {
    private final String URL = "jdbc:sqlite:papeterie_db.sqlite";

    public void selectAll() {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement unAutreEtat = connection.createStatement();
            String sql = "SELECT * FROM Article;";
            //on stocke le resultat dans une variable
            ResultSet rs = unAutreEtat.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * méthode séléctionnant un article par son identifiant
     * paramètre int id
     */

    public List <Article> selectByld(int id) {
        List <Article> articleList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(URL);
            Statement unAutreEtat = connection.createStatement();


            String sql = "SELECT * FROM Article WHERE idArticle = "+id+" ;";
            //on stocke dans une variable
            ResultSet rs = unAutreEtat.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return articleList;

    }


    /**
     * méthode prenant en paramètre un article et mettant à jour les quantitiés
     * @param article
     */
    public void update (Article article){
        try {
            Connection cnx = DriverManager.getConnection(this.URL);
            Statement unAutreEtat = cnx.createStatement();

            String type = "";
            //on vérifie quelle est l'instance de l'article
            //si l'article est un stylo on utilise la couleur qui se trouve dans la clase Stylo
            if (article instanceof Stylo){
                type = "couleur = ' " + ((Stylo) article).getCouleur() + "'";
            }
            //si l'article est une ramette on utilise le grammage qui se trouve dans la clase Ramette
            if (article instanceof Ramette){
                type = "grammage = " + ((Ramette) article).getGrammage() + " ";
            }
            String sql = "UPDATE Article SET " +
                    "reference ='"+article.getReference() +"' "+
                    "marque ='"+article.getMarque() +"' "+
                    "designatoion ='"+article.getDesignation() +"' "+
                    "qteStock ="+article.getQteStock() +
                    "prixUnitaire ="+article.getPrixUnitaire() + ""+
                    type+
                    " WHERE idarticle =" + article.getIdArticle() +";";

            //pas de ResultSet par ce qu'on ne retourne rien

            unAutreEtat.executeQuery(sql);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }

    /**
     * méthode d'ajout d'un article dans la base de données
     *
     */

    public void insert (Article article){
        try {
            Connection cnx = DriverManager.getConnection(this.URL);
            Statement etat = cnx.createStatement();

            String type = "";
            if (article instanceof Stylo){
                type = "couleur = " + (((Stylo) article).getCouleur());
            }
            if (article instanceof Ramette){
                type = "grammage = " + ((Ramette) article).getGrammage();
            }

            String sql = "set IDENTITY_INSERT Article ON"+"INSERT INTO Article VALUES(" +
                    "reference ='"+ article.getReference() +"' "+
                    "marque ='"+article.getMarque() +"' "+
                    "designation ='"+article.getDesignation() +"' "+
                    "qteStock ="+article.getQteStock() +
                    "prixUnitaire ="+article.getPrixUnitaire() + ")"+
                    type+
                    "WHERE idarticle =" + article.getIdArticle()+
                    ";"+"set IDENTITY_INSERT Article OFF";

            etat.executeQuery(sql);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }


    /**
     * méthode de suppréssion d'article
     * @param article
     */
    public void delete (Article article) {

        try {
            Connection connection = DriverManager.getConnection(URL);
            Statement etat = connection.createStatement();
            String sql = "DELETE FROM Article WHERE idArticle ="+ article.getIdArticle();
            etat.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}