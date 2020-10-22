package fr.pwetpwet.shopPlugin.sql;

import java.sql.*;

public class SQL {

    private Connection con;
    private boolean connected;
    private boolean firstjoin = true;
    private int currency = 0;

    public int initNewPlayer(String fichierSQL, String username, String password, String uuid, String pseudo) {
        // Connection
        System.out.println(fichierSQL);
        try {
            System.out.println("[Status] Connection to database");
            Class.forName("org.sqlite.JDBC");
            if(username!= "" && password != "") {
                con = DriverManager.getConnection("jdbc:sqlite:" + fichierSQL, username, password);
            } else {
                con = DriverManager.getConnection("jdbc:sqlite:" + fichierSQL);
            }
            System.out.println("[Status] Connected");
            connected = true;

            // Verification de l'unicité
            System.out.println("[STATUS] Connexion of " + pseudo + "...");
            Statement statement = con.createStatement();
            ResultSet unicite = statement.executeQuery("SELECT uuid, pseudo, balance FROM monnaie");
            System.out.println(uuid);
            while (unicite.next()) {
                String uuidDB = unicite.getString("uuid");
                if (uuidDB.equals(uuid)) {
                    firstjoin = false;
                    currency = unicite.getInt(3);
                }
            }
            unicite.close();

            // Ajout si unique
            if (firstjoin) {
                int added = statement.executeUpdate("INSERT INTO monnaie VALUES ('" + uuid + "', \'" + pseudo + "', 0)");
                System.out.println("[STATUS] " + pseudo + " registred");
            }
            statement.close();
            con.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(" Erreur exécution requête !");
            throwables.printStackTrace();
        }
        return currency;
    }
}