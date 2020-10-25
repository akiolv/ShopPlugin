package fr.pwetpwet.shopPlugin;

import fr.pwetpwet.shopPlugin.sql.SQL;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopPlugin extends JavaPlugin{

    @Override
    public void onEnable(){

        saveDefaultConfig();

        String fichierSQL = getConfig().getString("database.adresse");
        String username = getConfig().getString("database.user");
        String password = getConfig().getString("database.password");
        getServer().getPluginManager().registerEvents(new NewPlayerListerner(this), this);

        getCommand("shop").setExecutor(new CommandShow(this));

        SQL bdd = new SQL();
        bdd.initBDD(fichierSQL, username, password);

        System.out.println("Plugin allume");
    }

    @Override
    public void onDisable(){
        System.out.println("Plugin eteint");
    }

}
