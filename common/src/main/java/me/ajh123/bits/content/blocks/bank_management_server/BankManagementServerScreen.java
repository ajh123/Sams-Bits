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

		background.render(graphics, x, y);

		labelCentered(graphics, x, y, title, 0x592424);
		renderConsole(graphics, x+4, y+17);
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
