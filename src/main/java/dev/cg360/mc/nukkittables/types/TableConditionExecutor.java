package dev.cg360.mc.nukkittables.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.context.TableRollContext;

import java.util.Optional;

public abstract class TableConditionExecutor {

    protected String condition;

    public TableConditionExecutor(String conditionID){
        this.condition = conditionID;
    }

    public abstract boolean isConditionPassed(TableRollContext context, JsonObject data);

    public static Optional<TableConditionExecutor> loadConditionFromJsonObject(JsonObject object){
        JsonElement conditionElement = object.get("condition");

        if(conditionElement instanceof JsonPrimitive){
            JsonPrimitive conditionPrimitive = (JsonPrimitive) conditionElement;
            if(conditionPrimitive.isString()){
                String name = conditionPrimitive.getAsString();
                // Data is JsonObject
                //TODO: get condition from registry
            }
        }
        return Optional.empty();
    }

    public String getConditionType() { return condition; }
}
