package de.langomatisch.lobbysystem.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Rank {
    
    private final String permission, name, prefix, suffix, color;
    private final int order;
    
}
