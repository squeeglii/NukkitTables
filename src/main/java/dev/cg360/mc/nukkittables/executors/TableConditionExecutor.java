package dev.cg360.mc.nukkittables.executors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.context.TableRollContext;

import java.util.Optional;

public abstract class TableConditionExecutor {

    public abstract boolean isConditionPassed(TableRollContext context, JsonObject data);

    public abstract String getConditionType();
}
