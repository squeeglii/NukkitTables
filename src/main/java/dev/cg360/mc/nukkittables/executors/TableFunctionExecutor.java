package dev.cg360.mc.nukkittables.executors;

import cn.nukkit.item.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.types.TableCondition;

import java.util.ArrayList;
import java.util.Optional;

public abstract class TableFunctionExecutor {

    public final Item applyFunction(Item item, TableRollContext context, TableCondition[] conditions, JsonObject data){ //TODO: And conditions
        return Utility.compileConditions(conditions, context) ? applyFunctionToItem(item, data) : item;
    }

    protected abstract Item applyFunctionToItem(Item item, JsonObject data);

    public abstract String getFunctionType();
}
