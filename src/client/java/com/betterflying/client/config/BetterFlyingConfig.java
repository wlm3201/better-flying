package com.betterflying.client.config;

import java.nio.file.Path;

public final class BetterFlyingConfig {
    public static BetterFlyingConfig load(Path path) {
        BetterFlyingConfig config = ConfigSerializer.read(path);
        config.path = path;
        return config;
    }

    private transient Path path = null;

    public boolean doubleTapJumpToToggleFlight = true;

    public float flightInertiaMultiplier = 1F;

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
