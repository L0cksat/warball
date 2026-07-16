package com.warball.backend.embeddables;

import lombok.Data;

@Data
public class PlayerAttributes {
    private MysticalAttributes mysticalAttributes;
    private PhysicalAttributes physicalAttributes;
    private TechnicalAttributes technicalAttributes;
    private VibeAttributes vibeAttributes;
}
