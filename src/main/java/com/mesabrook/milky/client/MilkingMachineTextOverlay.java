package com.mesabrook.milky.client;

import com.mesabrook.milky.blocks.BlockMilkingMachine;
import com.mesabrook.milky.blocks.TileEntityMilkingMachine;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MilkingMachineTextOverlay 
{
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public void renderOverlay(RenderGameOverlayEvent.Post event)
    {
		if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
		{
			Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.player;
            World world = mc.world;
            RayTraceResult result = player.rayTrace(mc.playerController.getBlockReachDistance(), event.getPartialTicks());
		
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) 
            {
            	BlockPos pos = result.getBlockPos();
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                
                if (block instanceof BlockMilkingMachine)
                {
                	TileEntity tileEntity = world.getTileEntity(pos);
                	if (tileEntity instanceof TileEntityMilkingMachine)
                	{
                		renderMilkingMachineOverlay((TileEntityMilkingMachine) tileEntity, event.getResolution().getScaledWidth(), event.getResolution().getScaledHeight());
                	}
                }
            }
		}
    }
	
	private void renderMilkingMachineOverlay(TileEntityMilkingMachine machine, int screenWidth, int screenHeight) 
	{
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRenderer;
        int x = screenWidth / 2 + 10;
        int y = screenHeight / 2 - 30;

        fontRenderer.drawStringWithShadow(TextFormatting.YELLOW + "Milking Machine", x, y, 0xFFFFFF);
        y += 10;
        fontRenderer.drawStringWithShadow(TextFormatting.GRAY + "Energy Stored: " + machine.getEnergyStored() + "/" + machine.getMaxEnergy() + " FE", x, y, 0xFFFFFF);
        y += 10;
        fontRenderer.drawStringWithShadow(TextFormatting.GRAY + "Energy Usage: " + machine.getEnergyUsage() + " FE/t", x, y, 0xFFFFFF);
        y += 10;
        fontRenderer.drawStringWithShadow(TextFormatting.GRAY + "Milk Tank Level: " + machine.milkTank.getFluidAmount() + " mB", x, y, 0xFFFFFF);
    }
}
