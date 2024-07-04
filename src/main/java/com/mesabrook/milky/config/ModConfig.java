package com.mesabrook.milky.config;

import com.mesabrook.milky.Milky;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Milky.MOD_ID, category = "", name = "Milky")
@Config.LangKey("milky.config.title")
public class ModConfig {
    @Config.Comment({"General settings"})
    public static General GENERAL = new General();

    public static class General
    {
    	@Comment("Should the flavored milks (chocolate, strawberry, and caramel) give potion effects when used? [default: true]")
    	public boolean flavoredMilkPotionEffects = true;
    	
    	@Comment("Should the Milking Machine require a Redstone signal to function when provided with energy? [default: true]")
    	public boolean milkingMachineRequiresRedstone = true;
    }

    @Mod.EventBusSubscriber(modid = Milky.MOD_ID)
    private static class EventHandler 
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) 
        {
            if (event.getModID().equals(Milky.MOD_ID)) 
            {
                ConfigManager.sync(Milky.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
