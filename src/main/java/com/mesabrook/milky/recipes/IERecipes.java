package com.mesabrook.milky.recipes;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.init.ModFluids;
import com.mesabrook.milky.init.ModItems;

import blusunrize.immersiveengineering.api.crafting.BottlingMachineRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class IERecipes 
{
	public static void registerMachineRecipes()
	{
		// Fluid Stacks
		FluidStack milk_bucket = new FluidStack(ModFluids.liquid_milk, 1000);
		FluidStack milk_bottle = new FluidStack(ModFluids.liquid_milk, 256);
		
		// Recipes Try/Catch
		try
		{
			BottlingMachineRecipe.addRecipe(new ItemStack(Items.MILK_BUCKET, 1), Items.BUCKET, milk_bucket);
			BottlingMachineRecipe.addRecipe(new ItemStack(ModItems.MILK_BOTTLE, 1), Items.GLASS_BOTTLE, milk_bottle);
		}
		catch(Exception ex)
		{
			Milky.logger.info("[" + Milky.MOD_NAME + "] ERROR: " + ex);
		}
		
	}
}
