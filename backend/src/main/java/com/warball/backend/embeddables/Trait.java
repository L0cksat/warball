package com.warball.backend.embeddables;

import java.util.Map;

import lombok.Data;

@Data
public class Trait {
    private String traitId;
    private String name;
    private String type;
    private Map<String, Double> modifiers;
}
