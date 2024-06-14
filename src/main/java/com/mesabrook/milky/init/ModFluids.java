package com.mesabrook.milky.init;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.blocks.BlockLiquidMilk;
import com.mesabrook.milky.blocks.FluidMilk;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Locale;

@Mod.EventBusSubscriber(modid = Milky.MOD_ID)
public class ModFluids 
{
	// Fluids go here.
    public static Fluid liquid_milk;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        final IForgeRegistry<Block> registry = event.getRegistry();

        // Milk
        liquid_milk = fluidMilk("liquid_milk", 0xffffff);
        liquid_milk.setDensity(1050);
        liquid_milk.setRarity(EnumRarity.COMMON);
        registerClassicBlock(registry, liquid_milk);
    }

    private static FluidMilk fluidMilk(String name, int color) 
    {
        FluidMilk fluid = new FluidMilk(name, color);
        return registerFluid(fluid);
    }

    protected static <T extends Fluid> T registerFluid(T fluid) 
    {
        fluid.setUnlocalizedName(Milky.MOD_PREFIX + fluid.getName());
        FluidRegistry.registerFluid(fluid);
        
        if(!(fluid instanceof FluidMilk))
        {
        	FluidRegistry.addBucketForFluid(fluid);
        }

        return fluid;
    }

    public static BlockFluidBase registerClassicBlock(IForgeRegistry<Block> registry, Fluid fluid) 
    {
        return registerBlock(registry, new BlockLiquidMilk(fluid, net.minecraft.block.material.Material.WATER), fluid.getName());
    }

    protected static <T extends Block> T registerBlock(IForgeRegistry<Block> registry, T block, String name) 
    {
        if(!name.equals(name.toLowerCase(Locale.US))) 
        {
            throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block: %s", name));
        }

        String prefixedName = Milky.MOD_PREFIX + name;
        block.setUnlocalizedName(prefixedName);

        register(registry, block, name);
        return block;
    }

    protected static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T object, String name) 
    {
        object.setRegistryName(new ResourceLocation(Milky.MOD_ID, "fluid." + name));
        registry.register(object);
        return object;
    }
}
