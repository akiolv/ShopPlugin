package fr.pwetpwet.shopPlugin.sql;

import fr.pwetpwet.shopPlugin.EncodingItem;
import fr.pwetpwet.shopPlugin.ShopPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Vector;

public class Transfert {

    private ShopPlugin main;

    public Transfert(ShopPlugin main) {
        this.main = main;
    }

    public void vente(Player player, ItemStack item, int prix) {

        EncodingItem encodedItem = new EncodingItem(item);
        String base64Item = encodedItem.codeItem();

        String fichierSQL = main.getConfig().getString("database.adresse");
        String user = main.getConfig().getString("database.user");
        String password = main.getConfig().getString("database.password");

        String uuid = player.getUniqueId().toString();
        String pseudo = player.getName();

        SQL bdd = new SQL();
        bdd.ajoutObjet(fichierSQL, user, password, uuid, pseudo, base64Item, prix);
    }

    public void achat(Player player, ItemStack item){

        EncodingItem encodedItem = new EncodingItem(item);
        String base64Item = encodedItem.codeItem();

        String fichierSQL = main.getConfig().getString("database.adresse");
        String user = main.getConfig().getString("database.user");
        String password = main.getConfig().getString("database.password");

        String uuid = player.getUniqueId().toString();
        String pseudo = player.getName();

        SQL bdd = new SQL();
        bdd.retirerObjet(fichierSQL, user, password, uuid, pseudo, base64Item);

    }

    public Vector<Vector> afficher(String cdt){

        Vector<Vector> listeVenteDecodé = new Vector();

        String fichierSQL = main.getConfig().getString("database.adresse");
        String user = main.getConfig().getString("database.user");
        String password = main.getConfig().getString("database.password");

        SQL bdd = new SQL();
        Vector<Vector> ventes = bdd.afficherItemVente(fichierSQL, user, password, cdt);

        for(Vector l : ventes){
            Vector temp = new Vector();
            temp.add(l.get(0));
            temp.add(l.get(1));

            EncodingItem encodedItem = new EncodingItem();
            ItemStack decodedItem = encodedItem.decodeItem((String) l.get(2));

            temp.add(decodedItem);

            listeVenteDecodé.add(temp);
        }

        return listeVenteDecodé;

    }
}
