package de.langomatisch.lobbysystem.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Data
@AllArgsConstructor
public class Warp {
    
    private String warpName;
    private String material;
    private int slot;
    private String world;
    private double x, y, z;
    
    public ItemStack toItemStack() {
        return new ItemBuilder( Material.valueOf( material ) ).setNBT( "warp", warpName ).build();
    }
    
    public Location toLocation() {
        return new Location( Bukkit.getWorld( world ), x, y, z );
    }
    
}
