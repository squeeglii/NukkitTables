package dev.cg360.mc.nukkittables.basetypes;

import com.google.gson.JsonElement;
import dev.cg360.mc.nukkittables.context.TableContext;

public abstract class TableCondition {

    protected String condition;

    protected JsonElement data;

    public abstract boolean isConditionPassed(TableContext context);

    public JsonElement getData() {
        return data;
    }
}
