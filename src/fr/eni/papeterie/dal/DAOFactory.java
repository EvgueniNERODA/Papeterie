package fr.eni.papeterie.dal;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.dal.jdbc.ArticleDAOJdbcImpl;

/**
 * classe DAOFactory située dans la DAL, sert d'intermédiaire entre
 * l'interface ArticleDAO et la BLL AppliTestDAL
 *
 * @author eneroda2021
 * @version 1.0
 */

public class DAOFactory {


    public static  ArticleDAO getArticleDAO(){

        ArticleDAO articleDAO = new ArticleDAOJdbcImpl();

        return articleDAO;
    }
}