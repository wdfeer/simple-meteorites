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
                                    Meteorites.interval = IntegerArgumentType.getInteger(context, "ticks");
                                    context.getSource().sendFeedback(() -> Text.literal("Set meteorite interval to " + Meteorites.interval + " ticks"), true);
                                    return 1;
                                }))
                )
                .then(literal("min_power")
                        .then(argument("value", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Meteorites.minPower = (byte) IntegerArgumentType.getInteger(context, "value");
                                    context.getSource().sendFeedback(() -> Text.literal("Set min meteorite power to " + Meteorites.minPower), true);
                                    return 1;
                                }))
                )
                .then(literal("max_power")
                        .then(argument("value", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Meteorites.maxPower = (byte) IntegerArgumentType.getInteger(context, "value");
                                    context.getSource().sendFeedback(() -> Text.literal("Set max meteorite power to " + Meteorites.maxPower), true);
                                    return 1;
                                })))
                .then(literal("distance")
                        .then(argument("value", IntegerArgumentType.integer())
                                .executes(context -> {
                                    Meteorites.maxDistance = (byte) IntegerArgumentType.getInteger(context, "value");
                                    context.getSource().sendFeedback(() -> Text.literal("Set max spawn distance to " + Meteorites.maxDistance), true);
                                    return 1;
                                })))
        );
    }
}

