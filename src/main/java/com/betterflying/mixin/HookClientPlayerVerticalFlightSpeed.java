package com.betterflying.mixin;

import com.betterflying.player.FlightHelper;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Abilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class HookClientPlayerVerticalFlightSpeed {
	@Redirect(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Abilities;getFlyingSpeed()F"))
	private float betterflying$modifyVerticalFlightSpeed(Abilities abilities) {
		LocalPlayer player = (LocalPlayer)(Object) this;
		return abilities.getFlyingSpeed() * FlightHelper.getVerticalSpeedMultiplier(player);
	}
}
