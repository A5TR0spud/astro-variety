package net.astrospud.astrovariety.types.utils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AVScaffoldRemoveData {
    AVScaffoldRemoveMatrix matrix;

    public AVScaffoldRemoveData() {
        matrix = AVScaffoldRemoveMatrix.create();
    }

    public void add(@Nullable BlockState state, BlockPos pos, World world) {
        state = state == null ? world.getBlockState(pos) : state;
        matrix.add(state, pos, world);
    }


    public AVScaffoldRemoveMatrix getNext() {
        return matrix.getNext();
    }

    public void clearData() {
        matrix.clearData();
    }

    public boolean containsPos(BlockPos pos) {
        return matrix.containsPos(pos);
    }
}
