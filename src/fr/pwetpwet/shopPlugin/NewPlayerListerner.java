package fr.pwetpwet.shopPlugin;


import fr.pwetpwet.shopPlugin.sql.SQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NewPlayerListerner implements Listener {

    private ShopPlugin main;

    public NewPlayerListerner(ShopPlugin shopPlugin) {
        this.main = shopPlugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String pseudo = player.getName();

        String fichierSQL = main.getConfig().getString("database.adresse");
        String user = main.getConfig().getString("database.user");
        String password = main.getConfig().getString("database.password");

        SQL bdd = new SQL();
        int currency = bdd.initNewPlayer(fichierSQL, user, password, uuid, pseudo);

        player.sendMessage("§2Tu as actuellement §c" + currency + " " + main.getConfig().getString("jeu.nom-money") +"§2 sur ce compte.");
    }
}
