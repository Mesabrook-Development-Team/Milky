package com.mesabrook.milky.init.materials;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;

public class FluidMaterialStrawberryMilk extends MaterialLiquid
{
	public FluidMaterialStrawberryMilk()
	{
        super(MapColor.PINK);
        this.setReplaceable();
        this.setNoPushMobility();
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
