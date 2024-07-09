package com.mesabrook.milky.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class BucketResultShaplessRecipeFactory implements IRecipeFactory {
	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		final String group = JsonUtils.getString(json, "group", "");
		final NonNullList<Ingredient> ingredients = parseShapeless(context, json);
		JsonObject resultObject = JsonUtils.getJsonObject(json, "result");
		String fluidName = JsonUtils.getString(resultObject, "bucket");
		Fluid fluid = FluidRegistry.getFluid(fluidName);
		if (fluid == null)
		{
			throw new JsonSyntaxException("Could not find fluid '" + fluidName + "'");
		}
		final ItemStack result = FluidUtil.getFilledBucket(new FluidStack(fluid, 1000));

		return new ShapelessOreRecipe(group.isEmpty() ? null : new ResourceLocation(group), ingredients, result)
		{
			@Override
			public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) { // Don't let empty buckets remain
				return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
			}
		};
	}
	
	private static NonNullList<Ingredient> parseShapeless(final JsonContext context, final JsonObject json) {
		final NonNullList<Ingredient> ingredients = NonNullList.create();
		for (final JsonElement element : JsonUtils.getJsonArray(json, "ingredients"))
			ingredients.add(CraftingHelper.getIngredient(element, context));

		if (ingredients.isEmpty())
			throw new JsonParseException("No ingredients for shapeless recipe");

		return ingredients;
	}
}
