package me.ajh123.sams_bits.content.items;

import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import me.ajh123.sams_bits.utils.UseOnEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;


public class TrainSchedule extends SimplePolymerItem implements UseOnEntityEvent {
    public TrainSchedule(Settings settings) {
        super(settings, Items.KNOWLEDGE_BOOK);
    }

    @Override
    public ActionResult useOnAnyEntity(World world, Entity target, PlayerEntity player) {
        if (!world.isClient) {
            if (target instanceof AbstractMinecartEntity) {
                TrainScheduleGUI gui = new TrainScheduleGUI((ServerPlayerEntity) player, player.getStackInHand(player.preferredHand));
                gui.open();

                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.SUCCESS;
    }
}
