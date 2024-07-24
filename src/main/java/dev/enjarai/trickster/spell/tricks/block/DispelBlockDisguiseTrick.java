package dev.enjarai.trickster.spell.tricks.block;

import dev.enjarai.trickster.cca.ModChunkCumponents;
import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.execution.source.SpellSource;
import dev.enjarai.trickster.spell.fragment.BooleanFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.tricks.blunder.BlockUnoccupiedBlunder;
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException;
import net.minecraft.world.chunk.EmptyChunk;

import java.util.List;

public class DispelBlockDisguiseTrick extends AbstractBlockDisguiseTrick {
    public DispelBlockDisguiseTrick() {
        super(Pattern.of(0, 4, 8, 5, 2, 4, 6, 3, 0, 1, 4, 7, 8));
    }

    @Override
    public Fragment activate(SpellSource ctx, List<Fragment> fragments) throws BlunderException {
        var pos = expectInput(fragments, FragmentType.VECTOR, 0);
        var blockPos = pos.toBlockPos();

        if (ctx.getWorld().getBlockState(blockPos).isAir()) {
            throw new BlockUnoccupiedBlunder(this, pos);
        }

        var chunk = ctx.getWorld().getChunk(blockPos);

        if (!(chunk instanceof EmptyChunk)) {
            ctx.useMana(this, 10);

            var component = ModChunkCumponents.SHADOW_DISGUISE_MAP.get(chunk);

            if (component.clearFunnyState(blockPos)) {
                updateShadow(ctx, blockPos);
                return BooleanFragment.TRUE;
            }
        }


        return BooleanFragment.FALSE;
    }
}
