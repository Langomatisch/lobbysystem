package de.langomatisch.lobbysystem.listener;

import de.langomatisch.lobbysystem.LobbySystem;
import de.langomatisch.lobbysystem.scoreboard.ScoreboardUser;
import de.langomatisch.lobbysystem.scoreboard.SidebarBuilder;
import de.langomatisch.lobbysystem.scoreboard.SidebarManager;
import de.langomatisch.lobbysystem.scoreboard.SmartScoreboard;
import de.langomatisch.lobbysystem.util.Rank;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    
    @EventHandler
    public void onJoin( PlayerJoinEvent event ) {
        final Player player = event.getPlayer();
        player.getInventory().clear();
        Rank rank = LobbySystem.getInstance().getRanks().stream().filter( ( o ) -> {
            if ( o.getPermission().isEmpty() ) return true;
            return player.hasPermission( o.getPermission() );
        } ).findFirst().get();
        final FileConfiguration config = LobbySystem.getInstance().getConfig();
        SidebarBuilder sidebarBuilder = new SidebarBuilder();
        sidebarBuilder.setTitle( ChatColor.translateAlternateColorCodes( '&', replaceStuff( player, config.getString( "Scoreboard-Title" ), rank ) ) );
        ScoreboardUser user = SmartScoreboard.getUser( player );
        SidebarManager sidebarManager = new SidebarManager( user.getScoreboard(), user.getPlayer() );
        user.setPrefix( ChatColor.translateAlternateColorCodes( '&', rank.getPrefix() ) );
        user.setSuffix( ChatColor.translateAlternateColorCodes( '&', rank.getSuffix() ) );
        user.setOrder( rank.getOrder() );
        LobbySystem.getInstance().getScoreboardContent().forEach( ( s ) -> sidebarBuilder.add( ChatColor.translateAlternateColorCodes( '&', replaceStuff( player, s, rank ) ) ) );
        sidebarManager.accept( sidebarBuilder );
        LobbySystem.getInstance().setInventory( player );
        player.setDisplayName( rank.getPrefix() + player.getName() );
        if ( config.getBoolean( "EnableTitle" ) ) {
            player.sendTitle( ChatColor.translateAlternateColorCodes( '&', replaceStuff( player, config.getString( "Title" ), rank ) ),
                    ChatColor.translateAlternateColorCodes( '&', replaceStuff( player, config.getString( "SubTitle" ), rank ) ) );
        }
    }
    
    private String replaceStuff( Player player, String line, Rank rank ) {
        return line.replace( "%player%", player.getDisplayName() ).replace( "%name%", player.getName() )
                .replace( "%rankColor%", rank.getColor() ).replace( "%rank%", rank.getName() )
                .replace( "%rankPrefix%", rank.getPrefix() ).replace( "%rankSuffix%", rank.getSuffix() );
    }
    
}
