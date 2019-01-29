package de.langomatisch.lobbysystem.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class LocationEntry {
    
    private String world;
    private double x, y, z;
    
}
