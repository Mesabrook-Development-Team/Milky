package com.mesabrook.milky.init.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;

public class FluidMaterialMilk extends MaterialLiquid
{
	public FluidMaterialMilk()
	{
        super(MapColor.SNOW);
        this.setReplaceable();
	}
	
	@Override
    public boolean isLiquid()
    {
        return true;
    }
	
	@Override
    public boolean blocksMovement()
    {
        return false;
    }
	
	@Override
    public boolean isSolid()
    {
        return false;
    }
}
