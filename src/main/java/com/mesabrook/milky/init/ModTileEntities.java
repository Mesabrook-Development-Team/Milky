package com.mesabrook.milky.init;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.blocks.TileEntityMilkingMachine;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.util.ResourceLocation;

public class ModTileEntities 
{
	public static void registerTileEntities()
	{
		Milky.logger.info("Registering Tile Entities...");
		
		GameRegistry.registerTileEntity(TileEntityMilkingMachine.class, new ResourceLocation(Milky.MOD_ID + ":milking_machine"));
	}
}
