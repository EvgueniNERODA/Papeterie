package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl {
    private final String URL = "jdbc:sqlite:papeterie_db.sqlite";

    public List<Article> selectAll() {
        List listeArticle = new ArrayList();
        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement unAutreEtat = connection.createStatement();
            String sql = "SELECT * FROM Article;";
            //on stocke le resultat dans une variable
            ResultSet rs = unAutreEtat.executeQuery(sql);

            listeArticle.add(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeArticle;
    }

    /**
     * méthode séléctionnant un article par son identifiant
     * paramètre int id
     */

    public Article selectById(int id) {
        Article article = null;
        try (
                Connection connection = DriverManager.getConnection(this.URL);
                Statement etat = connection.createStatement()
        ) {
            String sql = "SELECT idArticle, reference, marque, designation, prixUnitaire, qteStock, grammage, couleur," +
                    "type FROM Article WHERE idArticle = " + id + ";";
            System.out.println(sql);
            ResultSet rs = etat.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("type").trim().equalsIgnoreCase("RAMETTE")) {
                    article = new Ramette(
                            rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getInt("grammage")
                    );
                }
                if (rs.getString("type").trim().equalsIgnoreCase("STYLO")) {
                    article = new Stylo(
                            rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getString("couleur")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return article;
    }



    /**
     * méthode prenant en paramètre un article et mettant à jour les quantitiés
     *
     * @param article
     */
    public void update(Article article) {
        try {
            Connection cnx = DriverManager.getConnection(this.URL);
            Statement unAutreEtat = cnx.createStatement();

            String type = "";
            //on vérifie quelle est l'instance de l'article
            //si l'article est un stylo on utilise la couleur qui se trouve dans la clase Stylo
            if (article instanceof Stylo) {
                type = "couleur = ' " + ((Stylo) article).getCouleur() + "'";
            }
            //si l'article est une ramette on utilise le grammage qui se trouve dans la clase Ramette
            if (article instanceof Ramette) {
                type = "grammage = " + ((Ramette) article).getGrammage() + " ";
            }
            String sql = "UPDATE Article SET " +
                    "reference ='" + article.getReference() + "', " +
                    "marque ='" + article.getMarque() + "' " +
                    "designatoion ='" + article.getDesignation() + "', " +
                    "qteStock =" + article.getQteStock() +
                    "prixUnitaire =" + article.getPrixUnitaire() + ", " +
                    type +
                    " WHERE idarticle =" + article.getIdArticle() + ";";

            //pas de ResultSet par ce qu'on ne retourne rien

            unAutreEtat.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * méthode d'ajout d'un article dans la base de données
     */

    public void insert(Article article) {
        //si on mets la connection dans try elle sera fermé en sortant, idem pour l'état
        try (Connection cnx = DriverManager.getConnection(this.URL);Statement etat = cnx.createStatement()){

            String type = "";
            if (article instanceof Ramette) {
                type = ((Ramette) article).getGrammage() + ", null, 'RAMETTE'";
            }
            if (article instanceof Stylo) {
                type =  "null,'" + ((Stylo) article).getCouleur() + "','STYLO'";
            }

            String sql = "INSERT INTO Article" +
                    "(reference, marque, designation, prixUnitaire, qteStock, grammage, couleur, type)" +
                    " VALUES ('" +
                    article.getReference() + "','" +
                    article.getMarque() + "','" +
                    article.getDesignation() + "'," +
                    article.getPrixUnitaire() + "," +
                    article.getQteStock() + "," +
                    type + ");";
            etat.executeUpdate(sql);
            ResultSet rs = etat.getGeneratedKeys();

            if (rs.next()){
                //get int renvoi un tableau avec les numéros de cases, on veut la première, qui correspond à l'id généré dans la bdd
                int id = rs.getInt(1);
                article.setIdArticle(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * méthode de suppréssion d'article
     *
     * @param article
     */
    public void delete(Article article) {

        try {
            Connection connection = DriverManager.getConnection(URL);
            Statement etat = connection.createStatement();
            String sql = "DELETE FROM Article WHERE idArticle =" + article.getIdArticle();
            etat.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}