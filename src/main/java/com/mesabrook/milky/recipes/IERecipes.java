package com.mesabrook.milky.recipes;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.init.ModFluids;
import com.mesabrook.milky.init.ModItems;
import com.mesabrook.milky.util.ModUtils;

import blusunrize.immersiveengineering.api.crafting.BottlingMachineRecipe;
import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import blusunrize.immersiveengineering.api.crafting.SqueezerRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraft.util.ResourceLocation;

public class IERecipes 
{
	public static void registerMachineRecipes()
	{
		// Fluid Stacks - Buckets
		FluidStack milk_bucket = new FluidStack(ModFluids.liquid_milk, Fluid.BUCKET_VOLUME);
		FluidStack chocolate_milk_bucket = new FluidStack(ModFluids.liquid_chocolate_milk, Fluid.BUCKET_VOLUME);
		FluidStack strawberry_milk_bucket = new FluidStack(ModFluids.liquid_strawberry_milk, Fluid.BUCKET_VOLUME);
		FluidStack caramel_milk_bucket = new FluidStack(ModFluids.liquid_caramel_milk, Fluid.BUCKET_VOLUME);
		
		// Fluid Stacks - Bottles
		FluidStack milk_bottle = new FluidStack(ModFluids.liquid_milk, 250);
		FluidStack choc_milk_bottle = new FluidStack(ModFluids.liquid_chocolate_milk, 250);
		FluidStack straw_milk_bottle = new FluidStack(ModFluids.liquid_strawberry_milk, 250);
		FluidStack caramel_milk_bottle = new FluidStack(ModFluids.liquid_caramel_milk, 250);
		
		if(Loader.isModLoaded("harvestcraft")) 
		{
			Item phcFreshMilkBucket = ForgeRegistries.ITEMS.getValue(new ResourceLocation("harvestcraft", "freshmilkitem"));
			BottlingMachineRecipe.addRecipe(new ItemStack(phcFreshMilkBucket, 2), Items.BOWL, milk_bucket);
		}
		
		// Recipes Try/Catch
		try
		{
			// Bottling Machine Recipes - Buckets
			BottlingMachineRecipe.addRecipe(FluidUtil.getFilledBucket(milk_bucket), Items.BUCKET, milk_bucket);
			BottlingMachineRecipe.addRecipe(FluidUtil.getFilledBucket(chocolate_milk_bucket), Items.BUCKET, chocolate_milk_bucket);
			BottlingMachineRecipe.addRecipe(FluidUtil.getFilledBucket(strawberry_milk_bucket), Items.BUCKET, strawberry_milk_bucket);
			BottlingMachineRecipe.addRecipe(FluidUtil.getFilledBucket(caramel_milk_bucket), Items.BUCKET, caramel_milk_bucket);
			
			// Bottling Machine Recipes - Bottles
			BottlingMachineRecipe.addRecipe(new ItemStack(ModItems.MILK_BOTTLE, 1), Items.GLASS_BOTTLE, milk_bottle);
			BottlingMachineRecipe.addRecipe(new ItemStack(ModItems.CHOC_MILK_BOTTLE, 1), Items.GLASS_BOTTLE, choc_milk_bottle);
			BottlingMachineRecipe.addRecipe(new ItemStack(ModItems.STRAWB_MILK_BOTTLE, 1), Items.GLASS_BOTTLE, straw_milk_bottle);
			BottlingMachineRecipe.addRecipe(new ItemStack(ModItems.CARAMEL_MILK_BOTTLE, 1), Items.GLASS_BOTTLE, caramel_milk_bottle);

			// ItemStack Objects
			Object[] itemInputChocolateMilk =
				{
						new ItemStack(Items.SUGAR, 3),
						new ItemStack(Items.DYE, 5, 3)
				};
			
			Object[] itemInputStrawberryMilk =
				{
						new ItemStack(Items.SUGAR, 3),
						ModUtils.getItemStackFromOreDictionary("dyeRed", 2),
						ModUtils.getItemStackFromOreDictionary("cropStrawberry", 10)
				};
			
			Object[] itemInputCaramelMilk =
				{
						new ItemStack(Items.SUGAR, 3),
						ModUtils.getItemStackFromOreDictionary("foodCaramel", 10)
				};
			
			// Harvestcraft integration
			if(Loader.isModLoaded("harvestcraft"))
			{
				Milky.logger.info("[" + Milky.MOD_NAME + "] Pam's Harvestcraft Detected. IE Squeezer and Mixer recipes registered.");
				
				SqueezerRecipe.addRecipe(milk_bucket, ModUtils.getItemStackFromOreDictionary("cropSoybean", 1), ModUtils.getItemStackFromOreDictionary("cropSoybean", 16), 1000);
				// Mixer Recipes for making flavored milks.
				MixerRecipe.addRecipe(chocolate_milk_bucket, milk_bucket, itemInputChocolateMilk, 2500);
				MixerRecipe.addRecipe(strawberry_milk_bucket, milk_bucket, itemInputStrawberryMilk, 2500);
				MixerRecipe.addRecipe(caramel_milk_bucket, milk_bucket, itemInputCaramelMilk, 2500);
			}
		}
		catch(Exception ex)
		{
			Milky.logger.info("[" + Milky.MOD_NAME + "] ERROR: " + ex);
		}
		
	}
}
