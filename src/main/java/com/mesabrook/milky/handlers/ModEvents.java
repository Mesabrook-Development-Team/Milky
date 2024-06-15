package com.mesabrook.milky.handlers;

import com.mesabrook.milky.config.ModConfig;
import com.mesabrook.milky.init.ModFluids;
import com.mesabrook.milky.init.ModItems;

import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntitySheep;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class ModEvents 
{	
	@SubscribeEvent
    public void onEntityInteractSpecific(EntityInteractSpecific event)
    {
		if (event.getTarget() instanceof EntityCow || event.getTarget() instanceof EntitySheep || event.getTarget() instanceof EntityLlama)
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
            
            if(itemStack.getItem() == Items.BUCKET)
            {
            	if(!event.getWorld().isRemote)
            	{
            		event.getEntityPlayer().setHeldItem(event.getHand(), FluidUtil.getFilledBucket(new FluidStack(ModFluids.liquid_milk, Fluid.BUCKET_VOLUME)));
            	}
            	
                event.getWorld().playSound(event.getEntityPlayer(), event.getPos(), SoundEvents.ENTITY_COW_MILK, SoundCategory.BLOCKS, 1.0F, 1.0F);

                event.setCancellationResult(EnumActionResult.SUCCESS);
                event.setCanceled(true);
            }
		}
    }
}
