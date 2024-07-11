package com.mesabrook.milky.blocks;

import com.mesabrook.milky.init.ModFluids;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TileEntityMilkingMachine extends TileEntity
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static final int MAX_ENERGY = 10000;
    public static final int ENERGY_PER_OPERATION = 256;
    public static final int MAX_MILK = 8000;
    public static final int MILK_PRODUCTION_AMOUNT = 50;

    public EnergyStorage energyStorage = new EnergyStorage(MAX_ENERGY)
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

    public FluidTank milkTank = new FluidTank(MAX_MILK)
    {
        @Override
        protected void onContentsChanged()
        {
            markDirty();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
        }
    };
    
    private void onEnergyChange() 
    {
        markDirty();
        if (world != null && !world.isRemote) 
        {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
        }
    }

    public void produceMilk()
    {
        if (energyStorage.getEnergyStored() >= ENERGY_PER_OPERATION && milkTank.getFluidAmount() + MILK_PRODUCTION_AMOUNT <= milkTank.getCapacity())
        {
            energyStorage.extractEnergy(ENERGY_PER_OPERATION, false);
            milkTank.fill(new FluidStack(ModFluids.liquid_milk, MILK_PRODUCTION_AMOUNT), true);
            if(world.rand.nextInt(25) >= 23)
            {
            	world.playSound(null, pos, SoundEvents.ENTITY_COW_MILK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            markDirty();
        }
        else
        {
            if (milkTank.getFluidAmount() >= milkTank.getCapacity())
            {
                ejectMilk();
            }
        }
    }

    public int getMilkAmount()
    {
        return milkTank.getFluidAmount();
    }
    
    public int getEnergyStored()
    {
    	return energyStorage.getEnergyStored();
    }
    
    public int getEnergyUsage()
    {
    	return ENERGY_PER_OPERATION;
    }
    
    public int getMaxEnergy()
    {
    	return MAX_ENERGY;
    }

    public void drainMilk(int amount)
    {
        milkTank.drain(amount, true);
        markDirty();
    }
    
    public void makeEjectMilk()
    {
    	ejectMilk();
    }

    private void ejectMilk()
    {
        FluidStack milkStack = new FluidStack(ModFluids.liquid_milk, MILK_PRODUCTION_AMOUNT);
        for (EnumFacing side : EnumFacing.values())
        {
            TileEntity neighbor = world.getTileEntity(pos.offset(side));
            if (neighbor != null && neighbor.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite()))
            {
                IFluidHandler handler = neighbor.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
                if (handler != null)
                {
                    int filled = handler.fill(milkStack, true);
                    milkTank.drain(filled, true);
                    markDirty();
                    if (milkStack.amount <= 0)
                    {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY)
        {
            return true;
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY)
        {
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new IFluidHandler()
            {
                @Override
                public IFluidTankProperties[] getTankProperties()
                {
                    return milkTank.getTankProperties();
                }

                @Override
                public int fill(FluidStack resource, boolean doFill)
                {
                    return milkTank.fill(resource, doFill);
                }

                @Override
                public FluidStack drain(FluidStack resource, boolean doDrain)
                {
                    return milkTank.drain(resource, doDrain);
                }

                @Override
                public FluidStack drain(int maxDrain, boolean doDrain)
                {
                    return milkTank.drain(maxDrain, doDrain);
                }
            });
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("Energy", CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
        compound.setTag("Milk", milkTank.writeToNBT(new NBTTagCompound()));
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) 
    {
        super.readFromNBT(compound);
        if (compound.hasKey("Energy")) 
        {
            CapabilityEnergy.ENERGY.readNBT(energyStorage, null, compound.getTag("Energy"));
        } 
        else 
        {
            energyStorage = new EnergyStorage(MAX_ENERGY);
        }
        
        if (compound.hasKey("Milk")) 
        {
            milkTank.readFromNBT(compound.getCompoundTag("Milk"));
        } 
        else 
        {
            milkTank = new FluidTank(MAX_MILK);
        }
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
    
    @Override
    public NBTTagCompound getUpdateTag() {
    	return this.writeToNBT(new NBTTagCompound());
    }
    
    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
    	this.readFromNBT(tag);
    }
}
