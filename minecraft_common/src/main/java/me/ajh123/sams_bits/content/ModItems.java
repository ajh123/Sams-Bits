package me.ajh123.sams_bits.content;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.content.items.TrainSchedule;
import me.ajh123.sams_bits.content.items.VehicleEntitySpawner;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {
    public static TrainSchedule TRAIN_SCHEDULE = new TrainSchedule(new Item.Settings());
    public static VehicleEntitySpawner VEHICLE_ENTITY_SPAWNER = new VehicleEntitySpawner(new Item.Settings());

    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(SamsBits.MOD_ID, "item_group"));
    public static final ItemGroup ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.TRAIN_SCHEDULE))
            .displayName(Text.translatable("itemGroup.sams_bits"))
            .build();


    public static void initialize() {
        register(TRAIN_SCHEDULE, "train_schedule");
        register(VEHICLE_ENTITY_SPAWNER, "vehicle");

        PolymerItemGroupUtils.registerPolymerItemGroup(ITEM_GROUP_KEY, ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.TRAIN_SCHEDULE);
            itemGroup.add(ModItems.VEHICLE_ENTITY_SPAWNER);
        });
    }

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(SamsBits.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }
}
