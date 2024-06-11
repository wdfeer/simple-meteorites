package org.wdfeer.meteorites;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.dimension.DimensionType;

public class Meteorites {
    public static State state;

    public static void OnWorldTickEnd(ServerWorld serverWorld) {
        long time = serverWorld.getTime();
        long diff = time - state.last;
        if (diff > state.interval) {
            state.last = time;
            SummonMeteorite(serverWorld);
        }
    }


    private static byte GetPower(Random random) {
        return (byte) (random.nextBetween(state.minPower, state.maxPower));
    }


    private static Vec3d GetOffset(Vec3d playerPos, Random random) {
        return new Vec3d(random.nextBetween(-state.maxDistance, state.maxDistance), state.altitude - playerPos.y, random.nextBetween(-state.maxDistance, state.maxDistance));
    }

    private static boolean CanSpawnInDimension(DimensionType dimension) {
        return !dimension.hasCeiling();
    }

    private static void SummonMeteorite(ServerWorld serverWorld) {
        ServerPlayerEntity player = serverWorld.getRandomAlivePlayer();
        if (player == null) return;

        if (!CanSpawnInDimension(serverWorld.getDimension())) return;

        var fireball = new FireballEntity(EntityType.FIREBALL, serverWorld);
        fireball.powerY = -0.1;
        fireball.setPosition(player.getPos().add(GetOffset(player.getPos(), serverWorld.random)));

        NbtCompound nbt = new NbtCompound();
        nbt.putByte("ExplosionPower", GetPower(serverWorld.random));
        fireball.readCustomDataFromNbt(nbt);

        serverWorld.spawnEntity(fireball);
    }
}
