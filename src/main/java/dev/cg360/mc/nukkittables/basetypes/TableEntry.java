package dev.cg360.mc.nukkittables.basetypes;

import cn.nukkit.block.BlockAir;
import cn.nukkit.item.Item;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableContext;

public abstract class TableEntry {

    public final int DEFAULT_LUCK = 1;

    protected String type;

    protected TableCondition[] conditions;

    protected int weight;
    protected int quality;

    public final Item rollEntry(TableContext context){
        return Utility.compileConditions(conditions, context) ? rollEntryItems() : new BlockAir().toItem();
    }

    protected abstract Item rollEntryItems();

    public String getType() { return type; }
    public int getBaseWeight() { return weight; }
    public int getQuality() { return quality; }

    public int getModifiedWeight(){ return getModifiedWeight(DEFAULT_LUCK); }
    public int getModifiedWeight(int luck){
        return (int) Math.floor(weight + (quality * luck));
    }

    public void setBaseWeight(int weight) { this.weight = weight; }
    public void setQuality(int quality) { this.quality = quality; }
}
