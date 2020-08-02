package dev.cg360.mc.nukkittables.basetypes;

import com.google.gson.JsonElement;
import dev.cg360.mc.nukkittables.context.TableRollContext;

public abstract class TableCondition {

    protected String condition;

    protected JsonElement data;

    public abstract boolean isConditionPassed(TableRollContext context);

    public JsonElement getData() {
        return data;
    }
}
