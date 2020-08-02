package dev.cg360.mc.nukkittables.basetypes;

import com.google.gson.JsonObject;
import dev.cg360.mc.nukkittables.context.TableRollContext;

public abstract class TableCondition {

    protected String condition;
    protected JsonObject data;

    public TableCondition(String conditionID, JsonObject data){
        this.condition = conditionID;
        this.data = data;
    }

    public abstract boolean isConditionPassed(TableRollContext context);

    public String getConditionType() { return condition; }
    public JsonObject getData() {
        return data;
    }
}
