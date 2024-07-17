package com.mesabrook.milky.recipes;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.init.ModItems;
import com.mesabrook.milky.util.ModUtils;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes 
{
	public static void registerSmeltingRecipes()
	{
		Milky.logger.info("Registering Smelting Recipes...");
		try
		{
			GameRegistry.addSmelting(Items.SUGAR, new ItemStack(ModItems.CARAMEL_ITEM), 3F);
		}
		catch(Exception ex)
		{
			Milky.logger.error("ERROR! Unable to register one or more smelting recipes!");
			ex.printStackTrace();
		}
	}
}
