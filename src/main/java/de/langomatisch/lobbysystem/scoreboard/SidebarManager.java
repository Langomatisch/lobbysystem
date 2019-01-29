package de.langomatisch.lobbysystem.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class SidebarManager {
    
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final ArrayList<String> content = new ArrayList();
    private String title;
    
    public SidebarManager( Scoreboard scoreboard, Player player ) {
        if ( scoreboard.getObjective( player.getName() ) == null )
            this.objective = scoreboard.registerNewObjective( player.getName(), "dummy" );
        else
            objective = scoreboard.getObjective( player.getName() );
        this.objective.setDisplaySlot( DisplaySlot.SIDEBAR );
        this.scoreboard = scoreboard;
    }
    
    public SidebarManager( Scoreboard scoreboard, String player ) {
        this.objective = scoreboard.registerNewObjective( player, "dummy" );
        this.objective.setDisplaySlot( DisplaySlot.SIDEBAR );
        this.scoreboard = scoreboard;
    }
    
    
    public void accept( SidebarBuilder builder ) {
        if ( builder.title != null ) {
            this.title = builder.title;
        }
        
        if ( this.title == null ) {
            throw new InternalError( "Title cannot be null" );
        } else {
            this.objective.setDisplayName( this.title );
            ArrayList<String> newEntries = new ArrayList();
            int i = 0;
            
            Iterator var4;
            String entry;
            for ( var4 = builder.list.iterator(); var4.hasNext(); ++i ) {
                entry = (String) var4.next();
                newEntries.add( this.get( i ) + entry );
            }
            
            var4 = ( new ArrayList( this.content ) ).iterator();
            
            while ( var4.hasNext() ) {
                entry = (String) var4.next();
                if ( !newEntries.contains( entry ) ) {
                    this.scoreboard.resetScores( entry );
                    this.content.remove( entry );
                }
            }
            
            var4 = newEntries.iterator();
            
            while ( var4.hasNext() ) {
                entry = (String) var4.next();
                if ( !this.content.contains( entry ) ) {
                    this.objective.getScore( entry ).setScore( 0 );
                    this.content.add( entry );
                }
            }
            
        }
    }
    
    private String get( int i ) {
        String s = ( new DecimalFormat( "00" ) ).format( (long) i );
        String a = s.substring( 0, 1 );
        String b = s.substring( 1, 2 );
        return "§" + a + "§" + b + "§f";
    }
    
}
