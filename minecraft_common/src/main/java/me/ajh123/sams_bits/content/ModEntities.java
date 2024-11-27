package me.ajh123.sams_bits.content;

import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.content.entities.VehicleEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<VehicleEntity> VEHICLE_ENTITY = EntityType.Builder.create(VehicleEntity::new, SpawnGroup.MISC).build("vehicle");


    public static void initialize() {
        register(VEHICLE_ENTITY, "vehicle");
    }

    public static EntityType<?> register(EntityType<?> item, String id) {
        Identifier itemID = Identifier.of(SamsBits.MOD_ID, id);
        return Registry.register(Registries.ENTITY_TYPE, itemID, item);
    }
}
