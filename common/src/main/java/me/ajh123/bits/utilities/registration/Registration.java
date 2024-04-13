package me.ajh123.bits.utilities.registration;

import com.simibubi.create.foundation.data.CreateRegistrate;
import dan200.computercraft.shared.platform.RegistryEntry;
import me.ajh123.bits.SamsBits;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class Registration {
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(SamsBits.MOD_ID);

	public static void init() {
		REGISTRATE.defaultCreativeTab("main_group").register();
		ModBlocks.init();
		ModItems.init();
	}

	public static final class RegistryEntryImpl<T> implements RegistryEntry<T> {
		private final ResourceLocation id;
		private final Supplier<T> supplier;
		private @Nullable T instance;

		public RegistryEntryImpl(ResourceLocation id, Supplier<T> supplier) {
			this.id = id;
			this.supplier = supplier;
		}

		public void register() {
			instance = supplier.get();
		}

		@Override
		public ResourceLocation id() {
			return id;
		}

		@Override
		public T get() {
			if (instance == null) throw new IllegalStateException(id + " has not been constructed yet");
			return instance;
		}
	}
}
