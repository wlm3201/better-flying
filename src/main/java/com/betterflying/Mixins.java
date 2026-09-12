package com.betterflying;

import com.betterflying.mixin.AccessPlayerFields;
import net.minecraft.world.entity.player.Player;

public final class Mixins {
	private Mixins() {}

	public static AccessPlayerFields playerFields(Player player) {
		return (AccessPlayerFields) player;
	}
}
