package dev.cg360.mc.nukkittables.basetypes;

import cn.nukkit.block.BlockAir;
import cn.nukkit.item.Item;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableContext;

import java.util.Optional;

public abstract class TableEntry {

    public final float DEFAULT_LUCK = 0;

    protected String type;

    protected TableCondition[] conditions;

    protected int weight;
    protected int quality;

    public final Optional<Item> rollEntry(TableContext context){
        return Utility.compileConditions(conditions, context) ? rollEntryItems() : Optional.empty();
    }

    protected abstract Optional<Item> rollEntryItems();

    public String getType() { return type; }
    public TableCondition[] getConditions() { return conditions; }
    public int getBaseWeight() { return weight; }
    public int getQuality() { return quality; }

    public int getModifiedWeight(){ return getModifiedWeight(DEFAULT_LUCK); }
    public int getModifiedWeight(float luck){
        return (int) Math.floor(weight + (quality * luck));
    }

    public void setBaseWeight(int weight) { this.weight = weight; }
    public void setQuality(int quality) { this.quality = quality; }
}
