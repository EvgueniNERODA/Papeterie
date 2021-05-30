package fr.eni.papeterie.dal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * classe JdbcTools, récupère l'url via la méthode getPropriete de la classe Settings et ouvre une connectin grâce à la methode recupConnection
 * @author eneroda2021
 * @version 1.0
 */

public class    JdbcTools {

    private final static String url = Settings.getPropriete("url");//on va chercher l'url dans le fichier

    public static Connection recupConnetion () throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }
}
