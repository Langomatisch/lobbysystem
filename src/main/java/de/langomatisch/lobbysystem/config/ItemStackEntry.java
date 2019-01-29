package de.langomatisch.lobbysystem.config;

import de.langomatisch.lobbysystem.util.ItemBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
public class ItemStackEntry {
    
    private int id;
    private Material material = Material.DIRT;
    private String displayName = "", permission = "";
    private List<String> lore = new ArrayList<>();
    private int amount = 1;
    private int data = 0;
    private String command = "";
    private Map<String, Object> enchantments = new HashMap<>();
    
    public ItemStackEntry( ConfigurationSection section ) {
        section.getValues( true ).forEach( ( s, o ) -> {
            switch ( s ) {
                case "material":
                    material = Material.valueOf( o.toString() );
                    break;
                case "displayName":
                    displayName = ChatColor.translateAlternateColorCodes( '&', o.toString() );
                    break;
                case "lore":
                    lore = (List<String>) o;
                    break;
                case "amount":
                    amount = (int) o;
                    break;
                case "data":
                    data = (int) o;
                    break;
                case "enchantments":
                    enchantments = ( (MemorySection) o ).getValues( false );
                    break;
                case "command":
                    command = (String) o;
                    break;
                case "permission":
                    permission = (String) o;
                    break;
            }
        } );
    }
    
    public ItemStack toItemStack() {
        final ItemBuilder itemBuilder = new ItemBuilder( material ).setDisplayName( displayName )
                .setAmount( amount ).setLore( lore.toArray( new String[ 0 ] ) ).setSubID( data );
        enchantments.forEach( ( s, o ) -> {
            itemBuilder.enchant( Enchantment.getByName( s ), (int) o );
        } );
        itemBuilder.setNBT( "item", String.valueOf( id ) );
        itemBuilder.addItemFlag( ItemFlag.HIDE_ENCHANTS );
        return itemBuilder.build();
    }
    
}
