package net.astrospud.astrovariety.types.unique.entity;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.registry.AVItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class DynamiteEntity extends ThrownItemEntity {
    public DynamiteEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DynamiteEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
        super(entityType, livingEntity, world);
    }

    public DynamiteEntity(EntityType<? extends ThrownItemEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }


    @Override
    public Packet<?> createSpawnPacket() {
        return super.createSpawnPacket();
    }

    @Override
    protected Item getDefaultItem() {
        return AVItems.DYNAMITE;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        explode();
        this.discard();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        explode();
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(DamageSource.explosion(explode()), 4);
        this.discard();
    }

    public Explosion explode() {
        float f = 4F / 9F;
        return this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), f, Explosion.DestructionType.BREAK);
    }
}