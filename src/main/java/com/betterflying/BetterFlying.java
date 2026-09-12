package com.betterflying;

import com.betterflying.config.BetterFlyingConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class BetterFlying implements ClientModInitializer {
	public static final String MOD_ID = "betterflying";

	private static BetterFlyingConfig config = null;

	@Override
	public void onInitializeClient() {
		getConfig();
	}

	public static BetterFlyingConfig getConfig() {
		if (config == null) {
			config = BetterFlyingConfig.load(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".json"));
			config.applyModifiers();
		}

		return config;
	}
}
