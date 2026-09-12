package com.betterflying.input;

import net.minecraft.client.Minecraft;

public enum ModifierKey {
	CONTROL {
		@Override
		public boolean isPressed() {
			return Minecraft.getInstance().hasControlDown();
		}
	},

	SHIFT {
		@Override
		public boolean isPressed() {
			return Minecraft.getInstance().hasShiftDown();
		}
	},

	ALT {
		@Override
		public boolean isPressed() {
			return Minecraft.getInstance().hasAltDown();
		}
	};

	public abstract boolean isPressed();

	public static ModifierKey byName(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}

		for (ModifierKey modifier : values()) {
			if (modifier.name().equalsIgnoreCase(name)) {
				return modifier;
			}
		}

		return null;
	}
}
