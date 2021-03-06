package fr.pwetpwet.shopPlugin.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class SQL {

    private Connection con;
    private boolean connected;
    private boolean firstjoin = true;
    private int currency = 0;

    public void initBDD(String fichierSQL, String username, String password){
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

            Statement statement = con.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS \"monnaie\" (\"uuid\"\tvarchar(150) NOT NULL UNIQUE, \"pseudo\" TEXT, \"balance\"\tINTEGER, PRIMARY KEY(\"uuid\"))");
            statement.execute("CREATE TABLE IF NOT EXISTS \"vente\" (\"uuid\"\tvarchar(150), \"prix\" INTEGER, \"objet\"\tTEXT, PRIMARY KEY(\"uuid\"))");

            statement.close();
            con.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(" Erreur exécution requête !");
            throwables.printStackTrace();
        }
    }

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

    public void ajoutObjet(String fichierSQL, String username, String password, String uuid, String pseudo, String itemCoded, int prix){

        int actualCurrency = 0;

        try {
            System.out.println("[Status] Connection to database");
            Class.forName("org.sqlite.JDBC");
            if(username!= "" && password != "") {
                con = DriverManager.getConnection("jdbc:sqlite:" + fichierSQL, username, password);
            } else {
                con = DriverManager.getConnection("jdbc:sqlite:" + fichierSQL);
            }
            System.out.println("[Status] Connected");

            // Recherche des données du vendeur
            Statement statement = con.createStatement();
            ResultSet unicite = statement.executeQuery("SELECT uuid, pseudo, balance FROM monnaie");
            System.out.println(uuid);
            while (unicite.next()) {
                String uuidDB = unicite.getString("uuid");
                if (uuidDB.equals(uuid)) {
                    actualCurrency = unicite.getInt("balance");
                }
            }
            unicite.close();

            int newCurrency = actualCurrency + prix;

            // Inscription
            System.out.println("[STATUS] Mise en vente...");
            int result = statement.executeUpdate("INSERT INTO vente VALUES ('" + uuid + "', \'" + prix + "', '" + itemCoded + "')");
            int result2 = statement.executeUpdate("UPDATE monnaie SET balance = " + newCurrency + " WHERE uuid = '" + uuid +"'");
            System.out.println("[STATUS] Item en vente");

            // Deconnection
            statement.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(" Erreur exécution requête !");
            System.out.println(throwables);
        }
    }

    public void retirerObjet(String fichierSQL, String username, String password, String uuid, String pseudo, String itemCoded){

        String uuidVendeur = null;
        int balanceAcheteur = Integer.parseInt(null);
        int prixObjet = Integer.parseInt(null);
        int balanceVendeur = Integer.parseInt(null);

        try {
            System.out.println("[Status] Connection to database");
            Class.forName("org.sqlite.JDBC");
            if(username!= "" && password != "") {
                con = DriverManager.getConnection("jdbc:sqlite:" + fichierSQL, username, password);
            } else {
                con = DriverManager.getConnection("jdbc:sqlite:" + fichierSQL);
            }
            System.out.println("[Status] Connected");

            // Recherche des données du vendeur
            Statement statement = con.createStatement();
            ResultSet unicite = statement.executeQuery("SELECT uuid, prix, objet FROM vente");
            while (unicite.next()) {
                String item = unicite.getString("objet");
                if (item.equals(itemCoded)) {
                    uuidVendeur = unicite.getString("uuid");
                    prixObjet = unicite.getInt("prix");
                    ResultSet infoVendeur = statement.executeQuery("SELECT uuid, pseudo, balance FROM monnaie WHERE uuid='" + uuidVendeur +"'");
                    balanceVendeur = infoVendeur.getInt("balance");
                }
            }

            ResultSet infoAcheteur = statement.executeQuery("SELECT uuid, pseudo, balance FROM monnaie WHERE uuid='" + uuid +"'");
            balanceAcheteur = infoAcheteur.getInt("balance");

            int newBalanceVendeur = balanceVendeur + prixObjet;
            int newBalanceAcheteur = balanceAcheteur - prixObjet;

            // Inscription
            System.out.println("[STATUS] Mise en vente...");
            int result = statement.executeUpdate("DELETE FROM vente WHERE uuid='" + uuid + "' AND prix= " + prixObjet + " AND objet='" + itemCoded + "')");
            int result2 = statement.executeUpdate("UPDATE monnaie SET balance = " + newBalanceVendeur + " WHERE uuid = '" + uuidVendeur +"'");
            int result3 = statement.executeUpdate("UPDATE monnaie SET balance = " + newBalanceAcheteur + " WHERE uuid = '" + uuid +"'");
            System.out.println("[STATUS] Item en vente");

            // Deconnection
            statement.close();

            // Recupération Max(Id)
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(" Erreur exécution requête !");
            System.out.println(throwables);
        }
    }

    public Vector<Vector> afficherItemVente(String fichierSQL, String username, String password, String cdt){

        Vector<Vector> ventes = new Vector();

        if (cdt != null){ cdt = new StringBuilder().append("WHERE ").append(cdt).toString();}

        try {
            System.out.println("[Status] Connection to database");
            Class.forName("org.sqlite.JDBC");
            if(username!= "" && password != "") {
                con = DriverManager.getConnection("jdbc:sqlite:" + fichierSQL, username, password);
            } else {
                con = DriverManager.getConnection("jdbc:sqlite:" + fichierSQL);
            }
            System.out.println("[Status] Connected");

            // Selection des items
            Statement statement = con.createStatement();
            ResultSet unicite = statement.executeQuery("SELECT uuid, prix, objet FROM vente");
            while (unicite.next()) {
                Vector temp = new Vector();
                temp.add(unicite.getString("uuid"));
                temp.add(unicite.getInt("prix"));
                temp.add(unicite.getString("objet"));
                ventes.add(temp);
            }

            // Deconnection
            statement.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(" Erreur exécution requête !");
            System.out.println(throwables);
        }

        return ventes;

    }
}
