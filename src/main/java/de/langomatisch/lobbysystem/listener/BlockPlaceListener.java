package de.langomatisch.lobbysystem.listener;

import de.langomatisch.lobbysystem.command.BuildCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    
    @EventHandler
    public void onPlace( BlockPlaceEvent event ) {
        event.setCancelled( !BuildCommand.getBuildPlayers().contains( event.getPlayer().getUniqueId() ) );
    }
    
}
