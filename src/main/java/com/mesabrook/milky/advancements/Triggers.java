package com.mesabrook.milky.advancements;

import com.google.common.collect.Lists;
import com.mesabrook.milky.Milky;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Triggers 
{
	private static final List<BasicTrigger> TRIGGERS = Lists.newArrayList();
	
	// Milking
	public static final BasicTrigger EXOTIC_MILK = register("exotic_milk");
	
	
	
	private static BasicTrigger register(String name)
    {
        BasicTrigger trigger = new BasicTrigger(new ResourceLocation(Milky.MOD_ID, name));
        TRIGGERS.add(trigger);
        return trigger;
    }

    public static void trigger(IModTrigger trigger, EntityPlayer player)
    {
        if(player instanceof EntityPlayerMP)
        {
            trigger.trigger((EntityPlayerMP) player);
        }
    }

    @SuppressWarnings("deprecation")
    public static void init()
    {
        Method method;
        try
        {
            method = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
            method.setAccessible(true);
            for(BasicTrigger TRIGGER : TRIGGERS)
            {
                method.invoke(null, TRIGGER);
            }
        }
        catch(SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            Milky.logger.error(e);
        }
    }
}
