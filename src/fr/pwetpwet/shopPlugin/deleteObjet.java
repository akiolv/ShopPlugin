package fr.pwetpwet.shopPlugin;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class deleteObjet {

    public deleteObjet(Player player, ItemStack item){

        Inventory inv = player.getInventory();
        ItemStack[] currents = inv.getContents();

        if(inv.containsAtLeast(item, item.getAmount())) {
            int itemThatMustBeRemove = item.getAmount();
            while(itemThatMustBeRemove > 0) {
                final int indexItem = inv.first(item);
                final ItemStack invItem = inv.getItem(indexItem);
                final int nbrItem = invItem.getAmount();
                itemThatMustBeRemove -= nbrItem;
                if(nbrItem <= itemThatMustBeRemove) {
                    inv.clear(indexItem);
                } else {
                    invItem.setAmount(invItem.getAmount()-itemThatMustBeRemove);
                }
            }
        }
    }
}
