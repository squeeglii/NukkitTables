package dev.cg360.mc.nukkittables.types;

import com.google.gson.JsonObject;
import dev.cg360.mc.nukkittables.LootTableRegistry;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;

import java.util.Optional;

public final class TableCondition {

    protected String condition;
    protected JsonObject data;

    public boolean isConditionPassed(TableRollContext context, JsonObject data){
        Optional<TableConditionExecutor> pc = LootTableRegistry.get().getConditionExecutor(condition);
        if(pc.isPresent()){
            return pc.get().isConditionPassed(context, data);
        }
        return false;
    }

    public String getCondition() { return condition; }
    public JsonObject getData() { return data; }
}
