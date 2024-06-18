package com.mesabrook.milky.item;

import java.util.List;

import javax.annotation.Nullable;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.handlers.IHasModel;
import com.mesabrook.milky.init.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(stack.getItem() == ModItems.STRAWBERRY_SYRUP)
		{
			tooltip.add(TextFormatting.ITALIC.toString() + TextFormatting.RED + "A bottle filled with a mysterious red syrup that tastes like artificial strawberry flavoring.");
		}
	}
}
