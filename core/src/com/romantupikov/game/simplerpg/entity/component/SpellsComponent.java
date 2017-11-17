package com.romantupikov.game.simplerpg.entity.component;

import com.romantupikov.game.simplerpg.entity.spell.Spell;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by hvitserk on 17-Nov-17.
 */

public class SpellsComponent implements Component {
    private Map<String, Spell> spells;

    public SpellsComponent() {
        spells = new HashMap<String, Spell>();
    }

    public void addSpell(String name, Spell spell) {
        spells.put(name, spell);
    }

    public void removeSpell(String name) {
        spells.remove(name);
    }

    public Spell getSpell(String name) {
        return spells.get(name);
    }
}
