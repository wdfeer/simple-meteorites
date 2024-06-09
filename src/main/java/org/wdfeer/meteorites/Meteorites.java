package org.wdfeer.meteorites;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.Arrays;

public class Meteorites {
    private static long last = 0;
    public static long interval = 1000;

    public static void OnWorldTickEnd(ServerWorld serverWorld) {
        long time = serverWorld.getTime();
        long diff = time - last;
        if (diff > interval) {
            last = time;
            SummonMeteorite(serverWorld);
        }
    }


    public static byte minPower = 1;
    public static byte maxPower = 15;

    private static byte GetPower(Random random) {
        return (byte) (random.nextBetween(minPower, maxPower));
    }

    public static int maxDistance = 160;

    private static Vec3d GetOffset(Random random) {
        return new Vec3d(random.nextBetween(-maxDistance, maxDistance), 100, random.nextBetween(-maxDistance, maxDistance));
    }

    private static void SummonMeteorite(ServerWorld serverWorld) {
        ServerPlayerEntity player = serverWorld.getRandomAlivePlayer();
        if (player == null) return;

        var fireball = new FireballEntity(EntityType.FIREBALL, serverWorld);
        fireball.powerY = -0.1;
        fireball.setPosition(player.getPos().add(GetOffset(serverWorld.random)));

        NbtCompound nbt = new NbtCompound();
        nbt.putByte("ExplosionPower", GetPower(serverWorld.random));
        fireball.readCustomDataFromNbt(nbt);

        serverWorld.addEntities(Arrays.stream(new Entity[]{fireball}));
    }
}
