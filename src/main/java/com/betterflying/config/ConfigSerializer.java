package com.betterflying.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigSerializer {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private ConfigSerializer() {}

	public static BetterFlyingConfig read(Path path) {
		if (Files.exists(path)) {
			try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
				BetterFlyingConfig config = GSON.fromJson(reader, BetterFlyingConfig.class);

				if (config != null) {
					return config;
				}
			} catch (IOException | JsonParseException e) {
				System.err.println("[betterflying] Could not read config, using defaults: " + e);
			}
		}

		BetterFlyingConfig config = new BetterFlyingConfig();
		write(path, config);
		return config;
	}

	public static void write(Path path, BetterFlyingConfig config) {
		try {
			Path parent = path.getParent();

			if (parent != null) {
				Files.createDirectories(parent);
			}

			try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
				GSON.toJson(config, writer);
			}
		} catch (IOException e) {
			System.err.println("[betterflying] Could not write config: " + e);
		}
	}
}
