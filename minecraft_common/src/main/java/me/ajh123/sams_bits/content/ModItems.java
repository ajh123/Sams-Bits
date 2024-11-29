package me.ajh123.sams_bits.content;

import java.util.Map.Entry;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.content.other.TrainSchedule;
import me.ajh123.sams_bits.content.roads.RoadConnectorItem;
import me.ajh123.sams_bits.content.vehicles.VehicleEntitySpawner;
import me.ajh123.sams_bits.utils.HiddenItem;
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
    public static TrainSchedule TRAIN_SCHEDULE = register("train_schedule", new TrainSchedule(new Item.Settings()));
    public static VehicleEntitySpawner VEHICLE_ENTITY_SPAWNER = register("vehicle", new VehicleEntitySpawner(new Item.Settings()));
    public static RoadConnectorItem ROAD_CONNECTOR = register("road_connector", new RoadConnectorItem(new Item.Settings()));

    public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(SamsBits.MOD_ID, "item_group"));
    public static final ItemGroup ITEM_GROUP = PolymerItemGroupUtils.builder()
            .icon(() -> new ItemStack(ModItems.TRAIN_SCHEDULE))
            .displayName(Text.translatable("itemGroup.sams_bits"))
            .build();


    public static void initialize() {
        PolymerItemGroupUtils.registerPolymerItemGroup(ITEM_GROUP_KEY, ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(itemGroup -> {
            for (Entry<RegistryKey<Item>, Item> item : Registries.ITEM.getEntrySet()) {
                if (!(item.getValue() instanceof HiddenItem)) {
                    if (item.getKey().getValue().getNamespace().equals(SamsBits.MOD_ID)) {
                        itemGroup.add(item.getValue());
                    }
                }
            } 
        });
    }

    public static <T extends Item> T register(String id, T item) {
        Identifier itemID = Identifier.of(SamsBits.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }
}
