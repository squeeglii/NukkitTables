package dev.cg360.mc.nukkittables.types;

import cn.nukkit.item.Item;
import com.google.gson.JsonObject;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.executors.TableFunctionExecutor;

public class TableFunction {

    protected String function;
    protected TableCondition[] conditions;
    protected JsonObject data;

    public final Item applyFunction(Item item, TableRollContext context){ //TODO: And conditions
        return Utility.compileConditions(conditions, context) ? applyFunctionToItem(item, data) : item;
    }

    public String getFunctionType() { return function; }
    public TableCondition[] getConditions() { return conditions; }
    public JsonObject getData() { return data; }
}
