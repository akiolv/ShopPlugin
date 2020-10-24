package fr.pwetpwet.shopPlugin.sql;

import fr.pwetpwet.shopPlugin.EncodingItem;
import fr.pwetpwet.shopPlugin.ShopPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

        Inventory inventory = player.getInventory();
        int indexItem = inventory.first(item);
        ItemStack invItem = inventory.getItem(indexItem);
        int nbrItem = invItem.getAmount();
        if(nbrItem <= 1) {
            inventory.clear(indexItem);
        } else {
            invItem.setAmount(invItem.getAmount()-1);
        }
    }
}