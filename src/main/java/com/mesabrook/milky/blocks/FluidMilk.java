package com.mesabrook.milky.blocks;

import com.mesabrook.milky.Milky;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidMilk extends Fluid 
{
    public static ResourceLocation milk_still = new ResourceLocation(Milky.MOD_ID, "blocks/milk");
    public static ResourceLocation milk_flowing = new ResourceLocation(Milky.MOD_ID, "blocks/milk_flow");

    public final int color;

    public FluidMilk(String fluidName, int color) 
    {
        this(fluidName, color, milk_still, milk_flowing);
    }

    public FluidMilk(String fluidName, int color, ResourceLocation still, ResourceLocation flowing) 
    {
        super(fluidName, still, flowing);

        // Alpha fix
        if(((color >> 24) & 0xFF) == 0) 
        {
            color |= 0xFF << 24;
        }
        this.color = color;
    }


    @Override
    public int getColor() 
    {
        return color;
    }

    @Override
    public String getLocalizedName(FluidStack stack) 
    {
        String s = this.getUnlocalizedName();
        return s == null ? "" : I18n.translateToLocal(s + ".name");
    }
}