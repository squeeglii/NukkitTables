package dev.cg360.mc.nukkittables.basetypes;

import cn.nukkit.item.Item;
import com.google.gson.JsonElement;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableContext;

public abstract class TableFunction {

    protected String function;

    protected TableCondition[] conditions;
    protected JsonElement data;

    public final Item applyFunction(Item item, TableContext context){
        return Utility.compileConditions(conditions, context) ? applyFunctionToItem(item) : item;
    }

    protected abstract Item applyFunctionToItem(Item item);

    public JsonElement getData() {
        return data;
    }
}
