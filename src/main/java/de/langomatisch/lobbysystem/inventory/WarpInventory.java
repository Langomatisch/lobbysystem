package de.langomatisch.lobbysystem.inventory;

import de.langomatisch.lobbysystem.LobbySystem;
import de.langomatisch.lobbysystem.util.NBTModifier;
import de.langomatisch.lobbysystem.util.Warp;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class WarpInventory extends ClickableInventory {
    
    public WarpInventory() {
        super( "Warps", 3 * 9 );
        for ( Warp warp : LobbySystem.getInstance().getWarps() ) {
            getInventory().setItem( warp.getSlot(), warp.toItemStack() );
        }
    }
    
    
    @Override
    public void onClick( Player player, ItemStack itemStack ) {
        if ( !NBTModifier.hasNBTTag( itemStack, "warp" ) ) return;
        String warpName = NBTModifier.getNBTTag( itemStack, "warp" );
        Warp warp = null;
        for ( Warp warps : LobbySystem.getInstance().getWarps() ) {
            if ( warps.getWarpName().equals( warpName ) ) warp = warps;
        }
        if ( warp == null ) return;
        player.teleport( warp.toLocation() );
        player.sendMessage( "teleported" );
    }
    
}
