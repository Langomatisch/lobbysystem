package de.langomatisch.lobbysystem.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class ClickableInventory extends ClickableInventoryHolder {
    
    public ClickableInventory( String title, int size ) {
        super( Bukkit.createInventory( null, size, title ) );
    }
    
    abstract void onClick( Player player, ItemStack itemStack );
    
}
