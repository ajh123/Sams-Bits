package me.ajh123.sams_bits.content.vehicles;

import eu.pb4.polymer.core.api.item.PolymerItem;
import me.ajh123.sams_bits.content.ModEntities;
import me.ajh123.sams_bits.utils.HiddenItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Spawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


public class VehicleEntitySpawner extends Item implements PolymerItem, HiddenItem {
    private final EntityType<?> entityType = ModEntities.VEHICLE_ENTITY;

    public VehicleEntitySpawner(Settings settings) {
        super(settings);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.IRON_GOLEM_SPAWN_EGG;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);
            BlockEntity var8 = world.getBlockEntity(blockPos);
            if (var8 instanceof Spawner spawner) {
                spawner.setEntityType(entityType, world.getRandom());
                world.updateListeners(blockPos, blockState, blockState, 3);
                world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                itemStack.decrement(1);
            } else {
                BlockPos blockPos2;
                if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                    blockPos2 = blockPos;
                } else {
                    blockPos2 = blockPos.offset(direction);
                }

                if (entityType.spawnFromItemStack((ServerWorld)world, itemStack, context.getPlayer(), blockPos2, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP) != null) {
                    itemStack.decrement(1);
                    world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
                }

            }
            return ActionResult.CONSUME;
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        } else if (!(world instanceof ServerWorld)) {
            return TypedActionResult.success(itemStack);
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
                return TypedActionResult.pass(itemStack);
            } else if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {
                Entity entity = entityType.spawnFromItemStack((ServerWorld)world, itemStack, user, blockPos, SpawnReason.SPAWN_EGG, false, false);
                if (entity == null) {
                    return TypedActionResult.pass(itemStack);
                } else {
                    itemStack.decrementUnlessCreative(1, user);
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    world.emitGameEvent(user, GameEvent.ENTITY_PLACE, entity.getPos());
                    return TypedActionResult.consume(itemStack);
                }
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }
    }
}
