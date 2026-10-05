package com.betterflying.input;

import com.mojang.blaze3d.platform.InputConstants;

/**
 * 26.3 把 {@code InputConstants.Type.KEYSYM} 改名为 {@code KEYBOARD}，这里覆盖 shared 里的同名文件。
 */
public final class KeyInputType {
	public static final InputConstants.Type KEYBOARD = InputConstants.Type.KEYBOARD;

	private KeyInputType() {}
}
