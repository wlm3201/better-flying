package com.betterflying.player;

import com.betterflying.BetterFlying;
import com.betterflying.Mixins;
import com.betterflying.input.Keybinds;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Input;

import java.lang.ref.WeakReference;

public final class PlayerTicker {
	private static PlayerTicker ticker = new PlayerTicker(null);

	public static PlayerTicker get(LocalPlayer player) {
		if (ticker.ref.get() != player) {
			ticker = new PlayerTicker(player);
		}

		return ticker;
	}

	private final WeakReference<LocalPlayer> ref;

	private PlayerTicker(LocalPlayer player) {
		this.ref = new WeakReference<>(player);
	}

	private boolean wasSneakingBeforeTouchingGround = false;
	private boolean holdingSneakWhileTouchingGround = false;
	private int temporaryFlyOnGroundTimer = 0;

	public void atHead(LocalPlayer player) {
		if (FlightHelper.shouldFlyOnGround(player)) {
			player.setOnGround(false);
		}

		if (!BetterFlying.getConfig().doubleTapJumpToToggleFlight) {
			Mixins.playerFields(player).setJumpTriggerTime(0);
		}
	}

	public void afterSuperCall(LocalPlayer player) {
		if (FlightHelper.shouldFlyOnGround(player)) {
			boolean isSneaking = player.isShiftKeyDown();
			boolean isOnGround = player.onGround();

			if (!isSneaking) {
				wasSneakingBeforeTouchingGround = false;
			}
			else if (!isOnGround) {
				wasSneakingBeforeTouchingGround = true;
			}

			if (!isOnGround) {
				holdingSneakWhileTouchingGround = false;
			}
			else {
				boolean cancelLanding = true;

				if (!wasSneakingBeforeTouchingGround) {
					if (isSneaking) {
						holdingSneakWhileTouchingGround = true;
					}
					else if (holdingSneakWhileTouchingGround) {
						player.getAbilities().flying = false;
						player.onUpdateAbilities();
						cancelLanding = false;
					}
				}

				if (cancelLanding) {
					player.setOnGround(false);
				}
			}
		}
		else {
			wasSneakingBeforeTouchingGround = false;
			holdingSneakWhileTouchingGround = false;
		}

		if (FlightHelper.isFlyingCreativeOrSpectator(player)) {
			float inertiaMultiplier = BetterFlying.getConfig().flightInertiaMultiplier;

			if (inertiaMultiplier < 1F) {
				ClientInput input = player.input;
				Input keyPresses = input.keyPresses;
				double inertiaMultiplierSqrt = Math.sqrt(inertiaMultiplier);

				if (!keyPresses.forward() && !keyPresses.backward() && !keyPresses.left() && !keyPresses.right()) {
					player.setDeltaMovement(player.getDeltaMovement().multiply(inertiaMultiplierSqrt, 1.0, inertiaMultiplierSqrt));
				}

				if (!keyPresses.jump() && !keyPresses.shift()) {
					player.setDeltaMovement(player.getDeltaMovement().multiply(1.0, inertiaMultiplierSqrt, 1.0));
				}
			}
		}

		if (player.isCreative()) {
			if (Keybinds.TOGGLE_FLIGHT.consumeClick()) {
				boolean isFlying = !player.getAbilities().flying;

				player.getAbilities().flying = isFlying;
				player.onUpdateAbilities();

				if (isFlying) {
					temporaryFlyOnGroundTimer = 10;
				}
			}

			if (temporaryFlyOnGroundTimer > 0) {
				if (player.isShiftKeyDown()) {
					temporaryFlyOnGroundTimer = 0;
				}
				else {
					--temporaryFlyOnGroundTimer;
					player.setOnGround(false);
				}
			}
		}
		else {
			temporaryFlyOnGroundTimer = 0;
		}
	}

	public static boolean shouldResetFOV(LocalPlayer player) {
		return BetterFlying.getConfig().disableChangingFovWhileFlying && FlightHelper.isFlyingCreativeOrSpectator(player);
	}
}
