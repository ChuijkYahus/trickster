package dev.enjarai.trickster.spell.trick;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.enjarai.trickster.spell.Fragment;
import dev.enjarai.trickster.spell.Pattern;
import dev.enjarai.trickster.spell.SpellContext;
import dev.enjarai.trickster.spell.blunder.BlunderException;
import dev.enjarai.trickster.spell.type.Signature;

public abstract class DistortionTrick<T extends DistortionTrick<T>> extends Trick<T> {
    private static int CACHE_SIZE = 20;

    private Map<Fragment[], Fragment> cache = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Fragment[], Fragment> eldest) {
            return size() > CACHE_SIZE;
        }
    };

    public DistortionTrick(Pattern pattern) {
        super(pattern);
    }

    public DistortionTrick(Pattern pattern, List<Signature<T, Fragment>> handlers) {
        super(pattern, handlers);
    }

    public DistortionTrick(Pattern pattern, Signature<T, Fragment> primary) {
        super(pattern, primary);
    }

    @Override
    public Fragment activate(SpellContext ctx, List<Fragment> fragments) throws BlunderException {
        var fragmentArray = fragments.toArray(new Fragment[fragments.size()]);
        var fragment = cache.get(fragmentArray);

        if (fragment == null) {
            fragment = super.activate(ctx, fragments);
            cache.put(fragmentArray, fragment);
        }
        
        return fragment;
    }
}
