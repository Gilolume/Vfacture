package com.example.gwen.vfacture;

/**
 * Created by gwen on 22/01/2016.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectBDD {

    private Connection conn = null;

    private static String chemin_serveur;
    private static String admin_serveur;
    private static String mdp_admin_serveur;

    public static Connection Connexion_bdd(boolean base_internet){
        if (base_internet == false){
            chemin_serveur = "jdbc:mysql://82.241.74.36:3100/sog505";
            admin_serveur = "ggierak";
            mdp_admin_serveur = "ot7abCx926z";
        }
        else{
            chemin_serveur = "jdbc:mysql://mysql1.alwaysdata.com/sogdev_505";
            admin_serveur = "sogdev";
            mdp_admin_serveur = "pas_là";
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(chemin_serveur,admin_serveur,mdp_admin_serveur);
            return conn;
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
