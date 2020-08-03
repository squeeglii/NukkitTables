package dev.cg360.mc.nukkittables.types.entry;

import cn.nukkit.item.Item;
import com.google.gson.JsonObject;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.types.TableCondition;

import java.util.Optional;

public abstract class TableEntry {

    public final float DEFAULT_LUCK = 0;

    protected String type;

    protected TableCondition[] conditions;

    protected int weight;
    protected int quality;

    public TableEntry(String type, int weight, TableCondition... conditions){ this(type, weight, 1, conditions); }
    public TableEntry(String type, int weight, int quality, TableCondition... conditions) {
        this.type = type.toLowerCase();
        this.conditions = conditions;
        this.weight = weight;
        this.quality = quality;
    }

    public final Optional<Item> rollEntry(TableRollContext context){
        return Utility.compileConditions(conditions, context) ? rollEntryItems(context) : Optional.empty();
    }

    protected abstract Optional<Item> rollEntryItems(TableRollContext context);

    protected abstract boolean loadCustomPropertiesFromJson(JsonObject object);

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
