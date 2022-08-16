package net.astrospud.astrovariety.types.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AVScaffoldRemoveMatrix {
    private List<BlockState> stateList;
    public BlockState state;
    private List<BlockPos> posList;
    public BlockPos pos;
    private List<World> worldList;
    public World world;
    public boolean valid;

    private AVScaffoldRemoveMatrix() {}

    public static AVScaffoldRemoveMatrix create() {
        AVScaffoldRemoveMatrix matrix = new AVScaffoldRemoveMatrix();
        matrix.posList= new ArrayList<>();
        matrix.stateList = new ArrayList<>();
        matrix.worldList = new ArrayList<>();
        matrix.valid = false;
        return matrix;
    }

    private boolean canDoNext() {
        if (stateList != null && posList != null && worldList != null) {
            return (stateList.size() > 0 && posList.size() > 0 && worldList.size() > 0);
        }
        return false;
    }

    AVScaffoldRemoveMatrix getNext() {
        if (canDoNext()) {
            AVScaffoldRemoveMatrix matrix = this;

            matrix.state = stateList.get(0);
            stateList.remove(0);
            matrix.world = worldList.get(0);
            worldList.remove(0);
            matrix.pos = posList.get(0);
            posList.remove(0);
            matrix.valid = true;

            return matrix;
        }
        AVScaffoldRemoveMatrix matrix = this;
        matrix.valid = false;
        return matrix;
    }

    void add(BlockState state, BlockPos pos, World world) {
        stateList.add(state);
        posList.add(pos);
        worldList.add(world);
    }

    void clearData() {
        posList = new ArrayList<>();
        stateList = new ArrayList<>();
        worldList = new ArrayList<>();
        valid = false;
    }
}
