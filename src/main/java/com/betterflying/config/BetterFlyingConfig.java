package com.betterflying.config;

import com.betterflying.input.Keybinds;
import com.betterflying.input.ModifierKey;

import java.nio.file.Path;

public final class BetterFlyingConfig {
	public static BetterFlyingConfig load(Path path) {
		BetterFlyingConfig config = ConfigSerializer.read(path);
		config.path = path;
		return config;
	}

	private transient Path path = null;

	/**
	 * When disabled, double tapping the jump key no longer toggles flight in creative mode.
	 */
	public boolean doubleTapJumpToToggleFlight = true;

	/**
	 * Multiplier applied to flight inertia (lower values stop the player faster when no keys are held).
	 * Values below 1 reduce inertia, 1 keeps vanilla behavior.
	 */
	public float flightInertiaMultiplier = 1F;

	/**
	 * Prevents the field of view from changing while flying in creative or spectator mode.
	 */
	public boolean disableChangingFovWhileFlying = false;

	/**
	 * Keeps flight enabled when the player touches the ground in creative mode.
	 */
	public boolean flyOnGroundInCreative = false;

	/**
	 * Modifier key required to trigger the flight toggle keybind (NONE, CONTROL, SHIFT or ALT).
	 */
	public String toggleFlightModifier = "NONE";

	public float flightHorizontalSpeedMpCreativeDefault = 1F;
	public float flightHorizontalSpeedMpCreativeSprinting = 2F;
	public float flightHorizontalSpeedMpSpectatorDefault = 1F;
	public float flightHorizontalSpeedMpSpectatorSprinting = 2F;
	public float flightVerticalSpeedMpCreativeDefault = 1F;
	public float flightVerticalSpeedMpCreativeSprinting = 2F;
	public float flightVerticalSpeedMpSpectatorDefault = 1F;
	public float flightVerticalSpeedMpSpectatorSprinting = 2F;

	public void applyModifiers() {
		Keybinds.TOGGLE_FLIGHT.setModifier(ModifierKey.byName(toggleFlightModifier));
	}

	public void save() {
		if (path != null) {
			ConfigSerializer.write(path, this);
		}
	}
}
