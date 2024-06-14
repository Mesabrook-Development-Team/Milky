package com.mesabrook.milky.init;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.blocks.BlockCaramelMilk;
import com.mesabrook.milky.blocks.BlockChocolateMilk;
import com.mesabrook.milky.blocks.BlockLiquidMilk;
import com.mesabrook.milky.blocks.BlockStrawberryMilk;
import com.mesabrook.milky.blocks.FluidCaramelMilk;
import com.mesabrook.milky.blocks.FluidChocolateMilk;
import com.mesabrook.milky.blocks.FluidMilk;
import com.mesabrook.milky.blocks.FluidStrawberryMilk;

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
    public static Fluid liquid_chocolate_milk;
    public static Fluid liquid_strawberry_milk;
    public static Fluid liquid_caramel_milk;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        final IForgeRegistry<Block> registry = event.getRegistry();

        // Milk
        liquid_milk = fluidMilk("liquid_milk", 0xffffff);
        liquid_milk.setDensity(1050);
        liquid_milk.setRarity(EnumRarity.COMMON);
        registerClassicBlock(registry, liquid_milk);
        
        // Chocolate Milk
        liquid_chocolate_milk = fluidChocolateMilk("liquid_chocolate_milk", 0x976F4C);
        liquid_chocolate_milk.setDensity(1050);
        liquid_chocolate_milk.setRarity(EnumRarity.COMMON);
        registerClassicBlock(registry, liquid_chocolate_milk);
        
        // Strawberry Milk
        liquid_strawberry_milk = fluidStrawberryMilk("liquid_strawberry_milk", 0xF9B5B5);
        liquid_strawberry_milk.setDensity(1050);
        liquid_strawberry_milk.setRarity(EnumRarity.COMMON);
        registerClassicBlock(registry, liquid_strawberry_milk);
        
        // Caramel Milk
        liquid_caramel_milk = fluidCaramelMilk("liquid_caramel_milk", 0xCFA74E);
        liquid_caramel_milk.setDensity(1050);
        liquid_caramel_milk.setRarity(EnumRarity.COMMON);
        registerClassicBlock(registry, liquid_caramel_milk);
    }

    private static FluidMilk fluidMilk(String name, int color) 
    {
        FluidMilk fluid = new FluidMilk(name, color);
        return registerFluid(fluid);
    }
    
    private static FluidChocolateMilk fluidChocolateMilk(String name, int color) 
    {
    	FluidChocolateMilk fluid = new FluidChocolateMilk(name, color);
        return registerFluid(fluid);
    }
    
    private static FluidStrawberryMilk fluidStrawberryMilk(String name, int color) 
    {
    	FluidStrawberryMilk fluid = new FluidStrawberryMilk(name, color);
        return registerFluid(fluid);
    }
    
    private static FluidCaramelMilk fluidCaramelMilk(String name, int color) 
    {
    	FluidCaramelMilk fluid = new FluidCaramelMilk(name, color);
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
    	if(fluid == liquid_milk) return registerBlock(registry, new BlockLiquidMilk(fluid, net.minecraft.block.material.Material.WATER), fluid.getName());
    	if(fluid == liquid_chocolate_milk) return registerBlock(registry, new BlockChocolateMilk(fluid, net.minecraft.block.material.Material.WATER), fluid.getName());
    	if(fluid == liquid_strawberry_milk) return registerBlock(registry, new BlockStrawberryMilk(fluid, net.minecraft.block.material.Material.WATER), fluid.getName());
    	if(fluid == liquid_caramel_milk) return registerBlock(registry, new BlockCaramelMilk(fluid, net.minecraft.block.material.Material.WATER), fluid.getName());
    	
    	return null;
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
