package dev.enjarai.trickster.spell.tricks.vector;

import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.execution.source.SpellSource;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.tricks.Trick;
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException;

import java.util.List;

public class ExtractYTrick extends Trick {
    public ExtractYTrick() {
        super(Pattern.of(0, 4, 7));
    }

    @Override
    public Fragment activate(SpellSource ctx, List<Fragment> fragments) throws BlunderException {
        var vector = expectInput(fragments, FragmentType.VECTOR, 0);

        return new NumberFragment(vector.vector().y());
    }
}
