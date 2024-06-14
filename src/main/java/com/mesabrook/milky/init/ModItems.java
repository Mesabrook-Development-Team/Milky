package com.mesabrook.milky.init;

import java.util.ArrayList;
import java.util.List;

import com.mesabrook.milky.item.ItemMilkBottle;

import net.minecraft.item.Item;

public class ModItems
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item MILK_BOTTLE = new ItemMilkBottle("milk_bottle");
	public static final Item CHOC_MILK_BOTTLE = new ItemMilkBottle("chocolate_milk_bottle");
	public static final Item STRAWB_MILK_BOTTLE = new ItemMilkBottle("strawberry_milk_bottle");
	public static final Item CARAMEL_MILK_BOTTLE = new ItemMilkBottle("caramel_milk_bottle");
}
