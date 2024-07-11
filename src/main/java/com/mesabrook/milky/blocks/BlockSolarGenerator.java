package com.mesabrook.milky.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.handlers.IHasModel;
import com.mesabrook.milky.init.ModBlocks;
import com.mesabrook.milky.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSolarGenerator extends Block implements IHasModel
{    
    public BlockSolarGenerator(String name)
    {
    	super(Material.IRON);
    	setRegistryName(name);
    	setUnlocalizedName(name);
    	setCreativeTab(CreativeTabs.REDSTONE);
		setHarvestLevel("pickaxe", 0);
		setHardness(3.0F);
        setResistance(5.0F);
        setTickRandomly(true);
        
		ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    
	@Override
	public void registerModels() 
	{
		Milky.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");	
	}
	
    @Override
    public boolean hasTileEntity(IBlockState state) 
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) 
    {
        return new TileEntitySolarBlock();
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		if(GuiScreen.isShiftKeyDown())
		{
			tooltip.add(TextFormatting.YELLOW + "A low quality piece of crap solar generator. Good luck powering anything even remotely more power-intensive than a Milking Machine with this.");
		}
		else
		{
			tooltip.add(TextFormatting.YELLOW + "Press [" + Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName() + "] for more info.");
		}
	}
}
