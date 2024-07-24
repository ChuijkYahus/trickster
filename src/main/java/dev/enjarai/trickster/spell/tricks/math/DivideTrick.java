package dev.enjarai.trickster.spell.tricks.math;

import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.execution.source.SpellSource;
import dev.enjarai.trickster.spell.fragment.DivisibleFragment;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.tricks.Trick;
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException;
import dev.enjarai.trickster.spell.tricks.blunder.MissingInputsBlunder;

import java.util.List;

public class DivideTrick extends Trick {
    public DivideTrick() {
        super(Pattern.of(0, 1, 2, 4, 6, 7, 8));
    }

    @Override
    public Fragment activate(SpellSource ctx, List<Fragment> fragments) throws BlunderException {
        var list = supposeInput(fragments, 0).flatMap(l -> supposeType(l, FragmentType.LIST));

        if (list.isPresent()) {
            fragments = list.get().fragments();
        }

        return fragments.stream()
                .map(a -> expectType(a, DivisibleFragment.class))
                .reduce(DivisibleFragment::divide)
                .orElseThrow(() -> new MissingInputsBlunder(this));
    }
}
