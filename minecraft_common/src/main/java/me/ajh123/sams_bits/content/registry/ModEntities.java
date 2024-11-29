package me.ajh123.sams_bits.content.registry;

import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.content.vehicles.VehicleEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<VehicleEntity> VEHICLE_ENTITY = register("vehicle", EntityType.Builder.create(VehicleEntity::new, SpawnGroup.MISC).build("vehicle"));

    public static void initialize() {
        //do nothing expect initialize static
    }

    public static <T extends EntityType<?>> T  register(String id,T entity) {
        Identifier itemID = Identifier.of(SamsBits.MOD_ID, id);
        return Registry.register(Registries.ENTITY_TYPE, itemID, entity);
    }
}
