package com.fullwall.RainbowWool;

import java.util.Date;
import java.util.HashMap;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;

/**
 * Listener
 * 
 * @author fullwall
 */
public class BlockListen extends BlockListener {

    public static BlockListen pl;
    public final static int min_wait = 100;
    public static int MAX_CHANGE_PER_UPDATE = 8;
    HashMap<Location, RedstoneBlockChange> changedBlocks = new HashMap<Location, RedstoneBlockChange>();

    public class RedstoneBlockChange {

        protected int power;
        protected Date lastUpdate;
        public int numChanges;

        public RedstoneBlockChange(int newPower) {
            power = newPower;
            lastUpdate = new Date();
        }

        public final RedstoneBlockChange SetPower(int newPower) {
            power = newPower;
            ++numChanges;
            if (numChanges >= MAX_CHANGE_PER_UPDATE) {
                lastUpdate = new Date();
            }
            return this;
        }

        public int getPower() {
            return power;
        }

        public long getUpdateAge() {
            return (new Date()).getTime() - lastUpdate.getTime();
        }
    }

    //@SuppressWarnings("unused")
    //private final RainbowWool plugin;
    public BlockListen(final RainbowWool plugin) {
        //this.plugin = plugin;
    }

    @Override
    public void onBlockRedstoneChange(BlockRedstoneEvent e) {
        Block b = e.getBlock();
        RedstoneBlockChange redBlock = changedBlocks.get(b.getLocation());
        if (e.getOldCurrent() == e.getNewCurrent()
                || (redBlock != null
                && (redBlock.getUpdateAge() < min_wait || redBlock.getPower() == e.getNewCurrent()))) {
            return;
        }
        changedBlocks.put(b.getLocation(), redBlock != null
                ? redBlock.SetPower(e.getNewCurrent())
                : new RedstoneBlockChange(e.getNewCurrent()));
        
        DyeColor color = DyeColor.getByData((byte) e.getNewCurrent());

        Block rel[] = new Block[]{
            b.getRelative(BlockFace.UP),
            b.getRelative(BlockFace.DOWN),
            b.getRelative(BlockFace.NORTH),
            b.getRelative(BlockFace.SOUTH),
            b.getRelative(BlockFace.WEST),
            b.getRelative(BlockFace.EAST)};

        for (Block d : rel) {
            if (d.getType() == Material.WOOL) {
                // this triggers another REDSTONE_CHANGE event, potentially causing stack overflow
                d.setData(color.getData());
            }
        }

    }

    @Override
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.REDSTONE_WIRE) {
            Block b = e.getBlock();
            RedstoneBlockChange redBlock = changedBlocks.get(b.getLocation());
            if ((redBlock != null && (redBlock.getUpdateAge() < min_wait || redBlock.getPower() == -1))) {
                return;
            }
            changedBlocks.put(b.getLocation(), redBlock != null
                    ? redBlock.SetPower(-1)
                    : new RedstoneBlockChange(-1));
            Block rel[] = new Block[]{
                b.getRelative(BlockFace.UP),
                b.getRelative(BlockFace.DOWN),
                b.getRelative(BlockFace.NORTH),
                b.getRelative(BlockFace.SOUTH),
                b.getRelative(BlockFace.WEST),
                b.getRelative(BlockFace.EAST)};

            for (Block d : rel) {
                if (d.getType() == Material.WOOL && !(d.isBlockPowered() || d.isBlockIndirectlyPowered())) {
                    d.setData((byte) 0);
                }
            }
        }
    }
}
