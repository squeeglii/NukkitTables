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

public abstract class TableFunctionExecutor {

    protected String function;


    public TableFunctionExecutor(String functionID){
        this.function = functionID;
    }

    public final Item applyFunction(Item item, TableRollContext context, JsonObject data){ //TODO: And conditions
        return Utility.compileConditions(conditions, context) ? applyFunctionToItem(item, data) : item;
    }

    public static Optional<TableFunctionExecutor> loadFromJsonObject(JsonObject object){
        JsonElement functionElement = object.get("function");
        JsonElement conditionsElement = object.get("conditions");

        if(functionElement instanceof JsonPrimitive){
            JsonPrimitive functionPrimitive = (JsonPrimitive) functionElement;

            if(functionPrimitive.isString()){
                String name = functionPrimitive.getAsString();
                ArrayList<TableConditionExecutor> cs = new ArrayList<>();

                if(conditionsElement instanceof JsonArray){
                    for(JsonElement c: (JsonArray) conditionsElement){
                        if(c instanceof JsonObject) {
                            TableConditionExecutor.loadConditionFromJsonObject((JsonObject) c).ifPresent(cs::add);
                        }
                    }
                }

                // Data is JsonObject
                //TODO: get condition from registry
            }
        }
        return Optional.empty();
    }

    protected abstract Item applyFunctionToItem(Item item, JsonObject data);

    public String getFunctionType() { return function; }
}
