package me.ajh123.bits.foundation;

public class TextConsole {
	private ConsoleCharacter[][] screen;
	private int rows;
	private int cols;
	private int cursorRow;
	private int cursorCol;

	public TextConsole(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.screen = new ConsoleCharacter[rows][cols];
		clearScreen();
	}

	public void clearScreen() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				screen[i][j] = new ConsoleCharacter(' ', ConsoleColor.DEFAULT_FOREGROUND, ConsoleColor.DEFAULT_BACKGROUND);
			}
		}
		cursorRow = 0;
		cursorCol = 0;
	}

	public void print(String text, ConsoleColor foregroundColor, ConsoleColor backgroundColor) {
		for (char c : text.toCharArray()) {
			if (c == '\n') {
				cursorRow++;
				cursorCol = 0;
				if (cursorRow >= rows) {
					scrollUp();
					cursorRow = rows - 1;
				}
			} else if (cursorCol < cols) {
				screen[cursorRow][cursorCol] = new ConsoleCharacter(c, foregroundColor, backgroundColor);
				cursorCol++;
			}
		}
	}

	public ConsoleCharacter getCharacterAt(int row, int col) {
		if (isValidPosition(row, col)) {
			return screen[row][col];
		}
		return null;
	}

	public void setCursorPosition(int row, int col) {
		if (isValidPosition(row, col)) {
			cursorRow = row;
			cursorCol = col;
		}
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	private boolean isValidPosition(int row, int col) {
		return row >= 0 && row < rows && col >= 0 && col < cols;
	}

	private void scrollUp() {
		for (int i = 0; i < rows - 1; i++) {
			System.arraycopy(screen[i + 1], 0, screen[i], 0, cols);
		}
		for (int j = 0; j < cols; j++) {
			screen[rows - 1][j] = new ConsoleCharacter(' ', ConsoleColor.DEFAULT_FOREGROUND, ConsoleColor.DEFAULT_BACKGROUND);
		}
	}

	public static class ConsoleCharacter {
		private char character;
		private ConsoleColor foregroundColor;
		private ConsoleColor backgroundColor;

		public ConsoleCharacter(char character, ConsoleColor foregroundColor, ConsoleColor backgroundColor) {
			this.character = character;
			this.foregroundColor = foregroundColor;
			this.backgroundColor = backgroundColor;
		}

		public char getCharacter() {
			return character;
		}

		public ConsoleColor getForegroundColor() {
			return foregroundColor;
		}

		public ConsoleColor getBackgroundColor() {
			return backgroundColor;
		}
	}

	public enum ConsoleColor {
		RESET(0, 0, 0),
		BLACK(0, 0, 0),
		RED(170, 0, 0),
		GREEN(0, 170, 0),
		YELLOW(255, 255, 85),
		BLUE(85, 85, 255),
		MAGENTA(170, 0, 170),
		CYAN(0, 170, 170),
		WHITE(255, 255, 255);

		private final int red;
		private final int green;
		private final int blue;

		ConsoleColor(int red, int green, int blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		public int getRed() {
			return red;
		}

		public int getGreen() {
			return green;
		}

		public int getBlue() {
			return blue;
		}

		public static final ConsoleColor DEFAULT_FOREGROUND = ConsoleColor.WHITE;
		public static final ConsoleColor DEFAULT_BACKGROUND = ConsoleColor.BLACK;
	}
}