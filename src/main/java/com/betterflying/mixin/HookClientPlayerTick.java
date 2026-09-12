package com.betterflying.mixin;

import com.betterflying.player.PlayerTicker;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class HookClientPlayerTick {
	@Inject(method = "aiStep()V", at = @At("HEAD"))
	private void betterflying$atHead(CallbackInfo info) {
		LocalPlayer player = (LocalPlayer)(Object) this;
		PlayerTicker.get(player).atHead(player);
	}

	@Inject(
		method = "aiStep()V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;aiStep()V", ordinal = 0, shift = At.Shift.AFTER)
	)
	private void betterflying$afterSuperCall(CallbackInfo info) {
		LocalPlayer player = (LocalPlayer)(Object) this;
		PlayerTicker.get(player).afterSuperCall(player);
	}
}
