package dev.cg360.mc.nukkittables.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.LootTableRegistry;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;

import java.util.Optional;

public final class TableCondition {

    protected String condition;
    protected JsonObject data;

    public boolean isConditionPassed(TableRollContext context){
        Optional<TableConditionExecutor> pc = LootTableRegistry.get().getConditionExecutor(condition);
        if(pc.isPresent()){
            return pc.get().isConditionPassed(context, data);
        }
        return true;
    }

    public static Optional<TableCondition> loadConditionFromJsonObject(JsonObject object){
        JsonElement conditionElement = object.get("condition");

        if(conditionElement instanceof JsonPrimitive){
            JsonPrimitive conditionPrimitive = (JsonPrimitive) conditionElement;

            if(conditionPrimitive.isString()){
                TableCondition con = new TableCondition();
                con.condition = conditionPrimitive.getAsString();
                con.data = object;
                return Optional.of(con);
            }
        }
        return Optional.empty();
    }

    public String getCondition() { return condition; }
    public JsonObject getData() { return data; }
}
