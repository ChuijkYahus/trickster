package dev.enjarai.trickster.spell.tricks.math;

import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.execution.source.SpellSource;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.tricks.Trick;
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException;
import dev.enjarai.trickster.spell.tricks.blunder.MissingInputsBlunder;

import java.util.List;

public class MinTrick extends Trick {
    public MinTrick() {
        super(Pattern.of(3, 7, 5));
    }

    @Override
    public Fragment activate(SpellSource ctx, List<Fragment> fragments) throws BlunderException {
        var list = supposeInput(fragments, 0).flatMap(l -> supposeType(l, FragmentType.LIST));

        if (list.isPresent()) {
            fragments = list.get().fragments();
        }

        return new NumberFragment(fragments.stream()
                .mapToDouble(frag -> expectType(frag, FragmentType.NUMBER).number())
                .min()
                .orElseThrow(() -> new MissingInputsBlunder(this)));
    }
}
