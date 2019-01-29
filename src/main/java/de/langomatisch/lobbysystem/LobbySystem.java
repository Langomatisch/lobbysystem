package de.langomatisch.lobbysystem;

import com.google.common.reflect.ClassPath;
import de.langomatisch.lobbysystem.command.BuildCommand;
import de.langomatisch.lobbysystem.command.FlyCommand;
import de.langomatisch.lobbysystem.command.WarpsCommand;
import de.langomatisch.lobbysystem.config.ItemStackEntry;
import de.langomatisch.lobbysystem.inventory.WarpInventory;
import de.langomatisch.lobbysystem.listener.*;
import de.langomatisch.lobbysystem.util.Rank;
import de.langomatisch.lobbysystem.util.Warp;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class LobbySystem extends JavaPlugin {
    
    @Getter
    private static LobbySystem instance;
    private Economy economy;
    private Map<Integer, ItemStackEntry> inventory = new HashMap<>();
    private List<String> scoreboardContent = new ArrayList<>();
    private List<Rank> ranks = new ArrayList<>();
    private List<Warp> warps = new ArrayList<>();
    private WarpInventory warpInventory;
    
    private final boolean debug = true;
    
    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
        loadStuff();
        loadRanks();
        if ( !loadEconomy() ) getLogger().warning( "Coins could not be loaded ( missing Vault? )" );
        loadScoreboard();
        warpInventory = new WarpInventory();
    }
    
    private void loadStuff() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents( new BlockBreakListener(), this );
        pluginManager.registerEvents( new BlockPlaceListener(), this );
        pluginManager.registerEvents( new EntityDamageListener(), this );
        pluginManager.registerEvents( new InventoryClickListener(), this );
        pluginManager.registerEvents( new PlayerInteractListener(), this );
        pluginManager.registerEvents( new PlayerJoinListener(), this );
        pluginManager.registerEvents( new PlayerQuitListener(), this );
        getCommand( "build" ).setExecutor( new BuildCommand() );
        getCommand( "fly" ).setExecutor( new FlyCommand() );
        getCommand( "warps" ).setExecutor( new WarpsCommand() );
    }
    
    private void loadConfig() {
        if ( !getDataFolder().exists() ) {
            saveResource( "config.yml", false );
            saveResource( "items.yml", false );
            saveResource( "ranks.yml", false );
            saveResource( "warps.yml", false );
        }
        FileConfiguration itemConfig = YamlConfiguration.loadConfiguration( new File( getDataFolder(), "items.yml" ) );
        final Map<String, Object> values = itemConfig.getValues( false );
        values.forEach( ( s, o ) -> {
            try {
                int i = Integer.parseInt( s );
                final ItemStackEntry itemStackEntry = new ItemStackEntry( itemConfig.getConfigurationSection( s ) );
                itemStackEntry.setId( i );
                getLogger().info( "loaded inventory item: " + itemStackEntry.getDisplayName() );
                inventory.put( i, itemStackEntry );
            } catch ( NumberFormatException ignored ) {
            }
        } );
    }
    
    private void loadRanks() {
        FileConfiguration rankConfig = YamlConfiguration.loadConfiguration( new File( getDataFolder(), "ranks.yml" ) );
        rankConfig.getValues( false ).forEach( ( permission, o ) -> {
            MemorySection map = (MemorySection) o;
            final Rank rank = new Rank(
                    map.getString( "Permission" ),
                    map.getString( "Name" ),
                    map.getString( "Prefix" ),
                    map.getString( "Suffix" ),
                    map.getString( "Color" ),
                    map.getInt( "Order" )
            );
            getLogger().info( permission );
            getLogger().info( "loaded rank " + rank.getName() );
            ranks.add( rank );
        } );
    }
    
    private void loadScoreboard() {
        scoreboardContent.addAll( getConfig().getStringList( "Scoreboard" ) );
    }
    
    private boolean loadEconomy() {
        if ( getServer().getPluginManager().getPlugin( "Vault" ) == null ) return false;
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration( Economy.class );
        if ( economyProvider == null ) return false;
        economy = economyProvider.getProvider();
        return economy != null;
    }
    
    public void setInventory( Player player ) {
        getInventory().forEach( ( integer, itemStackEntry ) -> {
            if ( player.hasPermission( itemStackEntry.getPermission() ) ) {
                player.getInventory().setItem( integer, itemStackEntry.toItemStack() );
            }
        } );
    }
    
}
