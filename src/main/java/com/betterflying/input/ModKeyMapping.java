package com.betterflying.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class ModKeyMapping extends KeyMapping {
	public static final Category CATEGORY = Category.register(Identifier.fromNamespaceAndPath("betterflying", "all"));

	public ModKeyMapping(String translationKey) {
		// “未绑定”的键值各版本不同：26.1/26.2 是 -1，26.3 起是 0，必须用 InputConstants.UNKNOWN 取。
		// 写死 -1 时 26.3 会把它当成已绑定键，MouseHandler#grabMouse -> KeyMapping#setAll
		// 调用 InputConstants.isKeyDown(-1) 越界抛异常，鼠标抓不住、视角转不动。
		super(translationKey, KeyInputType.KEYBOARD, InputConstants.UNKNOWN.getValue(), CATEGORY);
	}

	/**
	 * 旧版写进 options.txt 的 {@code key.keyboard.-1} 读回来仍是非法值，这里兜底纠正为未绑定。
	 */
	@Override
	public void setKey(InputConstants.Key key) {
		super.setKey(key.getValue() < 0 ? InputConstants.UNKNOWN : key);
	}
}
