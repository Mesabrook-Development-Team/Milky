package com.mesabrook.milky.init;

import java.util.ArrayList;
import java.util.List;

import com.mesabrook.milky.blocks.BlockMilkingMachine;

import net.minecraft.block.Block;

public class ModBlocks 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block MILKING_MACHINE = new BlockMilkingMachine("milking_machine");
}
