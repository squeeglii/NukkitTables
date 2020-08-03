package dev.cg360.mc.nukkittables.types.entry;

import cn.nukkit.item.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;

import java.util.ArrayList;
import java.util.Optional;

public abstract class TableEntry {

    public final float DEFAULT_LUCK = 0;

    protected String type;

    protected TableConditionExecutor[] conditions;

    protected int weight;
    protected int quality;

    protected TableEntry() { }
    public TableEntry(String type, int weight, TableConditionExecutor... conditions){ this(type, weight, 1, conditions); }
    public TableEntry(String type, int weight, int quality, TableConditionExecutor... conditions) {
        this.type = type.toLowerCase();
        this.conditions = conditions;
        this.weight = weight;
        this.quality = quality;
    }

    public final Optional<Item> rollEntry(TableRollContext context){
        return Utility.compileConditions(conditions, context) ? rollEntryItems(context) : Optional.empty();
    }

    protected abstract Optional<Item> rollEntryItems(TableRollContext context);

    protected final boolean loadPropertiesFromJson(JsonObject entryObject){
        JsonElement elementType = entryObject.get("type");
        JsonElement elementConditions = entryObject.get("conditions");
        JsonElement elementWeight = entryObject.get("weight");
        JsonElement elementQuality = entryObject.get("quality");

        if(elementType instanceof JsonPrimitive && elementWeight instanceof JsonPrimitive){
            JsonPrimitive primitiveType = (JsonPrimitive) elementType;
            JsonPrimitive primitiveWeight = (JsonPrimitive) elementWeight;

            if(primitiveType.isString() && primitiveWeight.isNumber()) {
                ArrayList<TableConditionExecutor> approvedConditions = new ArrayList<>();
                int q = 0;

                if(elementConditions instanceof JsonArray){
                    JsonArray arrayConditions = (JsonArray) elementConditions;
                    for(JsonElement condition: arrayConditions){
                        if(condition instanceof JsonObject){
                            TableConditionExecutor.loadConditionFromJsonObject((JsonObject) condition).ifPresent(approvedConditions::add);
                        }
                    }
                }

                if(elementQuality instanceof JsonPrimitive){
                    JsonPrimitive primitiveQuality = (JsonPrimitive) elementQuality;
                    if(primitiveQuality.isNumber()){
                        q = primitiveQuality.getAsNumber().intValue();
                    }
                }

                this.type = elementType.getAsString().toLowerCase();
                this.conditions = approvedConditions.toArray(new TableConditionExecutor[0]);
                this.weight = primitiveWeight.getAsNumber().intValue();
                this.quality = q;

                return loadCustomPropertiesFromJson(entryObject);
            }
        }
        return false;
    }

    protected abstract boolean loadCustomPropertiesFromJson(JsonObject object);

    public String getType() { return type; }
    public TableConditionExecutor[] getConditions() { return conditions; }
    public int getBaseWeight() { return weight; }
    public int getQuality() { return quality; }

    public int getModifiedWeight(){ return getModifiedWeight(DEFAULT_LUCK); }
    public int getModifiedWeight(float luck){
        return Math.max((int) Math.floor(weight + (quality * luck)), 0);
    }

    public void setBaseWeight(int weight) { this.weight = weight; }
    public void setQuality(int quality) { this.quality = quality; }
}
