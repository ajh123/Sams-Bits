package me.ajh123.bits.utilities.registration;

import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.element.ScreenElement;
import com.simibubi.create.foundation.utility.Color;

import me.ajh123.bits.SamsBits;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 Based of com.simibubi.create.foundation.gui.AllGuiTextures.
 Found at <a href="https://github.com/Fabricators-of-Create/Create/blob/c298d9521a6fb47532c0884a461d25a90217a140/src/main/java/com/simibubi/create/foundation/gui/AllGuiTextures.java">https://github.com/Fabricators-of-Create/Create/blob/c298d9521a6fb47532c0884a461d25a90217a140/src/main/java/com/simibubi/create/foundation/gui/AllGuiTextures.java</a>
 **/

public enum ModGUIS implements ScreenElement {
	ATM("atm", 254, 161),
	TERMINAL("terminal", 406, 292),

	;


	public final ResourceLocation location;
	public final int width;
	public final int height;
	public final int startX;
	public final int startY;

	ModGUIS(String location, int width, int height) {
		this(location, 0, 0, width, height);
	}


	ModGUIS(String location, int startX, int startY, int width, int height) {
		this(SamsBits.MOD_ID, location, startX, startY, width, height);
	}

	ModGUIS(String namespace, String location, int startX, int startY, int width, int height) {
		this.location = new ResourceLocation(namespace, "textures/gui/" + location + ".png");
		this.width = width;
		this.height = height;
		this.startX = startX;
		this.startY = startY;
	}

	@Environment(EnvType.CLIENT)
	public void bind() {
		RenderSystem.setShaderTexture(0, location);
	}

	@Environment(EnvType.CLIENT)
	public void render(GuiGraphics graphics, int x, int y) {
		graphics.blit(location, x, y, startX, startY, width, height);
	}

	@Environment(EnvType.CLIENT)
	public void render(GuiGraphics graphics, int x, int y, Color c) {
		bind();
		UIRenderHelper.drawColoredTexture(graphics, c, x, y, startX, startY, width, height);
	}
}
