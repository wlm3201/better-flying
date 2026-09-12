package com.betterflying.input;

import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class KeyBindingWithModifier extends KeyMapping {
	public static final Category CATEGORY = Category.register(Identifier.fromNamespaceAndPath("betterflying", "all"));

	private ModifierKey modifier = null;

	public KeyBindingWithModifier(String translationKey) {
		super(translationKey, Type.KEYSYM, -1, CATEGORY);
	}

	public void setModifier(ModifierKey modifier) {
		this.modifier = modifier;
	}

	public ModifierKey getModifier() {
		return modifier;
	}

	@Override
	public boolean isDown() {
		return super.isDown() && (modifier == null || modifier.isPressed());
	}

	@Override
	public boolean consumeClick() {
		return super.consumeClick() && (modifier == null || modifier.isPressed());
	}
}
