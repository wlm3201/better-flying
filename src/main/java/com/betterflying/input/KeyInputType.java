package com.betterflying.input;

import com.mojang.blaze3d.platform.InputConstants;

/**
 * 键盘按键对应的输入类型，各版本名称不同：26.1/26.2 是 {@code KEYSYM}，26.3 起改名为 {@code KEYBOARD}。
 * 需要在 versions/&lt;版本&gt;/src/main/java 下用同路径文件覆盖。
 */
public final class KeyInputType {
	public static final InputConstants.Type KEYBOARD = InputConstants.Type.KEYSYM;

	private KeyInputType() {}
}
