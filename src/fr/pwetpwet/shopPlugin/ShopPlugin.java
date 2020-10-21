package fr.pwetpwet.shopPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class ShopPlugin extends JavaPlugin{

    @Override
    public void onEnable(){
        System.out.println("Plugin allumé");
    }

    @Override
    public void onDisable(){
        System.out.println("Plugin eteint");
    }

}
