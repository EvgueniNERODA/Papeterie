package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl {
    private final String URL = "jdbc:sqlite:papeterie_db.sqlite";

    public List selectAll() {
        List<Article> listeArticle = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement unAutreEtat = connection.createStatement();
            String sql = "SELECT * FROM Article;";
            //on stocke le resultat dans une variable de type ResulteSet
            ResultSet rs = unAutreEtat.executeQuery(sql);

            while (rs.next()) {
                if (rs.getString("type").equalsIgnoreCase("ramette")) {
                    Article artRamette = new Ramette(rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getInt("grammage"));
                    listeArticle.add(artRamette);

                }
                if (rs.getString("type").equalsIgnoreCase("stylo")) {
                    Article artStylo = new Stylo(rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getString("couleur"));
                    listeArticle.add(artStylo);

                }
            }

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
        try (Connection connection = DriverManager.getConnection(this.URL);
                Statement etat = connection.createStatement()) {

            //requète
            String sql = "SELECT idArticle, reference, marque, designation, prixUnitaire, qteStock, grammage, couleur," +
                    "type FROM Article WHERE idArticle = " + id + ";";

            //affichage de la requète pour plus de visibilité
            System.out.println(sql);

            //on mets le resultat de la requète dans une variable de type ResultSet
            ResultSet rs = etat.executeQuery(sql);

            //on sait qu'il n'y a qu'une ligne on peut donc utiliser le if pour parcourir le Resultset
            if (rs.next()) {

                //l'article peut être de type stylo ou ramette les constructeurs sont différents on doit donc distinguer les deux cas
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
     * méthode prenant en paramètre un article et mettant à jour les attibuts
     * d'un article
     *
     * @param article
     */
    public void update(Article article) {
        //ouverture de la connection dans le try afin qu'ils soient fermés automatiquement
        try (Connection cnx = DriverManager.getConnection(URL); Statement etat = cnx.createStatement()) {

            //initialisation de la variable type
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

            //requète
            String sql = "UPDATE Article SET " +
                    "reference ='" + article.getReference() + "', " +
                    "marque ='" + article.getMarque() + "', " +
                    "designation ='" + article.getDesignation() + "', " +
                    "qteStock =" + article.getQteStock() + "," +
                    "prixUnitaire =" + article.getPrixUnitaire() + ", " +
                    type +
                    " WHERE idarticle =" + article.getIdArticle() + ";";


            //pas de ResultSet par ce qu'on ne retourne rien

            //exectution de la requète
            etat.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * méthode d'ajout d'un article dans la base de données
     */

    public void insert(Article article) {
        //si on mets la connection dans try elle sera fermé en sortant, idem pour l'état
        try (Connection cnx = DriverManager.getConnection(this.URL); Statement etat = cnx.createStatement()) {

            String type = "";
            if (article instanceof Ramette) {
                type = ((Ramette) article).getGrammage() + ", null, 'RAMETTE'";
            }
            if (article instanceof Stylo) {
                type = "null,'" + ((Stylo) article).getCouleur() + "','STYLO'";
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

            //récupère
            ResultSet rs = etat.getGeneratedKeys();

            if (rs.next()) {
                //get int renvoit un tableau avec les numéros de cases, on veut la première, qui correspond à l'id généré dans la bdd
                int id = rs.getInt(1);

                //on modifie
                article.setIdArticle(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * méthode de suppression d'article de la base de données
     *
     * @param id
     */
    public void delete(int id) {
        Article article = null;
        //ouverture de la connection dans le try afin qu'ils soient fermés automatiquement
        try (Connection connection = DriverManager.getConnection(URL); Statement etat = connection.createStatement()) {

            String sql = "DELETE FROM Article WHERE idArticle =" + id + ";";

            System.out.println(sql);
            etat.executeUpdate(sql);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}