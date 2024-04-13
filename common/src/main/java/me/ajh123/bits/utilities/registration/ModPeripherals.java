package me.ajh123.bits.utilities.registration;

import dan200.computercraft.api.peripheral.IPeripheral;
import me.ajh123.bits.content.blocks.bank_management_server.BankManagementServerBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ModPeripherals {
	private static final Map<BlockEntityType<? extends BlockEntity>, BiFunction<? super BlockEntity, Direction, IPeripheral>> PERIPHERALS = new HashMap<>();

	static {
		registerForBlockEntity((b, d) -> ((BankManagementServerBlockEntity) b).peripheral(), ModBlocks.BANK_MANAGEMENT_SERVER_BLOCK_ENTITY.get());
	}

	private static void registerForBlockEntity(BiFunction<? super BlockEntity, Direction, IPeripheral> provider, BlockEntityType<? extends BlockEntity> blockEntityType ) {
		PERIPHERALS.put(blockEntityType, provider);
	}

	public static Map<BlockEntityType<? extends BlockEntity>, BiFunction<? super BlockEntity, Direction, IPeripheral>> getPeripherals() {
		return Collections.unmodifiableMap(PERIPHERALS);
	}
}
