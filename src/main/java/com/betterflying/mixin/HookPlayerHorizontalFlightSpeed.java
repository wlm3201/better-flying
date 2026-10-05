package com.betterflying.mixin;

import com.betterflying.player.FlightHelper;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class HookPlayerHorizontalFlightSpeed {
	@Inject(method = "getFlyingSpeed", at = @At("RETURN"), cancellable = true)
	private void betterflying$applyHorizontalSpeedMultiplier(CallbackInfoReturnable<Float> info) {
		Object self = this;

		if (self instanceof LocalPlayer player
			&& FlightHelper.isFlyingCreativeOrSpectator(player)
			&& !player.isPassenger()) {
			// Recomputing from the base flying speed replaces the vanilla 2x sprint boost,
			// so the configured multiplier stays absolute.
			info.setReturnValue(player.getAbilities().getFlyingSpeed() * FlightHelper.getHorizontalSpeedMultiplier(player));
		}
	}
}
