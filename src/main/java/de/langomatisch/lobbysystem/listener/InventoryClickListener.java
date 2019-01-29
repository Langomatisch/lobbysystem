package de.langomatisch.lobbysystem.listener;

import de.langomatisch.lobbysystem.LobbySystem;
import de.langomatisch.lobbysystem.config.ItemStackEntry;
import de.langomatisch.lobbysystem.inventory.WarpInventory;
import de.langomatisch.lobbysystem.util.NBTModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    
    @EventHandler
    public void onInteract( InventoryClickEvent event ) {
        final ItemStack currentItem = event.getCurrentItem();
        if ( event.getClickedInventory() == null || currentItem == null || currentItem.getType().equals( Material.AIR ) )
            return;
        if ( !NBTModifier.hasNBTTag( event.getCurrentItem(), "item" ) ) return;
        event.setCancelled( true );
        int id = Integer.valueOf( NBTModifier.getNBTTag( currentItem, "item" ) );
        final ItemStackEntry itemStackEntry = LobbySystem.getInstance().getInventory().get( id );
        if ( !LobbySystem.getInstance().getConfig().getBoolean( "CanClickItems" ) ) return;
        final String command = itemStackEntry.getCommand();
        if ( command.startsWith( "console:" ) )
            Bukkit.dispatchCommand( Bukkit.getConsoleSender(), itemStackEntry.getCommand().replace( "console:", "" ) );
        else ( (Player) event.getWhoClicked() ).performCommand( itemStackEntry.getCommand() );
    }
    
    @EventHandler
    public void onInventoryClick( InventoryClickEvent event ) {
        final ItemStack currentItem = event.getCurrentItem();
        final Inventory inventory = event.getInventory();
        if ( inventory == null ) return;
        if ( !inventory.equals( LobbySystem.getInstance().getWarpInventory().getInventory() ) ) return;
        final WarpInventory warpInventory = LobbySystem.getInstance().getWarpInventory();
        warpInventory.onClick( (Player) event.getWhoClicked(), currentItem );
    }
    
}
