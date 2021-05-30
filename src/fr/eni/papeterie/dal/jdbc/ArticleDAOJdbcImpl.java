package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

/**
 * classe ArticleDAOJdbcIml situé dans la DAL, contient les méthodes faisant appel
 * aux requetes SQL
 *
 * @author eneroda2021
 * @version 1.2
 *
 */

public class ArticleDAOJdbcImpl  implements ArticleDAO {
    private final String URL = Settings.getPropriete("url");
    private  final String SQL_SELCTBYID = "SELECT idArticle, reference, marque, designation, prixUnitaire, qteStock, grammage, couleur,type FROM Article WHERE idArticle =?;";
    private final String SQL_DELETE = "DELETE FROM Article WHERE idArticle =?";
    private final String SQL_UPDATE = "UPDATE Article SET reference =?, marque = ?, designation = ?, prixUnitaire = ?, qteStock = ?, grammage = ?, couleur = ? WHERE idArticle = ?;";
    private final String SQL_INSERT = "INSERT INTO Article (reference, marque , designation , prixUnitaire , qteStock , grammage , couleur, type ) VALUES(?,?,?,?,?,?,?,?);";


    /**
     * permet de sélectionnet tous les articles de la tables Article
     * les différencie selon le type(stylo ou ramette) et les place dans
     * une liste afin de les afficher par la suite
     * @return listeArticle
     */
    @Override
    public List selectAll() {

            List<Article> listeArticle = new ArrayList<>();
            try {
                Connection connection = JdbcTools.recupConnetion();

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
     * paramètre int id, retourne l'article séléctionné en faisant
     * entre ramette ou stylo (le constructeur n'est pas le même dans les deux cas)
     * @param id de l'article
     * @return article
     */

    @Override
    public Article selectById(int id) {
        Article article = null;
        try (Connection connection = JdbcTools.recupConnetion(); Statement reqPrepare = connection.createStatement()) {

            //requète


            //affichage de la requète pour plus de visibilité
            System.out.println(SQL_SELCTBYID);

            //création PreparedStatement
            PreparedStatement reqPreparee = connection.prepareStatement(SQL_SELCTBYID);

            reqPreparee.setInt(1,id);
            //on mets le resultat de la requète dans une variable de type ResultSet
            ResultSet rs = reqPreparee.executeQuery();

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return article;
    }

    /**
     * méthode prenant en paramètre un article et mettant à jour ses attibuts
     *
     *
     * @param article
     */
    @Override
    public void update(Article article) {
        //ouverture de la connection dans le try afin qu'ils soient fermés automatiquement
        try (Connection cnx = JdbcTools.recupConnetion(); PreparedStatement reqPrepare = cnx.prepareStatement(SQL_UPDATE)) {

            reqPrepare.setString(1, article.getReference());
            reqPrepare.setString(2, article.getMarque());
            reqPrepare.setString(3, article.getDesignation());
            reqPrepare.setFloat(4, article.getPrixUnitaire());
            reqPrepare.setInt(5, article.getQteStock());
            //si c'est un stylo Jaava mettra null à la place du 6 si s'est une ramette idem
            reqPrepare.setInt(8, article.getIdArticle());


            //on vérifie quelle est l'instance de l'article
            //si l'article est un stylo on utilise la couleur qui se trouve dans la clase Stylo
            if (article instanceof Stylo) {
                reqPrepare.setString(7, ((Stylo) article).getCouleur());
            }
            //si l'article est une ramette on utilise le grammage qui se trouve dans la clase Ramette
            if (article instanceof Ramette) {
                reqPrepare.setInt(6, ((Ramette) article).getGrammage());
            }


            //exectution de la requète
            reqPrepare.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * méthode d'ajout d'un article dans la base de données
     * comporte une condition sur le type d'article à ajouter en cas de stylo ou de ramette
     * @param  article
     */

    @Override
    public void insert(Article article) {
        //si on mets la connection dans try elle sera fermé en sortant, idem pour l'état
        try (Connection cnx = JdbcTools.recupConnetion(); PreparedStatement reqPrepare = cnx.prepareStatement(this.SQL_INSERT)) {// POUR SQL SERVEUR ajouter Statement.RETURN_GENERATED_KEYS

            //on indique les valeurs des ?
            reqPrepare.setString(1, article.getReference());
            reqPrepare.setString(2, article.getMarque());
            reqPrepare.setString(3, article.getDesignation());
            reqPrepare.setFloat(4, article.getPrixUnitaire());
            reqPrepare.setInt(5, article.getQteStock());
            //si c'est un stylo Java mettra null à la place du 6 si s'est une ramette idem

            if (article instanceof Ramette) {
                reqPrepare.setInt(6, ((Ramette) article).getGrammage());
                reqPrepare.setString(8, "RAMETTE");
            }
            if (article instanceof Stylo) {
                reqPrepare.setString(7, ((Stylo) article).getCouleur());
                reqPrepare.setString(8, "STYLO");
            }


            reqPrepare.executeUpdate();

            //récupère l'id autogénéré par l'insert mais cela retourne un ResulteSet
            ResultSet rs = reqPrepare.getGeneratedKeys();

            if (rs.next()) {
                //get int renvoit un tableau avec les numéros de cases, on veut la première, qui correspond à l'id généré dans la bdd
                //on le mets donc dans l'article en utilisant un setter
                int id = rs.getInt(1);

                //on modifie
                article.setIdArticle(id);

                //article.setIdArticle(rs.getInt(1)); même chose en plus court
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
    @Override
    public void delete(int id) {
        Article article = null;
        //ouverture de la connection dans le try afin qu'ils soient fermés automatiquement
        try (Connection connection = JdbcTools.recupConnetion(); Statement reqPrepare = connection.createStatement()) {

            //on crée le PreparedStatement
            PreparedStatement reqPreparee = connection.prepareStatement(this.SQL_DELETE);

            //on indique que le premier ? correspond à l'id
            reqPreparee.setInt(1, id);

            //on execute la requète
            reqPreparee.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}