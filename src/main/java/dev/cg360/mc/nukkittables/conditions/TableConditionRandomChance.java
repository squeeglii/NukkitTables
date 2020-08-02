package dev.cg360.mc.nukkittables.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.types.TableCondition;

public class TableConditionRandomChance extends TableCondition {

    public TableConditionRandomChance(JsonObject data) {
        super("minecraft:random_chance", data);
    }

    @Override
    public boolean isConditionPassed(TableRollContext context) {
        JsonElement element = data.get("chance");
        if(element instanceof JsonPrimitive){
            JsonPrimitive primitive = (JsonPrimitive) element;
            if(primitive.isBoolean()){
                return primitive.getAsBoolean();
            }
        }
        return true;
    }
}
