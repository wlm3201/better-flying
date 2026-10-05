package com.betterflying.config;

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
	 * Keeps flight enabled when the player touches the ground in creative mode.
	 */
	public boolean flyOnGroundInCreative = false;

	public float flightHorizontalSpeedMpCreativeDefault = 1F;
	public float flightHorizontalSpeedMpCreativeSprinting = 2F;
	public float flightHorizontalSpeedMpSpectatorDefault = 1F;
	public float flightHorizontalSpeedMpSpectatorSprinting = 2F;
	public float flightVerticalSpeedMpCreativeDefault = 1F;
	public float flightVerticalSpeedMpCreativeSprinting = 2F;
	public float flightVerticalSpeedMpSpectatorDefault = 1F;
	public float flightVerticalSpeedMpSpectatorSprinting = 2F;

	public void save() {
		if (path != null) {
			ConfigSerializer.write(path, this);
		}
	}
}
