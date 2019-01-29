package de.langomatisch.lobbysystem.listener;

import de.langomatisch.lobbysystem.LobbySystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    
    @EventHandler
    public void onDamage( EntityDamageEvent event ) {
        event.setCancelled( LobbySystem.getInstance().getConfig().getBoolean( "EntityDamage" ) );
    }
    
}
