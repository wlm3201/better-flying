package com.betterflying.mixin;

import com.betterflying.player.PlayerTicker;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Abilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractClientPlayer.class)
public abstract class HookClientPlayerFOV {
	@Redirect(method = "getFieldOfViewModifier", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Abilities;getWalkingSpeed()F"))
	private float betterflying$overrideWalkingSpeed(Abilities abilities) {
		Object self = this;

		if (self instanceof LocalPlayer player && PlayerTicker.shouldResetFOV(player)) {
			return 0F;
		}

		return abilities.getWalkingSpeed();
	}
}
