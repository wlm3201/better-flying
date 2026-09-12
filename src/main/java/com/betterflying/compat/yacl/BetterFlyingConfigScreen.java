package com.betterflying.compat.yacl;

import com.betterflying.BetterFlying;
import com.betterflying.config.BetterFlyingConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DropdownStringControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * YACL based config screen, only loaded through the Mod Menu entrypoint so that neither
 * Mod Menu nor YACL are required at runtime.
 */
public final class BetterFlyingConfigScreen {
	private static final String KEY_PREFIX = "betterflying.config.";
	private static final float MAX_MULTIPLIER = 8F;
	private static final float MULTIPLIER_STEP = 0.25F;
	private static final float INERTIA_STEP = 0.05F;

	private BetterFlyingConfigScreen() {}

	public static Screen create(Screen parent) {
		BetterFlyingConfig config = BetterFlying.getConfig();

		return YetAnotherConfigLib.createBuilder()
			.title(Component.translatable(KEY_PREFIX + "title"))
			.category(ConfigCategory.createBuilder()
				.name(Component.translatable(KEY_PREFIX + "category.flight"))
				.group(OptionGroup.createBuilder()
					.name(Component.translatable(KEY_PREFIX + "group.toggles"))
					.option(tickBox("doubleTapJumpToToggleFlight", true,
						() -> config.doubleTapJumpToToggleFlight,
						value -> config.doubleTapJumpToToggleFlight = value))
					.option(tickBox("flyOnGroundInCreative", false,
						() -> config.flyOnGroundInCreative,
						value -> config.flyOnGroundInCreative = value))
					.option(tickBox("disableChangingFovWhileFlying", false,
						() -> config.disableChangingFovWhileFlying,
						value -> config.disableChangingFovWhileFlying = value))
					.build())
				.group(OptionGroup.createBuilder()
					.name(Component.translatable(KEY_PREFIX + "group.inertia"))
					.option(multiplier("flightInertiaMultiplier", 1F, 0F, 1F, INERTIA_STEP, 2,
						() -> config.flightInertiaMultiplier,
						value -> config.flightInertiaMultiplier = value))
					.build())
				.group(OptionGroup.createBuilder()
					.name(Component.translatable(KEY_PREFIX + "group.creative"))
					.option(multiplier("flightHorizontalSpeedMpCreativeDefault", 1F,
						() -> config.flightHorizontalSpeedMpCreativeDefault,
						value -> config.flightHorizontalSpeedMpCreativeDefault = value))
					.option(multiplier("flightHorizontalSpeedMpCreativeSprinting", 2F,
						() -> config.flightHorizontalSpeedMpCreativeSprinting,
						value -> config.flightHorizontalSpeedMpCreativeSprinting = value))
					.option(multiplier("flightVerticalSpeedMpCreativeDefault", 1F,
						() -> config.flightVerticalSpeedMpCreativeDefault,
						value -> config.flightVerticalSpeedMpCreativeDefault = value))
					.option(multiplier("flightVerticalSpeedMpCreativeSprinting", 2F,
						() -> config.flightVerticalSpeedMpCreativeSprinting,
						value -> config.flightVerticalSpeedMpCreativeSprinting = value))
					.build())
				.group(OptionGroup.createBuilder()
					.name(Component.translatable(KEY_PREFIX + "group.spectator"))
					.option(multiplier("flightHorizontalSpeedMpSpectatorDefault", 1F,
						() -> config.flightHorizontalSpeedMpSpectatorDefault,
						value -> config.flightHorizontalSpeedMpSpectatorDefault = value))
					.option(multiplier("flightHorizontalSpeedMpSpectatorSprinting", 2F,
						() -> config.flightHorizontalSpeedMpSpectatorSprinting,
						value -> config.flightHorizontalSpeedMpSpectatorSprinting = value))
					.option(multiplier("flightVerticalSpeedMpSpectatorDefault", 1F,
						() -> config.flightVerticalSpeedMpSpectatorDefault,
						value -> config.flightVerticalSpeedMpSpectatorDefault = value))
					.option(multiplier("flightVerticalSpeedMpSpectatorSprinting", 2F,
						() -> config.flightVerticalSpeedMpSpectatorSprinting,
						value -> config.flightVerticalSpeedMpSpectatorSprinting = value))
					.build())
				.group(OptionGroup.createBuilder()
					.name(Component.translatable(KEY_PREFIX + "group.keybind"))
					.option(Option.<String>createBuilder()
						.name(Component.translatable(KEY_PREFIX + "toggleFlightModifier"))
						.description(OptionDescription.of(Component.translatable(KEY_PREFIX + "toggleFlightModifier.desc")))
						.binding("NONE",
							() -> config.toggleFlightModifier,
							value -> config.toggleFlightModifier = value)
						.controller(option -> DropdownStringControllerBuilder.create(option)
							.values("NONE", "CONTROL", "SHIFT", "ALT"))
						.build())
					.build())
				.build())
			.save(() -> {
				config.applyModifiers();
				config.save();
			})
			.build()
			.generateScreen(parent);
	}

	private static Option<Boolean> tickBox(String name, boolean defaultValue, Supplier<Boolean> getter, Consumer<Boolean> setter) {
		return Option.<Boolean>createBuilder()
			.name(Component.translatable(KEY_PREFIX + name))
			.description(OptionDescription.of(Component.translatable(KEY_PREFIX + name + ".desc")))
			.binding(defaultValue, getter, setter)
			.controller(TickBoxControllerBuilder::create)
			.build();
	}

	private static Option<Float> multiplier(String name, float defaultValue, Supplier<Float> getter, Consumer<Float> setter) {
		return multiplier(name, defaultValue, 0F, MAX_MULTIPLIER, MULTIPLIER_STEP, 2, getter, setter);
	}

	private static Option<Float> multiplier(String name, float defaultValue, float min, float max, float step, int decimals, Supplier<Float> getter, Consumer<Float> setter) {
		return Option.<Float>createBuilder()
			.name(Component.translatable(KEY_PREFIX + name))
			.description(OptionDescription.of(Component.translatable(KEY_PREFIX + name + ".desc")))
			.binding(defaultValue, getter, setter)
			.controller(option -> FloatSliderControllerBuilder.create(option)
				.range(min, max)
				.step(step)
				.formatValue(value -> Component.literal(String.format(Locale.ROOT, "%." + decimals + "f", value))))
			.build();
	}
}
