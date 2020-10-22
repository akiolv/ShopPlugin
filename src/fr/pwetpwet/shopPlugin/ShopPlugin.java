package fr.pwetpwet.shopPlugin;

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

        System.out.println(getConfig().getString("database.adresse"));

        System.out.println("Plugin allumé");
    }

    @Override
    public void onDisable(){
        System.out.println("Plugin eteint");
    }

}
