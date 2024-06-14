package com.mesabrook.milky.handlers;

import com.mesabrook.milky.config.ModConfig;
import com.mesabrook.milky.init.ModFluids;
import com.mesabrook.milky.init.ModItems;

import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketCombatEvent.Event;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class ModEvents 
{
	@SubscribeEvent
    public void onRightClickWithMilkBucket(PlayerInteractEvent.RightClickItem event)
    {
        ItemStack itemStack = event.getItemStack();
        World world = event.getWorld();

        if (itemStack.getItem() == Items.MILK_BUCKET) 
        {
            RayTraceResult rayTraceResult = event.getEntityPlayer().rayTrace(5.0D, 1.0F);
            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) 
            {
                BlockPos pos = rayTraceResult.getBlockPos().offset(rayTraceResult.sideHit);
                if (world.isAirBlock(pos) || world.getBlockState(pos).getBlock().isReplaceable(world, pos)) 
                {
                    world.setBlockState(pos, ModFluids.liquid_milk.getBlock().getDefaultState(), 11);

                    if (!event.getEntityPlayer().capabilities.isCreativeMode) 
                    {
                        itemStack.shrink(1);
                        event.getEntityPlayer().setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BUCKET));
                    }

                    event.setCanceled(true);
                    event.setCancellationResult(EnumActionResult.SUCCESS);
                }
            }
        } 
        else 
        {
            // Handle other buckets
            if (!world.isRemote) 
            {
                RayTraceResult rayTraceResult = event.getEntityPlayer().rayTrace(5.0D, 1.0F);
                if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
                {
                    BlockPos pos = rayTraceResult.getBlockPos().offset(rayTraceResult.sideHit);
                    FluidActionResult result = FluidUtil.tryPlaceFluid(event.getEntityPlayer(), world, pos, itemStack, null);
                    if (result.isSuccess()) 
                    {
                        // If the bucket was placed successfully, update the player's inventory
                        event.getEntityPlayer().setHeldItem(EnumHand.MAIN_HAND, result.getResult());
                        event.setCanceled(true);
                        event.setCancellationResult(EnumActionResult.SUCCESS);
                    }
                }
            }
        }
    }
	
	@SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {
		try
		{
			World world = event.getWorld();
	        BlockPos pos = event.getTarget().getBlockPos();

	        if (!world.isRemote && pos != null) 
	        {
	            if (world.getBlockState(pos).getBlock() instanceof IFluidBlock) 
	            {
	                IFluidBlock fluidBlock = (IFluidBlock) world.getBlockState(pos).getBlock();
	                Fluid fluid = fluidBlock.getFluid();

	                if (fluid == ModFluids.liquid_milk) {
	                    event.setFilledBucket(new ItemStack(Items.MILK_BUCKET));
	                    world.setBlockToAir(pos);
	                    event.setResult(net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW);
	                }
	            }
	        }
		}
		catch(Exception ex)
		{
			
		}
    }
	
	@SubscribeEvent
    public void onItemUseStart(LivingEntityUseItemEvent.Start event)
    {
		if (event.getEntityLiving() instanceof EntityPlayer) 
		{
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (event.getItem().getItem() == Items.MILK_BUCKET) 
            {
                event.setCanceled(true);
            }
        }
    }
	
	@SubscribeEvent
    public void onEntityInteractSpecific(EntityInteractSpecific event)
    {
		if (event.getTarget() instanceof EntityCow)
		{
			ItemStack itemStack = event.getItemStack();
            if (itemStack.getItem() == Items.GLASS_BOTTLE)
            {
            	if (!event.getWorld().isRemote) 
            	{
                    event.getEntityPlayer().setHeldItem(event.getHand(), new ItemStack(ModItems.MILK_BOTTLE));
            	}
                event.getWorld().playSound(event.getEntityPlayer(), event.getPos(), SoundEvents.ENTITY_COW_MILK, SoundCategory.BLOCKS, 1.0F, 1.0F);

                event.setCancellationResult(EnumActionResult.SUCCESS);
                event.setCanceled(true);
            }
		}
    }
}
