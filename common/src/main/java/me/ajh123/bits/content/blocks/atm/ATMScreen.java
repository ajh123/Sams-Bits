package me.ajh123.bits.content.blocks.atm;

import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.widget.IconButton;
import me.ajh123.bits.utilities.registration.ModBlocks;
import me.ajh123.bits.utilities.registration.ModGUIS;
import me.ajh123.bits.utilities.util.Lang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ATMScreen extends AbstractSimiScreen {
	private final ItemStack renderedItem = ModBlocks.ATM_BLOCK.asStack();
	private final ModGUIS background = ModGUIS.ATM;

	public ATMScreen() {
		super(Lang.translate("gui.atm_screen.title").component());
	}

	@Override
	protected void init() {
		setWindowSize(background.width, background.height);
		setWindowOffset(-20, 0);
		super.init();

		int x = guiLeft;
		int y = guiTop;

		IconButton confirmButton = new IconButton(x + background.width - 33, y + background.height - 24, AllIcons.I_CONFIRM);
		confirmButton.withCallback(this::onClose);
		addRenderableWidget(confirmButton);
	}

	@Override
	protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		int x = guiLeft;
		int y = guiTop;

		background.render(graphics, x, y);

		labelCentered(graphics, x, y, title, 0x592424);
		labelCentered(graphics, x, y + 16, Component.literal("aaaaaa"), 0xFFFFFF);
		renderAdditional(graphics, mouseX, mouseY, partialTicks, x, y, background);
	}

	private void renderAdditional(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, int guiLeft, int guiTop,
								  ModGUIS background) {
		GuiGameElement.of(renderedItem).<GuiGameElement
						.GuiRenderBuilder>at(guiLeft + background.width + 6, guiTop + background.height - 56, 100)
				.scale(5)
				.render(graphics);
	}

	private void labelCentered(GuiGraphics graphics, int x, int y, Component text, int color) {
		graphics.drawString(font, text, x + (background.width - 8) / 2 - font.width(text) / 2, y + 4, color, false);
	}
}
