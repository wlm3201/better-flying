package com.betterflying.player;

import com.betterflying.BetterFlying;
import com.betterflying.config.BetterFlyingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public final class FlightHelper {
	private FlightHelper() {}

	public static boolean isFlyingCreativeOrSpectator(LocalPlayer player) {
		return player.getAbilities().flying && (player.isCreative() || player.isSpectator());
	}

	static boolean shouldFlyOnGround(LocalPlayer player) {
		return BetterFlying.getConfig().flyOnGroundInCreative && player.isCreative() && player.getAbilities().flying;
	}

	private static boolean isSprintKeyDown() {
		return Minecraft.getInstance().options.keySprint.isDown();
	}

	public static float getHorizontalSpeedMultiplier(LocalPlayer player) {
		BetterFlyingConfig config = BetterFlying.getConfig();

		if (player.isCreative()) {
			return isSprintKeyDown() ? config.flightHorizontalSpeedMpCreativeSprinting : config.flightHorizontalSpeedMpCreativeDefault;
		}
		else if (player.isSpectator()) {
			return isSprintKeyDown() ? config.flightHorizontalSpeedMpSpectatorSprinting : config.flightHorizontalSpeedMpSpectatorDefault;
		}
		else {
			return 1F;
		}
	}

	public static float getVerticalSpeedMultiplier(LocalPlayer player) {
		BetterFlyingConfig config = BetterFlying.getConfig();

		if (player.isCreative()) {
			return isSprintKeyDown() ? config.flightVerticalSpeedMpCreativeSprinting : config.flightVerticalSpeedMpCreativeDefault;
		}
		else if (player.isSpectator()) {
			return isSprintKeyDown() ? config.flightVerticalSpeedMpSpectatorSprinting : config.flightVerticalSpeedMpSpectatorDefault;
		}
		else {
			return 1F;
		}
	}
}
