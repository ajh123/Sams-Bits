package me.ajh123.bits.forge;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import me.ajh123.bits.utilities.registration.ModPeripherals;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;

public class ForgePeripheralProvider implements IPeripheralProvider {
	@Override
	public LazyOptional<IPeripheral> getPeripheral(Level world, BlockPos pos, Direction side) {
		BlockEntity entity = world.getBlockEntity(pos);
		var peripherals = ModPeripherals.getPeripherals();

		if (entity != null) {
			var peripheral = peripherals.get(entity.getType());

			if (peripheral != null) {
				return LazyOptional.of(() -> peripheral.apply(entity, side));
			}
		}
		return LazyOptional.empty();
	}
}
