package com.betterflying.compat.modmenu;

import com.betterflying.compat.yacl.BetterFlyingConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;

public class ModMenuIntegration implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		if (!FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
			// YACL is optional, fall back to Mod Menu's "no config screen" factory.
			return ModMenuApi.super.getModConfigScreenFactory();
		}

		ConfigScreenFactory<Screen> factory = BetterFlyingConfigScreen::create;
		return factory;
	}
}
