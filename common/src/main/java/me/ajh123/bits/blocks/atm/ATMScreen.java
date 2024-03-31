package me.ajh123.bits.blocks.atm;

import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import me.ajh123.bits.registration.ModBlocks;
import me.ajh123.bits.util.Lang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class ATMScreen extends AbstractSimiScreen {
	private final ItemStack renderedItem = ModBlocks.ATM_BLOCK.asStack();
	private final AllGuiTextures background = AllGuiTextures.SEQUENCER;

	public ATMScreen() {
		super(Lang.translate("gui.atm_screen.title").component());
	}

	@Override
	protected void init() {
		setWindowSize(background.width, background.height);
		setWindowOffset(-20, 0);
		super.init();
	}

	@Override
	protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		int x = guiLeft;
		int y = guiTop;

		background.render(graphics, x, y);

		graphics.drawString(font, title, x + (background.width - 8) / 2 - font.width(title) / 2, y + 4, 0x592424, false);
		renderAdditional(graphics, mouseX, mouseY, partialTicks, x, y, background);
	}

	private void renderAdditional(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, int guiLeft, int guiTop,
								  AllGuiTextures background) {
		GuiGameElement.of(renderedItem).<GuiGameElement
						.GuiRenderBuilder>at(guiLeft + background.width + 6, guiTop + background.height - 56, 100)
				.scale(5)
				.render(graphics);
	}
}
