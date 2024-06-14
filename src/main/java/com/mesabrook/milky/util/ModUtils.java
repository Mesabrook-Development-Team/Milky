package com.mesabrook.milky.util;

import com.mesabrook.milky.Milky;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModUtils 
{
	public static ItemStack getItemStackFromOreDictionary(String dictionaryEntry, int stackAmount)
	{
		ItemStack[] stacks = OreDictionary.getOres(dictionaryEntry).toArray(new ItemStack[0]);
		if(stackAmount <= 0)
		{
			stackAmount = 1;
			Milky.logger.error("ERROR in converting " + dictionaryEntry + " to an ItemStack!");
			Milky.logger.error(dictionaryEntry + " must have a stack amount greater than zero! Defaulting to 1.");
		}
		if (stacks.length > 0)
		{
			ItemStack stack = stacks[0].copy();
			stack.setCount(stackAmount);
			return stack;
		}
		return null;
	}
}
