package dev.cg360.mc.nukkittables.types;

import cn.nukkit.item.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.LootTableRegistry;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;
import dev.cg360.mc.nukkittables.executors.TableFunctionExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class TableFunction {

    protected String function;
    protected TableCondition[] conditions;
    protected JsonObject data;

    public final Item applyFunction(Item item, TableRollContext context){ //TODO: And conditions
        Optional<TableFunctionExecutor> pf = LootTableRegistry.get().getFunctionExecutor(function);
        if(pf.isPresent()){
            return pf.get().applyFunction(item, context, conditions, data);
        }
        return item;
    }

    public static Optional<TableFunction> loadFromJsonObject(JsonObject object){
        JsonElement functionElement = object.get("function");
        JsonElement conditionsElement = object.get("conditions");

        if(functionElement instanceof JsonPrimitive){
            JsonPrimitive functionPrimitive = (JsonPrimitive) functionElement;

            if(functionPrimitive.isString()){
                ArrayList<TableCondition> cs = new ArrayList<>();

                if(conditionsElement instanceof JsonArray){
                    for(JsonElement c: (JsonArray) conditionsElement){
                        if(c instanceof JsonObject) {
                            TableCondition.loadConditionFromJsonObject((JsonObject) c).ifPresent(cs::add);
                        }
                    }
                }

                TableFunction func = new TableFunction();
                func.function = functionPrimitive.getAsString();
                func.conditions = cs.toArray(new TableCondition[0]);
                func.data = object;
                return Optional.of(func);
            }
        }
        return Optional.empty();
    }

    public String getFunctionType() { return function; }
    public TableCondition[] getConditions() { return conditions; }
    public JsonObject getData() { return data; }

    @Override
    public String toString() {
        return "TableFunction{" +
                "function='" + function + '\'' +
                ", conditions=" + Arrays.toString(conditions) +
                ", data=" + data +
                '}';
    }
}
