package dev.cg360.mc.nukkittables.basetypes;

import cn.nukkit.item.Item;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;

public abstract class TableFunction {

    protected String function;

    protected JsonElement data;
    protected TableCondition[] conditions;

    public TableFunction(String functionID, JsonElement data, TableCondition... conditions){
        this.function = functionID;
        this.conditions = conditions;

        this.data = data;
    }

    public final Item applyFunction(Item item, TableRollContext context){
        return Utility.compileConditions(conditions, context) ? applyFunctionToItem(item) : item;
    }

    protected abstract Item applyFunctionToItem(Item item);

    public String getFunctionType() { return function; }
    public JsonElement getData() { return data; }
    public TableCondition[] getConditions() { return conditions; }
}
