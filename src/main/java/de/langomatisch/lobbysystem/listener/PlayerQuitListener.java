package de.langomatisch.lobbysystem.listener;

import de.langomatisch.lobbysystem.scoreboard.SmartScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    
    @EventHandler
    public void onQuit( PlayerQuitEvent event ) {
        SmartScoreboard.removeUser( event.getPlayer() );
    }
    
}
