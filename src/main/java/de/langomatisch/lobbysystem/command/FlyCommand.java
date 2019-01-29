package de.langomatisch.lobbysystem.command;

import de.langomatisch.lobbysystem.LobbySystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings ) {
        
        if ( !( commandSender instanceof Player ) ) return false;
        
        Player player = (Player) commandSender;
        player.setAllowFlight( !player.getAllowFlight() );
        player.sendMessage( player.getAllowFlight() ? "an" : "aus" );
        return false;
    }
    
}
