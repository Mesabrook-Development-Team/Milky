package com.mesabrook.milky.item;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.handlers.IHasModel;
import com.mesabrook.milky.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GenericItem extends Item implements IHasModel
{
	public GenericItem(String name, int stackSize, CreativeTabs creativeTab, boolean includeInModItems)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(stackSize);
		
		if(includeInModItems)
		{
			setCreativeTab(creativeTab);
		}
		else
		{
			setCreativeTab(null);
		}
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Milky.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
