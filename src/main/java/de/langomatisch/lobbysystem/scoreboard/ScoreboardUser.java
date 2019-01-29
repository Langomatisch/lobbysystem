package de.langomatisch.lobbysystem.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.DecimalFormat;
import java.util.Iterator;

public class ScoreboardUser {

    protected final Player player;
    protected final SidebarManager sidebarManager;
    private final Scoreboard scoreboard;
    private String prefix = "";
    private String suffix = "";
    private String playerName;
    private int order;

    protected ScoreboardUser( Player player ) {
        this.player = player;
        this.playerName = player.getName();
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.order = 0;
        this.registerTeam( this ).addPlayer( player );
        this.player.setScoreboard( this.scoreboard );
        this.sidebarManager = new SidebarManager( this.scoreboard, player );
        this.register();
        for ( ScoreboardUser user : SmartScoreboard.users ) {
            Team otherTeam = user.registerTeam( this );
            otherTeam.setPrefix( user.prefix );
            otherTeam.setSuffix( user.suffix );
            otherTeam.addEntry( user.playerName );
        }
    }

    protected ScoreboardUser( String player ) {
        this.playerName = player;
        this.player = Bukkit.getPlayer( playerName );
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.order = 0;
        this.registerTeam( this ).addEntry( player );
        if ( player != null ) this.player.setScoreboard( this.scoreboard );
        this.sidebarManager = new SidebarManager( this.scoreboard, player );
        this.register();
        for ( ScoreboardUser user : SmartScoreboard.users ) {
            Team otherTeam = user.registerTeam( this );
            otherTeam.setPrefix( user.prefix );
            otherTeam.setSuffix( user.suffix );
            otherTeam.addPlayer( user.player );
        }
    }

    private void register( ) {
        for ( ScoreboardUser user : SmartScoreboard.users ) {
            Team team = registerTeam( user );
            team.addEntry( this.playerName );
            team.setPrefix( this.prefix );
            team.setSuffix( this.suffix );
        }
    }

    protected String getTeamName( ) {
        String s = ( new DecimalFormat( "000" ) ).format( (long) this.order ) + playerName;
        if ( s.length() > 16 ) {
            s = s.substring( 0, 16 );
        }
        return s;
    }

    public ScoreboardUser setPrefix( String prefix ) {
        if ( prefix.length() > 16 ) {
            throw new InternalError( "prefix is too big: " + prefix.length() );
        } else if ( !this.prefix.equals( prefix ) ) {
            this.prefix = prefix;
            for ( ScoreboardUser user : SmartScoreboard.users ) {
                Team team = this.getTeam( user );
                team.setPrefix( prefix );
            }
        }
        return this;
    }

    public ScoreboardUser setSuffix( String suffix ) {
        if ( suffix.length() > 16 ) {
            throw new InternalError( "suffix is too big: " + suffix.length() );
        } else if ( !this.suffix.equals( suffix ) ) {
            this.suffix = suffix;
            Team team;
            for ( Iterator var2 = SmartScoreboard.users.iterator(); var2.hasNext(); team.setSuffix( suffix ) ) {
                ScoreboardUser user = (ScoreboardUser) var2.next();
                team = this.getTeam( user );
                if ( team == null ) {
                    team = this.registerTeam( user );
                }
            }
        }
        return this;
    }

    public ScoreboardUser setOrder( int order ) {
        if ( this.order != order ) {
            if ( order >= 0 && order <= 99 ) {
                for ( ScoreboardUser user : SmartScoreboard.users )
                    this.getTeam( user ).unregister();
                this.order = order;
                this.register();
            } else throw new InternalError( "Scoreboard order must be between 0 and 99!" );
        }
        return this;
    }

    private SidebarManager getTablistManager( ) {
        return this.sidebarManager;
    }

    protected Team getTeam( ScoreboardUser user ) {
        return user.scoreboard.getTeam( this.getTeamName() );
    }

    private Team registerTeam( ScoreboardUser user ) {
        return user.scoreboard.registerNewTeam( this.getTeamName() );
    }

    public void checkScoreboard( ) {
        if ( this.player.getScoreboard() != this.scoreboard ) this.player.setScoreboard( this.scoreboard );
    }

    public Player getPlayer( ) {
        return this.player;
    }

    public String getPlayerName( ) {
        return playerName;
    }

    public Scoreboard getScoreboard( ) {
        return scoreboard;
    }

}
