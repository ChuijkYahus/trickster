package dev.enjarai.trickster.block.cauldron;

import dev.enjarai.trickster.item.ModItems;
import dev.enjarai.trickster.item.component.ModComponents;
import dev.enjarai.trickster.item.component.FragmentComponent;
import dev.enjarai.trickster.spell.SpellPart;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EraseSpellCauldronBehavior implements CauldronBehavior {
    @Override
    public ActionResult interact(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        stack.remove(ModComponents.WRITTEN_SCROLL_META);
        stack.set(ModComponents.FRAGMENT, new FragmentComponent(new SpellPart()));
        player.setStackInHand(hand, stack.withItem(ModItems.SCROLL_AND_QUILL));
        LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        return ActionResult.SUCCESS;
    }
}
