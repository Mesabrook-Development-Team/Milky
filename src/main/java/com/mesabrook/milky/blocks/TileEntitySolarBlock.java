package com.mesabrook.milky.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntitySolarBlock extends TileEntity implements ITickable
{
	private static final int ENERGY_GENERATION = 100; // FE generated per tick
    
	private EnergyStorage energyStorage = new EnergyStorage(10000)
    {
		@Override
        public int receiveEnergy(int maxReceive, boolean simulate)
        {
            int received = super.receiveEnergy(maxReceive, simulate);
            markDirty();
            return received;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate)
        {
            int extracted = super.extractEnergy(maxExtract, simulate);
            markDirty();
            return extracted;
        }
    };

    private boolean status = false;
    
    @Override
    public void update() 
    {
        if (world != null && !world.isRemote) 
        {
            BlockPos pos = getPos();
            if (world.canSeeSky(pos.up()) && world.isDaytime()) 
            {
                energyStorage.receiveEnergy(ENERGY_GENERATION, false);
                setGeneratorStatus(true);
                distributeEnergy();
            }
            else
            {
            	setGeneratorStatus(false);
            }
            distributeEnergy();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            
            markDirty();
        }
    }
    
    private void onEnergyChange() 
    {
        markDirty();
        if (world != null && !world.isRemote) 
        {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            if(status) distributeEnergy();
        }
    }
    
    public boolean setGeneratorStatus(boolean in)
    {
    	markDirty();
    	return this.status = in;
    }
    
    public String getGeneratorStatus()
    {
    	if(this.status)
    	{
    		return "Yes";
    	}
    	else
    	{
    		return "No";
    	}
    }
    
    public int getEnergyStored()
    {
    	return energyStorage.getEnergyStored();
    }
    
    public int getEnergyGen()
    {
    	return ENERGY_GENERATION;
    }
    
    private void distributeEnergy() 
    {
        for (EnumFacing facing : EnumFacing.values()) 
        {
            if (facing != EnumFacing.UP) 
            {
                TileEntity tile = world.getTileEntity(pos.offset(facing));
                if (tile != null) 
                {
                    IEnergyStorage handler = tile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                    if (handler != null && handler.canReceive()) 
                    {
                        int energyToExtract = 100; // Adjust this value as needed
                        int extracted = energyStorage.extractEnergy(energyToExtract, true);
                        int received = handler.receiveEnergy(extracted, false);
                        energyStorage.extractEnergy(received, false);
                    }
                }
            }
        }
    }

    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("energy", CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
        compound.setBoolean("status", status);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) 
    {
        super.readFromNBT(compound);
        if (compound.hasKey("energy")) 
        {
            CapabilityEnergy.ENERGY.readNBT(energyStorage, null, compound.getTag("energy"));
        } 
        else 
        {
            energyStorage = new EnergyStorage(10000);
        }
        this.status = compound.getBoolean("status");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
    {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
    {
        if (capability == CapabilityEnergy.ENERGY) 
        {
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        return super.getCapability(capability, facing);
    }
    
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() 
    {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new SPacketUpdateTileEntity(pos, 1, tag);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) 
    {
        readFromNBT(pkt.getNbtCompound());
    }
}
