package com.mesabrook.milky.item;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.handlers.IHasModel;
import com.mesabrook.milky.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMilkBottle extends ItemFood implements IHasModel
{
	public ItemMilkBottle(String name)
	{
		super(0, 0.0F, false);
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.FOOD);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack) 
	{
        return 32;
    }
	
	@Override
	public void registerModels() 
	{
		Milky.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
    @Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
    	return EnumAction.DRINK;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote) entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
        
        if (entityLiving instanceof EntityPlayer && ((EntityPlayer) entityLiving).capabilities.isCreativeMode) 
        {
            return stack;
        }

        stack.shrink(1);
        return stack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : stack;
    }
}
