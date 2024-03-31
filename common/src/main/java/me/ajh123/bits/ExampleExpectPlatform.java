package me.ajh123.bits;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class ExampleExpectPlatform {
    /**
     * an example of {@link ExpectPlatform}.
     * <p>
     * This must be a <b>public static</b> method. The platform-implemented solution must be placed under a
     * platform sub-package, with its class suffixed with {@code Impl}.
     * <p>
     * Example:
     * Expect: me.ajh123.bits.ExampleExpectPlatform#platformName()
     * Actual Fabric: me.ajh123.bits.fabric.ExampleExpectPlatformImpl#platformName()
     * Actual Forge: me.ajh123.bits.forge.ExampleExpectPlatformImpl#platformName()
     * <p>
     * <a href="https://plugins.jetbrains.com/plugin/16210-architectury">You should also get the IntelliJ plugin to help with @ExpectPlatform.</a>
     */
    @ExpectPlatform
    public static String platformName() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
}
