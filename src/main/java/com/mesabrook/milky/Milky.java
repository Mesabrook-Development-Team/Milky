package com.mesabrook.milky;

import com.mesabrook.milky.config.ModConfig;
import com.mesabrook.milky.handlers.ModEvents;
import com.mesabrook.milky.init.ModFluids;
import com.mesabrook.milky.init.ModItems;
import com.mesabrook.milky.item.ItemMilkBottle;
import com.mesabrook.milky.proxy.CommonProxy;
import com.mesabrook.milky.recipes.IERecipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Milky.MOD_ID,
        name = Milky.MOD_NAME,
        version = Milky.VERSION,
        acceptedMinecraftVersions = Milky.ACCEPTED_VERSIONS)
public class Milky
{
    public static final String MOD_ID = "milky";
    public static final String MOD_NAME = "Milky";
    public static final String MOD_PREFIX = MOD_ID + ":";
    public static final String VERSION = "1.0.1";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final String CLIENT_PROXY_CLASS = "com.mesabrook.milky.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.mesabrook.milky.proxy.ServerProxy";

    @Instance(Milky.MOD_ID)
    public static Milky instance;

    @SidedProxy(clientSide = Milky.CLIENT_PROXY_CLASS, serverSide = Milky.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final Logger logger = LogManager.getLogger(Milky.MOD_ID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger.info("[" + MOD_NAME + "] Mod Pre-Initialization.");
        logger.info("[" + MOD_NAME + "] Registering Milky config.");
        MinecraftForge.EVENT_BUS.register(new ModConfig());

        proxy.Preinit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.Init();
        logger.info("[" + MOD_NAME + "] Mod Initialization.");
        
        if(Loader.isModLoaded("immersiveengineering"))
        {
        	logger.info("[" + MOD_NAME + "] Immersive Engineering detected. Machine recipes added.");
        	IERecipes.registerMachineRecipes();
        }
        
        MinecraftForge.EVENT_BUS.register(new ModEvents());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	logger.info("[" + MOD_NAME + "] OreDict: Registering Milk Bottles...");
    	OreDictionary.registerOre("listAllmilk", ModItems.MILK_BOTTLE);
    	OreDictionary.registerOre("listAllchocolatemilk", ModItems.CHOC_MILK_BOTTLE);
    	OreDictionary.registerOre("listAllstrawberrymilk", ModItems.STRAWB_MILK_BOTTLE);
    	OreDictionary.registerOre("listAllcaramelmilk", ModItems.CARAMEL_MILK_BOTTLE);
    	logger.info("[" + MOD_NAME + "] OreDict: Registered Milk Bottles.");
    	
    	try
    	{
    		logger.info("[" + MOD_NAME + "] OreDict: Attempting to register Milk universal bucket...");
        	ItemStack milkBucket = FluidUtil.getFilledBucket(new FluidStack(ModFluids.liquid_milk, Fluid.BUCKET_VOLUME));
        	OreDictionary.registerOre("listAllmilk", milkBucket);
        	logger.info("[" + MOD_NAME + "] OreDict: Registered Milk Bucket.");
    	}
    	catch (Exception ex)
    	{
    		logger.error("[" + MOD_NAME + "] OreDict ERROR: Unable to register Milk Bucket " + ex);
    		ex.printStackTrace();
    	}
    }
}
