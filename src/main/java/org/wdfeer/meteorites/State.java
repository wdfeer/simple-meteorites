package org.wdfeer.meteorites;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class State extends PersistentState {
    public long last = 0;
    public long interval = 1000;
    public byte minPower = 1;
    public byte maxPower = 15;
    public int maxDistance = 160;
    public int altitude = 300;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putLong("lastMeteoriteTime", last);
        nbt.putLong("meteoriteSpawnInterval", interval);
        nbt.putByte("meteoriteMinPower", minPower);
        nbt.putByte("meteoriteMaxPower", maxPower);
        nbt.putInt("meteoriteMaxSpawnDistance", maxDistance);
        nbt.putInt("meteoriteSpawnAltitude", altitude);
        return nbt;
    }

    public static State createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        State state = new State();

        state.last = tag.getLong("lastMeteoriteTime");
        state.interval = tag.getLong("meteoriteSpawnInterval");
        state.minPower = tag.getByte("meteoriteMinPower");
        state.maxPower = tag.getByte("meteoriteMaxPower");
        state.maxDistance = tag.getInt("meteoriteMaxSpawnDistance");
        state.maxDistance = tag.getInt("meteoriteSpawnAltitude");

        return state;
    }

    private static final Type<State> type = new Type<>(
            State::new,
            State::createFromNbt,
            null
    );

    public static State getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        State state = persistentStateManager.getOrCreate(type, Mod.MOD_ID);

        state.markDirty();

        return state;
    }
}
