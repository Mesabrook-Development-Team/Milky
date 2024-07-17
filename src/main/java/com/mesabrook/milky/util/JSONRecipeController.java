package com.mesabrook.milky.util;

import java.util.List;

import com.mesabrook.milky.Milky;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
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
    		
    		// Crafting buckets recipes
    		ResourceLocation choc = new ResourceLocation("milky:bucket_chocolate_milk");
    		ResourceLocation strwb = new ResourceLocation("milky:bucket_strawberry_milk");
    		ResourceLocation crml = new ResourceLocation("milky:bucket_caramel_milk");
    		
    		// Crafting bottle recipes
    		ResourceLocation milk_b = new ResourceLocation("milky:milk_bottle");
    		ResourceLocation choc_b = new ResourceLocation("milky:choc_milk_bottle");
    		ResourceLocation strwb_b = new ResourceLocation("milky:strawberry_milk_bottle");
    		ResourceLocation crml_b = new ResourceLocation("milky:caramel_milk_bottle");
    		
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
    			
    			if(Loader.isModLoaded("immersiveengineering"))
    			{
        			modRegistry.remove(choc);
        			modRegistry.remove(strwb);
        			modRegistry.remove(crml);
        			
        			modRegistry.remove(crml_b);
        			modRegistry.remove(milk_b);
        			modRegistry.remove(choc_b);
        			modRegistry.remove(strwb_b);
        			Milky.logger.info("Immersive Engineering has been detected, disabling vanilla-friendly flavored milk crafting recipes.");
    			}
    			else
    			{
    				Milky.logger.info("Immersive Engineering has NOT been detected. Flavored milk vanilla crafting recipes enabled.");
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
