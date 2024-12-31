package me.ajh123.sams_bits.content.registry;

import java.nio.file.Files;
import java.nio.file.Path;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.data.exporter.OSM_XML_Exporter;
import me.ajh123.sams_bits.roads.RoadManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.WorldSavePath;

public class ModCommands {
    public static void initialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("sams_transport")
                .then(CommandManager.literal("osm_export")
                    .then(CommandManager.argument("dimension", DimensionArgumentType.dimension())
                        .executes(ModCommands::executeOSMExport)
                    )
                )
            );
        });
    }

    private static int executeOSMExport(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerWorld world = DimensionArgumentType.getDimensionArgument(context, "dimension");
        RoadManager manager = SamsBits.getRoadManager(world);

        if (manager == null) {
            context.getSource().sendError(Text.literal("No RoadManager found for the selected dimension."));
            return 0;
        }

        MinecraftServer server = world.getServer();
        Path dir = server.getSavePath(WorldSavePath.ROOT).resolve(SamsBits.MOD_ID);
        String worldName = SamsBits.getWorldName(world);
        Path roadsDir = dir.resolve("roads").resolve(worldName);

        try {
            Files.createDirectories(roadsDir);
            Path path = roadsDir.resolve("osm_export.osm");
            OSM_XML_Exporter exporter = new OSM_XML_Exporter(path, manager);
            exporter.export();

            context.getSource().sendFeedback(() -> Text.literal("Exported roads for %s to %s (server side only!)".formatted(worldName, path)), false);
        } catch (Exception e) {
            context.getSource().sendError(Text.literal("Failed to export roads: " + e.getMessage()));
            return 0;
        }

        return 1;
    }
}
