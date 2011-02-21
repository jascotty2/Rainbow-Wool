package com.fullwall.RainbowWool;



import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.material.Wool;

/**
 * Listener
 * 
 * @author fullwall
 */
public class BlockListen extends BlockListener {
	public static BlockListen pl;
	@SuppressWarnings("unused")
	private final RainbowWool plugin;

	public BlockListen(final RainbowWool plugin) {
		this.plugin = plugin;
	}

	public void onBlockRedstoneChange(BlockRedstoneEvent e) {
		Block b = e.getBlock();
		Block U = b.getRelative(BlockFace.UP);
		Block D = b.getRelative(BlockFace.DOWN);
		Block N = b.getRelative(BlockFace.NORTH);
		Block S = b.getRelative(BlockFace.SOUTH);
		Block W = b.getRelative(BlockFace.WEST);
		Block E = b.getRelative(BlockFace.EAST);
		Wool w;
		DyeColor color = DyeColor.getByData((byte) e.getNewCurrent());
		
		if (U.getType() == Material.WOOL) {
			w = (Wool) U;
			w.setColor(color);
		}
		if (D.getType() == Material.WOOL) {
			w = (Wool) D;
			w.setColor(color);
		}
		if (N.getType() == Material.WOOL) {
			w = (Wool) N;
			w.setColor(color);
		}
		if (S.getType() == Material.WOOL) {
			w = (Wool) S;
			w.setColor(color);
		}
		if (W.getType() == Material.WOOL) {
			w = (Wool) W;
			w.setColor(color);
		}
		if (E.getType() == Material.WOOL) {
			w = (Wool) E;
			w.setColor(color);
		}

	}
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.getBlock().getType()== Material.REDSTONE_WIRE) {
			Block b = e.getBlock();
			Block U = b.getRelative(BlockFace.UP);
			Block D = b.getRelative(BlockFace.DOWN);
			Block N = b.getRelative(BlockFace.NORTH);
			Block S = b.getRelative(BlockFace.SOUTH);
			Block W = b.getRelative(BlockFace.WEST);
			Block E = b.getRelative(BlockFace.EAST);
			if (U.getType() == Material.WOOL) {
				U.setData((byte) 0);
			}
			if (D.getType() == Material.WOOL) {
				D.setData((byte) 0);
			}
			if (N.getType() == Material.WOOL) {
				N.setData((byte) 0);
			}
			if (S.getType() == Material.WOOL) {
				S.setData((byte) 0);
			}
			if (W.getType() == Material.WOOL) {
				W.setData((byte) 0);
			}
			if (E.getType() == Material.WOOL) {
				E.setData((byte) 0);
			}
		}
	}
}
