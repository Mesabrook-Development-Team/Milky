package com.mesabrook.milky.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.mesabrook.milky.Milky;
import com.mesabrook.milky.handlers.IHasModel;
import com.mesabrook.milky.init.ModBlocks;
import com.mesabrook.milky.init.ModFluids;
import com.mesabrook.milky.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMilkingMachine extends Block implements IHasModel
{
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
	
	public BlockMilkingMachine(String name)
	{
		super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.TOOLS);
		setHarvestLevel("pickaxe", 0);
		setHardness(3.0F);
        setResistance(5.0F);
        setTickRandomly(true);
		
		ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() 
	{
		Milky.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");	
	}
	
    @Override
    public boolean hasTileEntity(IBlockState state) 
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) 
    {
        return new TileEntityMilkingMachine();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
    {
        return AABB;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) 
    {
        if (!worldIn.isRemote) 
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityMilkingMachine) 
            {
            	TileEntityMilkingMachine milkProducer = (TileEntityMilkingMachine) tileEntity;
                if (entityIn instanceof EntityCow || entityIn instanceof EntitySheep || entityIn instanceof EntityLlama) 
                {
                    milkProducer.produceMilk();
                }
            }
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            ItemStack heldItem = playerIn.getHeldItem(hand);

            if (tileEntity instanceof TileEntityMilkingMachine && heldItem.getItem() == Items.BUCKET)
            {
                TileEntityMilkingMachine milkProducer = (TileEntityMilkingMachine) tileEntity;

                if (milkProducer.getMilkAmount() >= 1000)
                {
                    milkProducer.drainMilk(1000);
                    ItemStack milkBucket = FluidUtil.getFilledBucket(new FluidStack(ModFluids.liquid_milk, Fluid.BUCKET_VOLUME));
                    
                    if (!playerIn.inventory.addItemStackToInventory(milkBucket))
                    {
                        playerIn.dropItem(milkBucket, false);
                    }

                    if(!playerIn.isCreative()) heldItem.shrink(1);
                    playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                    playerIn.swingArm(hand);
                    
                    return true;
                } 
            }
        }
        return false;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(TextFormatting.YELLOW + "A simple automatic milking machine. Requires FE to function.");
		tooltip.add(TextFormatting.YELLOW + "Has an 8000mB internal storage tank and can output to compatible tanks using pipes.");
	}
}
