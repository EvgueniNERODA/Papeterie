package fr.eni.papeterie.dal;

import fr.eni.papeterie.bo.Article;

import java.util.List;

/**
 * interface ArticleDAO, contient les signatures des méthodes imlémentées dans ArticleDAOJdbcIml
 *
 * @author eneroda2021
 * @version 1.0
 */
public interface ArticleDAO {

    //création des signatures des méthodes abstraites
    List selectAll ();

    Article selectById (int id);

    void update (Article article);

    void insert (Article article);

    void delete (int id);


}
