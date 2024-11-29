package me.ajh123.sams_bits.content.vehicles;

import me.ajh123.sams_bits.content.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class VehicleEntity extends BoatEntity  {
    public VehicleEntity(EntityType<VehicleEntity> type, World world) {
        super(type, world);
    }

    @Override
    public float getStepHeight() {
        return 1.0F;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (this.canAddPassenger(player)) {
            if (this.canStartRiding(player)) {
                player.startRiding(this);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }

    @Override
    public Item asItem() {
        return ModItems.VEHICLE_ENTITY_SPAWNER;
    }
}
