package org.wdfeer.meteorites;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ModInitializer {
    public static final String MOD_ID = "meteorites";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ServerTickEvents.END_WORLD_TICK.register(Meteorites::OnWorldTickEnd);

        CommandRegistrationCallback.EVENT.register(Commands::RegisterCommands);

        ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer server) -> Meteorites.state = State.getServerState(server));
    }
}