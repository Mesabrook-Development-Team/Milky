package com.mesabrook.milky.proxy;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.client.MilkingMachineTextOverlay;
import com.mesabrook.milky.client.SolarGeneratorTextOverlay;
import com.mesabrook.milky.init.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{

	@Override
	public void Preinit() 
	{

	}

	@Override
	public void Init()  
	{
		Milky.logger.info("Registered client-side text overlays.");
		MinecraftForge.EVENT_BUS.register(new MilkingMachineTextOverlay());
		MinecraftForge.EVENT_BUS.register(new SolarGeneratorTextOverlay());
	}
	
	public void registerItemRenderer(Item item, int meta, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) 
	{
		registerFluidModel(ModFluids.liquid_milk);
		registerFluidModel(ModFluids.liquid_chocolate_milk);
		registerFluidModel(ModFluids.liquid_strawberry_milk);
		registerFluidModel(ModFluids.liquid_caramel_milk);
	}

	private static final String FLUID_MODEL_PATH = Milky.MOD_PREFIX + "fluid_block";

	private static void registerFluidModel(final Fluid fluid) 
	{
		final Item item = Item.getItemFromBlock((Block) fluid.getBlock());
		assert item != Items.AIR;

		ModelBakery.registerItemVariants(item);

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, fluid.getName());
		ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);
		ModelLoader.setCustomStateMapper((Block) fluid.getBlock(), new StateMapperBase() 
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(final IBlockState state) 
			{
				return modelResourceLocation;
			}
		});
	}
}
