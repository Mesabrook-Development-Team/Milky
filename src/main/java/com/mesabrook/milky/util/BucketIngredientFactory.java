package com.mesabrook.milky.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class BucketIngredientFactory implements IIngredientFactory {

	@Override
	public Ingredient parse(JsonContext context, JsonObject json) {
		String fluidName = JsonUtils.getString(json, "bucket");
		Fluid fluid = FluidRegistry.getFluid(fluidName);
		if (fluid == null)
		{
			throw new JsonSyntaxException("Could not find fluid '" + fluidName + "'");
		}
		
		FluidStack fluidStack = new FluidStack(fluid, 1000);
		return Ingredient.fromStacks(FluidUtil.getFilledBucket(fluidStack));
	}

}
