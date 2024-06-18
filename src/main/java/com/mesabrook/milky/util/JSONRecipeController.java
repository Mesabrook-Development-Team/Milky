package com.mesabrook.milky.util;

import java.util.List;

import com.mesabrook.milky.Milky;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@EventBusSubscriber
public class JSONRecipeController 
{
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
    	try
    	{
    		ResourceLocation tech = new ResourceLocation("milky:milking_machine_tech");
    		ResourceLocation no_tech = new ResourceLocation("milky:milking_machine_no_tech");
    		
    		IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();
    		
    		String oreName = "ingotSteel";
    		List<ItemStack> ores = OreDictionary.getOres(oreName);
    	
    		if(!ores.isEmpty())
    		{
    			// Tech mod installed, disable no_tech recipe.
    			modRegistry.remove(no_tech);
    			Milky.logger.info("ingotSteel found. Tech mod is likely installed. Vanilla-friendly milking machine recipe disabled.");
    			Milky.logger.info("ingotSteel has been found in the following locations below:");
    			for (ItemStack ore : ores) 
    			{
                    Milky.logger.info("- " + ore.getDisplayName());
                }
    		}
    		else
    		{
    			// No tech mods found, disable tech recipe.
    			modRegistry.remove(tech);
    			Milky.logger.info("ingotSteel not found. No tech mods are installed. Milking machine recipe that requires steel is disabled.");
    		}
    	}
    	catch(Exception ex)
    	{
    		Milky.logger.error("ERROR IN RECIPE CONTROLLER");
    		ex.printStackTrace();
    	}
    }
}
