package org.wdfeer.meteorites;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Arrays;
import java.util.Random;

public class Meteorites {
    private static long last = 0;
    private static final long INTERVAL = 200;

    public static void OnWorldTickEnd(ServerWorld serverWorld) {
        long time = serverWorld.getTime();
        long diff = time - last;
        if (diff > INTERVAL) {
            last = time;
            SummonMeteorite(serverWorld);
        }
    }


    private static byte GetPower() {
        return (byte) (new Random().nextInt(1, 11));
    }

    private static void SummonMeteorite(ServerWorld serverWorld) {
        ServerPlayerEntity player = serverWorld.getRandomAlivePlayer();
        if (player == null) return;

        var fireball = new FireballEntity(EntityType.FIREBALL, serverWorld);
        fireball.powerY = -0.1;
        fireball.setPosition(player.getPos().add(0, 100, 0));

        NbtCompound nbt = new NbtCompound();
        nbt.putByte("ExplosionPower", GetPower());
        fireball.readCustomDataFromNbt(nbt);

        serverWorld.addEntities(Arrays.stream(new Entity[]{fireball}));
    }
}
