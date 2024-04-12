package me.ajh123.bits.content.blocks.bank_management_server;

import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import me.ajh123.bits.utilities.registration.ModGUIS;
import me.ajh123.bits.utilities.util.Lang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class BankManagementServerScreen extends AbstractSimiScreen {
	private final ModGUIS background = ModGUIS.TERMINAL;

	public BankManagementServerScreen()  {
		super(Lang.translate("gui.bank_management.title").component());
	}

	@Override
	protected void init() {
		setWindowSize(background.width, background.height);
		super.init();
	}

	@Override
	protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		int x = guiLeft;
		int y = guiTop;

		// Render border
		int borderSize = 4; // Adjust this value to change the border size
		int topBorderWidth = 6; // Adjust this value to change the width of the top border
		int topBorderHeight = 6; // Adjust this value to change the height of the top border
		int bottomBorderHeight = 4; // Adjust this value to change the height of the bottom border
		int leftBorderWidth = 4; // Adjust this value to change the width of the left border
		int rightBorderWidth = 4; // Adjust this value to change the width of the right border
		int textureSize = 16; // Assuming your border texture has a tile size of 16x16
		int textureWidth = textureSize - borderSize;
		int textureHeight = textureSize - borderSize;

		// Top border
		int topU = 0;
		int topV = 0;
		int topWidth = leftBorderWidth;
		int topHeight = topBorderHeight;
		graphics.blit(background.getLocation(), x, y, topU, topV, topWidth, topHeight); // Top-left corner
		topU += topWidth;
		topWidth = textureWidth - leftBorderWidth - rightBorderWidth;
		graphics.blit(background.getLocation(), x + leftBorderWidth, y, topU, topV, topWidth, topHeight); // Top center
		topU += topWidth;
		topWidth = rightBorderWidth;
		graphics.blit(background.getLocation(), x + background.width - rightBorderWidth, y, topU, topV, topWidth, topHeight); // Top-right corner

		// Bottom border
		int bottomU = 0;
		int bottomV = textureSize - borderSize - bottomBorderHeight;
		int bottomWidth = leftBorderWidth;
		int bottomHeight = bottomBorderHeight;
		graphics.blit(background.getLocation(), x, y + background.height - bottomBorderHeight, bottomU, bottomV, bottomWidth, bottomHeight); // Bottom-left corner
		bottomU += bottomWidth;
		bottomWidth = textureWidth - leftBorderWidth - rightBorderWidth;
		graphics.blit(background.getLocation(), x + leftBorderWidth, y + background.height - bottomBorderHeight, bottomU, bottomV, bottomWidth, bottomHeight); // Bottom center
		bottomU += bottomWidth;
		bottomWidth = rightBorderWidth;
		graphics.blit(background.getLocation(), x + background.width - rightBorderWidth, y + background.height - bottomBorderHeight, bottomU, bottomV, bottomWidth, bottomHeight); // Bottom-right corner

		// Left border
		int leftU = 0;
		int leftV = topBorderHeight;
		int leftWidth = leftBorderWidth;
		int leftHeight = textureHeight - topBorderHeight - bottomBorderHeight;
		for (int i = 0; i < background.height / textureSize; i++) {
			graphics.blit(background.getLocation(), x, y + i * textureSize + topBorderHeight, leftU, leftV, leftWidth, leftHeight);
		}

		// Right border
		int rightU = textureSize - borderSize - rightBorderWidth;
		int rightV = topBorderHeight;
		int rightWidth = rightBorderWidth;
		int rightHeight = textureHeight - topBorderHeight - bottomBorderHeight;
		for (int i = 0; i < background.height / textureSize; i++) {
			graphics.blit(background.getLocation(), x + background.width - rightBorderWidth, y + i * textureSize + topBorderHeight, rightU, rightV, rightWidth, rightHeight);
		}

		// Render title
		labelCentered(graphics, x, y, title, 0x592424);
	}


	private void labelCentered(GuiGraphics graphics, int x, int y, Component text, int color) {
		graphics.drawString(font, text, x + (background.width - 8) / 2 - font.width(text) / 2, y + 4, color, false);
	}
}
