package fr.pwetpwet.shopPlugin;

import fr.pwetpwet.shopPlugin.sql.SQL;
import fr.pwetpwet.shopPlugin.sql.Transfert;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandShow implements CommandExecutor {

    private ShopPlugin main;

    public CommandShow(ShopPlugin shopPlugin) {
        this.main = shopPlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender instanceof Player && command.getName().equalsIgnoreCase("shop")) {

            Player player = (Player) commandSender;

            if (args.length == 1 && args[0].equalsIgnoreCase("banque")) {

                String uuid = player.getUniqueId().toString();
                String pseudo = player.getName();

                String fichierSQL = main.getConfig().getString("database.adresse");
                String user = main.getConfig().getString("database.user");
                String password = main.getConfig().getString("database.password");

                SQL bdd = new SQL();
                int currency = bdd.initNewPlayer(fichierSQL, user, password, uuid, pseudo);

                player.sendMessage("§2Tu as actuellement §c" + currency + " " + main.getConfig().getString("jeu.nom-money") + "§2 sur ce compte.");

            } else if (args.length == 1 && args[0].equalsIgnoreCase("sell")) {

                Inventory inv = Bukkit.createInventory(null, 9, "§8Vente");
                player.openInventory(inv);

            } else {
                player.sendMessage("Il faut mettre quelque chose derrière zebi");
            }
        }

        return false;

    }
}
