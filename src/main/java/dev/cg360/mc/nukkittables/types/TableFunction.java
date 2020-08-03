package dev.cg360.mc.nukkittables.types;

import cn.nukkit.item.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;

import java.util.ArrayList;
import java.util.Optional;

public abstract class TableFunction {

    protected String function;

    protected JsonObject data;
    protected TableCondition[] conditions;

    public TableFunction(String functionID, JsonObject data, TableCondition... conditions){
        this.function = functionID;
        this.conditions = conditions;

        this.data = data;
    }

    public final Item applyFunction(Item item, TableRollContext context){
        return Utility.compileConditions(conditions, context) ? applyFunctionToItem(item) : item;
    }

    public static Optional<TableFunction> loadFromJsonObject(JsonObject object){
        JsonElement functionElement = object.get("function");
        JsonElement conditionsElement = object.get("conditions");

        if(functionElement instanceof JsonPrimitive){
            JsonPrimitive functionPrimitive = (JsonPrimitive) functionElement;

            if(functionPrimitive.isString()){
                String name = functionPrimitive.getAsString();
                ArrayList<TableCondition> cs = new ArrayList<>();

                if(conditionsElement instanceof JsonArray){
                    for(JsonElement c: (JsonArray) conditionsElement){
                        if(c instanceof JsonObject) {
                            TableCondition.loadConditionFromJsonObject((JsonObject) c).ifPresent(cs::add);
                        }
                    }
                }

                // Data is JsonObject
                //TODO: get condition from registry
            }
        }
        return Optional.empty();
    }

    protected abstract Item applyFunctionToItem(Item item);

    public String getFunctionType() { return function; }
    public JsonObject getData() { return data; }
    public TableCondition[] getConditions() { return conditions; }
}
