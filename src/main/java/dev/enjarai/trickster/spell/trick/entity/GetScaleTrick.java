package dev.enjarai.trickster.spell.trick.entity;

import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.fragment.FragmentType;
import dev.enjarai.trickster.spell.fragment.NumberFragment;
import dev.enjarai.trickster.spell.trick.Trick;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.blunder.InvalidEntityBlunder;
import dev.enjarai.trickster.spell.blunder.UnknownEntityBlunder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.List;

import static dev.enjarai.trickster.spell.trick.entity.SetScaleTrick.SCALE_ID;

public class GetScaleTrick extends Trick {
    public GetScaleTrick() {
        super(Pattern.of(7, 6, 0, 1, 2, 8, 7));
    }

    @Override
    public Fragment activate(SpellContext ctx, List<Fragment> fragments) throws BlunderException {
        var target = expectInput(fragments, FragmentType.ENTITY, 0)
                .getEntity(ctx).orElseThrow(() -> new UnknownEntityBlunder(this));

        if (!(target instanceof LivingEntity livingEntity)) {
            throw new InvalidEntityBlunder(this);
        }

        var currentScale = 0d;
        if (livingEntity.getAttributes().hasModifierForAttribute(EntityAttributes.SCALE, SCALE_ID)) {
            currentScale = livingEntity.getAttributes().getModifierValue(EntityAttributes.SCALE, SCALE_ID);
        }
        return new NumberFragment(currentScale + 1);
    }
}
