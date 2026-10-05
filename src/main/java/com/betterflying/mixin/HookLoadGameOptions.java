package com.betterflying.mixin;

import com.betterflying.input.Keybinds;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(Options.class)
public class HookLoadGameOptions {
	@Shadow @Mutable @Final private KeyMapping[] keyMappings;

	private boolean betterflying$hasLoaded = false;

	@Inject(method = "load", at = @At("HEAD"))
	private void addModKeyMappings(CallbackInfo info) {
		if (betterflying$hasLoaded) {
			return;
		}

		betterflying$hasLoaded = true;

		int oldLength = keyMappings.length;
		keyMappings = Arrays.copyOf(keyMappings, oldLength + Keybinds.ALL.length);
		System.arraycopy(Keybinds.ALL, 0, keyMappings, oldLength, Keybinds.ALL.length);
	}
}
