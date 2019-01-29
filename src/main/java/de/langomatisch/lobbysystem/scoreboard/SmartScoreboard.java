package de.langomatisch.lobbysystem.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class SmartScoreboard {

    public static final ArrayList<ScoreboardUser> users = new ArrayList();

    public SmartScoreboard( ) {
    }

    public static ScoreboardUser getUser( Player player ) {
        return getUser( player.getName() );
    }

    public static ScoreboardUser getUser( String playerName ) {
        ScoreboardUser user;
        for ( ScoreboardUser scoreboardUser : users )
            if ( playerName.equals( scoreboardUser.getPlayerName() ) )
                return scoreboardUser;
        user = new ScoreboardUser( playerName );
        users.add( user );
        return user;
    }

    public static void removeUser( Player player ) {
        ScoreboardUser user = getUser( player );
        if ( user != null ) {
            for ( ScoreboardUser scoreboardUser : users ) {
                Team team = user.getTeam( scoreboardUser );
                if ( team != null ) {
                    team.unregister();
                }
            }
            users.remove( user );
        }
    }

    public static void removeUser( String player ) {
        ScoreboardUser user = getUser( player );
        if ( user != null ) {
            for ( ScoreboardUser scoreboardUser : users ) {
                Team team = scoreboardUser.getTeam( user );
                if ( team != null ) team.unregister();
            }
            users.remove( user );
        }
    }

}
