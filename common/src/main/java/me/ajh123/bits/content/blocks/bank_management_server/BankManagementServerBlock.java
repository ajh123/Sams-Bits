package me.ajh123.bits.content.blocks.bank_management_server;

import dan200.computercraft.shared.computer.blocks.AbstractComputerBlockEntity;
import dan200.computercraft.shared.computer.blocks.ComputerBlock;
import dan200.computercraft.shared.computer.items.ComputerItem;
import me.ajh123.bits.utilities.registration.ModBlocks;
import me.ajh123.bits.utilities.registration.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;


import static me.ajh123.bits.SamsBits.modLoc;

public class BankManagementServerBlock extends ComputerBlock<BankManagementServerBlockEntity> {
	private static final ResourceLocation DROP = modLoc("bank_management_server");

	public BankManagementServerBlock(Properties properties) {
		super(properties, REGISTRY_ENTRY);
	}

	public static final Registration.RegistryEntryImpl<BlockEntityType<BankManagementServerBlockEntity>> REGISTRY_ENTRY = new Registration.RegistryEntryImpl<>(
			modLoc("bank_management_server"),
			ModBlocks.BANK_MANAGEMENT_SERVER_BLOCK_ENTITY
	);

	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		// We need to completely override this method, since it uses an incorrect `DROP`.
		// So, we need to manually include the implementation from `Block` so we can bypass
		// the one inside `AbstractComputerBlock`

		this.spawnDestroyParticles(world, player, pos, state);
		if (state.is(BlockTags.GUARDED_BY_PIGLINS)) {
			PiglinAi.angerNearbyPiglins(player, false);
		}

		world.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(player, state));

		// "Borrow" implementation from `AbstractComputerBlock`
		if (!(world instanceof ServerLevel serverWorld)) return;

		// We drop the item here instead of doing it in the harvest method, as we should
		// drop computers for creative players too.

		var tile = world.getBlockEntity(pos);
		if (tile instanceof BankManagementServerBlockEntity computer) {
			var context = new LootParams.Builder(serverWorld)
					.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
					.withParameter(LootContextParams.TOOL, player.getMainHandItem())
					.withParameter(LootContextParams.THIS_ENTITY, player)
					.withParameter(LootContextParams.BLOCK_ENTITY, tile)
					.withDynamicDrop(DROP, out -> out.accept(getItem(computer))); // make `DROP` use our ID.
			for (var item : state.getDrops(context)) {
				popResource(world, pos, item);
			}

			state.spawnAfterBreak(serverWorld, pos, player.getMainHandItem(), true);
		}
	}

	@Override
	protected ItemStack getItem(AbstractComputerBlockEntity tile) {
		if (!(tile instanceof BankManagementServerBlockEntity computer)) return ItemStack.EMPTY;
		if (!(asItem() instanceof ComputerItem item)) return ItemStack.EMPTY;

		return item.create(computer.getComputerID(), computer.getLabel());
	}
}
