package me.ajh123.bits.content.blocks.bank_management_server;

import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import me.ajh123.bits.foundation.TextConsole;
import me.ajh123.bits.utilities.registration.ModGUIS;
import me.ajh123.bits.utilities.util.Lang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class BankManagementServerScreen extends AbstractSimiScreen {
	private final ModGUIS background = ModGUIS.TERMINAL;
	private final TextConsole console;

	public BankManagementServerScreen()  {
		super(Lang.translate("gui.bank_management.title").component());
		this.console = new TextConsole(30, 24); // Example size for the console
	}

	@Override
	protected void init() {
		setWindowSize(background.width, background.height);
		super.init();
		this.console.print("AAAAA", TextConsole.ConsoleColor.DEFAULT_FOREGROUND, TextConsole.ConsoleColor.DEFAULT_BACKGROUND);
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

		// Render console
		renderConsole(graphics, x + leftBorderWidth, y + topBorderHeight + 13); // Adjust the position to accommodate the border
	}


	private void labelCentered(GuiGraphics graphics, int x, int y, Component text, int color) {
		graphics.drawString(font, text, x + (background.width - 8) / 2 - font.width(text) / 2, y + 4, color, false);
	}

	private void renderConsole(GuiGraphics graphics, int startX, int startY) {
		int charWidth = 8; // Example character width
		int charHeight = 8; // Example character height

		// Render each character of the console onto the screen
		for (int row = 0; row < console.getRows(); row++) {
			for (int col = 0; col < console.getCols(); col++) {
				TextConsole.ConsoleCharacter character = console.getCharacterAt(row, col);
				int foregroundColor = combineColors(character.getForegroundColor(), 0xFF);
				int backgroundColor = combineColors(character.getBackgroundColor(), 0xFF);


				// Draw background
				graphics.fill(startX + col * charWidth, startY + row * charHeight,
						startX + (col + 1) * charWidth, startY + (row + 1) * charHeight,
						backgroundColor);

				// Draw text
				graphics.drawString(font, Component.literal(String.valueOf(character.getCharacter())),
						startX + col * charWidth, startY + row * charHeight,
						foregroundColor, false);
			}
		}
	}

	private int combineColors(TextConsole.ConsoleColor color, int alpha) {
		return (alpha << 24) | (color.getRed() << 16) | (color.getGreen() << 8) | (color.getBlue());
	}
}
