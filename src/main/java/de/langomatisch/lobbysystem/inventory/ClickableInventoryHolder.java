package de.langomatisch.lobbysystem.inventory;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@AllArgsConstructor
public abstract class ClickableInventoryHolder implements InventoryHolder {
    
    private final Inventory inventory;
    
    @Override
    public Inventory getInventory() {
        return inventory;
    }
    
}
