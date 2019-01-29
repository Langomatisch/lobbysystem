package de.langomatisch.lobbysystem.scoreboard;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SidebarBuilder {

    protected final ArrayList<String> list = new ArrayList();
    protected String title;

    public SidebarBuilder( ) {
    }

    public SidebarBuilder setTitle( String title ) {
        this.title = title;
        return this;
    }

    public SidebarBuilder add( String... lines ) {
        String[] var2 = lines;
        int var3 = lines.length;
        for ( int var4 = 0; var4 < var3; ++var4 ) {
            String s = var2[var4];
            this.list.add( s );
        }
        return this;
    }

    public void send( Player p ) {
        SmartScoreboard.getUser( p ).sidebarManager.accept( this );
    }
}
