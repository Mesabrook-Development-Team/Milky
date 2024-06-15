package com.mesabrook.milky.blocks;

import com.mesabrook.milky.config.ModConfig;
import com.mesabrook.milky.init.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;

public class BlockLiquidMilk extends BlockFluidClassic 
{
    public BlockLiquidMilk(Fluid fluid, Material material) 
    {
        super(fluid, material);
    }

    @Nonnull
    @Override
    public String getUnlocalizedName() 
    {
        Fluid fluid = FluidRegistry.getFluid(fluidName);
        if(fluid != null) 
        {
            return fluid.getUnlocalizedName();
        }
        return super.getUnlocalizedName();
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) 
    {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
    {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == Items.GLASS_BOTTLE) 
        {
            if (!world.isRemote) 
            {
                if (!player.capabilities.isCreativeMode) 
                {
                    stack.shrink(1);
                }
                ItemStack milkBottle = new ItemStack(ModItems.MILK_BOTTLE);
                if (!player.inventory.addItemStackToInventory(milkBottle)) 
                {
                    player.dropItem(milkBottle, false);
                }
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return true;
        }
        return false;
    }
}