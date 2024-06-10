package org.wdfeer.meteorites;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Commands {
    public static void RegisterCommands(CommandDispatcher<ServerCommandSource> dispatcher,
                                        CommandRegistryAccess access,
                                        CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("meteorites")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("interval")
                        .then(argument("ticks", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Meteorites.state.interval = IntegerArgumentType.getInteger(context, "ticks");
                                    context.getSource().sendFeedback(() -> Text.literal("Set meteorite interval to " + Meteorites.state.interval + " ticks"), true);
                                    Meteorites.state.markDirty();
                                    return 1;
                                }))
                )
                .then(literal("min_power")
                        .then(argument("value", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Meteorites.state.minPower = (byte) IntegerArgumentType.getInteger(context, "value");
                                    context.getSource().sendFeedback(() -> Text.literal("Set min meteorite power to " + Meteorites.state.minPower), true);
                                    Meteorites.state.markDirty();
                                    return 1;
                                }))
                )
                .then(literal("max_power")
                        .then(argument("value", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Meteorites.state.maxPower = (byte) IntegerArgumentType.getInteger(context, "value");
                                    context.getSource().sendFeedback(() -> Text.literal("Set max meteorite power to " + Meteorites.state.maxPower), true);
                                    Meteorites.state.markDirty();
                                    return 1;
                                })))
                .then(literal("distance")
                        .then(argument("value", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Meteorites.state.maxDistance = (byte) IntegerArgumentType.getInteger(context, "value");
                                    context.getSource().sendFeedback(() -> Text.literal("Set max spawn distance to " + Meteorites.state.maxDistance), true);
                                    Meteorites.state.markDirty();
                                    return 1;
                                })))
                .then(literal("altitude")
                        .then(argument("height", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Meteorites.state.altitude = IntegerArgumentType.getInteger(context, "height");
                                    context.getSource().sendFeedback(() -> Text.literal("Set spawn altitude to " + Meteorites.state.altitude), true);
                                    Meteorites.state.markDirty();
                                    return 1;
                                })))
        );
    }
}

