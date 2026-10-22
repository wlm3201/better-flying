package com.betterflying.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class ModKeyMapping extends KeyMapping {
    public static final Category CATEGORY =
            Category.register(Identifier.fromNamespaceAndPath("betterflying", "all"));

    public ModKeyMapping(String translationKey) {
        super(translationKey, KeyInputType.KEYBOARD, InputConstants.UNKNOWN.getValue(), CATEGORY);
    }

    @Override
    public void setKey(InputConstants.Key key) {
        super.setKey(key.getValue() < 0 ? InputConstants.UNKNOWN : key);
    }
}
