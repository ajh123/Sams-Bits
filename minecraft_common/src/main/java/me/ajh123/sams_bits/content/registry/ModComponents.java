package me.ajh123.sams_bits.content.registry;

import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.content.roads.LinkingComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    public static final ComponentType<LinkingComponent> LINKING_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(SamsBits.MOD_ID, "linking"),
            ComponentType.<LinkingComponent>builder().codec(LinkingComponent.CODEC).build()
    );

    public static void initialize() {
        //do nothing expect initialize static
    }
}
