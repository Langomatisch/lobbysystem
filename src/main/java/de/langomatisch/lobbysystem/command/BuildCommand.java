package de.langomatisch.lobbysystem.command;

import de.langomatisch.lobbysystem.LobbySystem;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuildCommand implements CommandExecutor {
    
    @Getter
    private static List<UUID> buildPlayers = new ArrayList<>();
    
    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings ) {
        if ( !( commandSender instanceof Player ) ) return false;
        Player player = (Player) commandSender;
        if ( buildPlayers.remove( player.getUniqueId() ) ) {
            //player.sendMessage( LobbySystem.getInstance().getCurrentLanguage().getMessage( "command-build-turnedoff", LobbySystem.getPrefix() ) );
            player.sendMessage( "build aus" );
            player.setGameMode( GameMode.SURVIVAL );
            LobbySystem.getInstance().setInventory( player );
        } else {
            buildPlayers.add( player.getUniqueId() );
            //player.sendMessage( LobbySystem.getInstance().getCurrentLanguage().getMessage( "command-build-turnedon", LobbySystem.getPrefix() ) );
            player.sendMessage( "build an" );
            player.setGameMode( GameMode.CREATIVE );
            player.getInventory().clear();
        }
        return false;
    }
    
}
