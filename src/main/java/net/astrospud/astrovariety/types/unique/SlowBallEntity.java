package net.astrospud.astrovariety.types.unique;

import net.astrospud.astrovariety.registry.AVEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class SlowBallEntity extends ThrownItemEntity {
    public SlowBallEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        setDefaults();
    }

    public SlowBallEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
        super(entityType, livingEntity, world);
        setDefaults();
    }

    public void setDefaults() {
        this.setNoGravity(true);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return super.createSpawnPacket();
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity entity) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 4, false, true, true));
        }
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();

        if (age > 900) {
            this.discard();
        }

        this.setVelocity(this.getVelocity().normalize());
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        Box box = new Box(x - 5, y - 5, z - 5, x + 5, y + 5, z + 5);

        List<Entity> others = world.getOtherEntities(this, box);
        for (int i = 0; i < others.size(); i++) {
            Entity entity = others.get(i);
            if (entity.distanceTo(this) <= 5) {
                if (!(entity instanceof SlowBallEntity)) {
                    entity.setVelocity(
                            entity.getVelocity().x * 0.9,
                            entity.getVelocity().y * 0.9,
                            entity.getVelocity().z * 0.9
                    );
                }
            }
        }
    }
}