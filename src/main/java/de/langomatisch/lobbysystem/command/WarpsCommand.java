package de.langomatisch.lobbysystem.command;

import de.langomatisch.lobbysystem.LobbySystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpsCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings ) {
        if ( !( commandSender instanceof Player ) ) return false;
        Player player = (Player) commandSender;
        player.openInventory( LobbySystem.getInstance().getWarpInventory().getInventory() );
        return false;
    }
    
}
