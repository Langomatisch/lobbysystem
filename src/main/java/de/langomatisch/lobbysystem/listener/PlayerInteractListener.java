package de.langomatisch.lobbysystem.listener;

import de.langomatisch.lobbysystem.LobbySystem;
import de.langomatisch.lobbysystem.config.ItemStackEntry;
import de.langomatisch.lobbysystem.util.NBTModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    
    @EventHandler
    public void onInteract( PlayerInteractEvent event ) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();
        final FileConfiguration config = LobbySystem.getInstance().getConfig();
        if ( ( event.getAction().equals( Action.RIGHT_CLICK_BLOCK ) || event.getAction().equals( Action.RIGHT_CLICK_AIR ) )
                && !config.getBoolean( "CanRightClickItems" ) ) {
            return;
        }
        if ( ( event.getAction().equals( Action.LEFT_CLICK_BLOCK ) || event.getAction().equals( Action.LEFT_CLICK_AIR ) )
                && !config.getBoolean( "CanLeftClickItems" ) ) {
            return;
        }
        if ( item == null || item.getType().equals( Material.AIR ) || !NBTModifier.hasNBTTag( item, "item" ) ) return;
        event.setCancelled( true );
        final ItemStackEntry itemStackEntry = LobbySystem.getInstance().getInventory().get( Integer.valueOf( NBTModifier.getNBTTag( item, "item" ) ) );
        final String command = itemStackEntry.getCommand();
        if ( command.startsWith( "console:" ) )
            Bukkit.dispatchCommand( Bukkit.getConsoleSender(), itemStackEntry.getCommand().replace( "console:", "" ) );
        else event.getPlayer().performCommand( itemStackEntry.getCommand() );
    }
    
}
