package de.langomatisch.lobbysystem.listener;

import de.langomatisch.lobbysystem.command.BuildCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    
    @EventHandler
    public void onBreak( BlockBreakEvent event ) {
        event.setCancelled( !BuildCommand.getBuildPlayers().contains( event.getPlayer().getUniqueId() ) );
    }
    
}
