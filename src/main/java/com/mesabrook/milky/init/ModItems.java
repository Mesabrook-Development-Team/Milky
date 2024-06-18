package com.mesabrook.milky.init;

import java.util.ArrayList;
import java.util.List;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.item.GenericItem;
import com.mesabrook.milky.item.FoodItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;

public class ModItems
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item MILK_BOTTLE = new FoodItem("milk_bottle", 0, 0.0F, 1);
	public static final Item CHOC_MILK_BOTTLE = new FoodItem("chocolate_milk_bottle", 0, 0.0F, 1);
	public static final Item STRAWB_MILK_BOTTLE = new FoodItem("strawberry_milk_bottle", 0, 0.0F, 1);
	public static final Item CARAMEL_MILK_BOTTLE = new FoodItem("caramel_milk_bottle", 0, 0.0F, 1);
	
	public static final Item STRAWBERRY_SYRUP = new GenericItem("strawberry_syrup", 16, CreativeTabs.MISC, true);
	public static final Item CARAMEL_ITEM = new FoodItem("caramel_item", 2, 3.4F, 16);
}
