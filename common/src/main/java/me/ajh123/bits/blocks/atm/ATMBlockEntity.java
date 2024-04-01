package me.ajh123.bits.blocks.atm;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.VersionedInventoryWrapper;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class ATMBlockEntity extends SmartBlockEntity implements SidedStorageBlockEntity {
	protected Storage<ItemVariant> itemCapability;
	protected ItemStackHandler inventory;

	public ATMBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);

		inventory = new ItemStackHandler(9);
		itemCapability = null;
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		if (!clientPacket) {
			inventory.deserializeNBT(tag.getCompound("inventory"));
		}
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		super.write(tag, clientPacket);

		if (!clientPacket) {
			tag.put("inventory", inventory.serializeNBT());
		}
	}

	@Nullable
	@Override
	public Storage<ItemVariant> getItemStorage(@Nullable Direction face) {
		initCapability();
		return itemCapability;
	}

	private void initCapability() {
		if (itemCapability != null)
			return;
		Storage<ItemVariant> combinedInvWrapper = new VersionedInventoryWrapper(new CombinedStorage<>(List.of(inventory)));
		itemCapability = combinedInvWrapper;
	}

	void onRemove() {
		itemCapability = null;
	}
}
