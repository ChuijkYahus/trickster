package dev.enjarai.trickster.spell.tricks.projectile;

import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.execution.source.SpellSource;
import dev.enjarai.trickster.spell.tricks.blunder.BlunderException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.joml.Vector3dc;

import java.util.List;

public class SummonTntTrick extends AbstractProjectileTrick {
    public SummonTntTrick() {
        super(Pattern.of(0, 2, 8, 6, 0, 1, 4, 7, 8, 5, 4, 3, 0, 4, 8));
    }

    @Override
    protected Entity makeProjectile(SpellSource ctx, Vector3dc pos, ItemStack stack, List<Fragment> extraInputs) throws BlunderException {
        return new TntEntity(ctx.getWorld(), pos.x(), pos.y(), pos.z(), null);
    }

    @Override
    protected boolean isValidItem(Item item) {
        return item.equals(Items.TNT);
    }
}
