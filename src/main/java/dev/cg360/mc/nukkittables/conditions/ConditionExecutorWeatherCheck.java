package dev.cg360.mc.nukkittables.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;

public class ConditionExecutorWeatherCheck extends TableConditionExecutor {

    public ConditionExecutorWeatherCheck() {
        super("minecraft:weather_check");
    }

    @Override
    public boolean isConditionPassed(TableRollContext context, JsonObject data) {
        JsonElement rainingElement = data.get("raining");
        if(rainingElement instanceof JsonPrimitive){
            JsonPrimitive rainingPrimitive = (JsonPrimitive) rainingElement;
            if(rainingPrimitive.isBoolean()){
                boolean raining = rainingPrimitive.getAsBoolean();
                if(raining != context.getOrigin().getLevel().isRaining()) return false;
            }
        }

        JsonElement thunderingElement = data.get("thundering");
        if(thunderingElement instanceof JsonPrimitive){
            JsonPrimitive thunderingPrimitive = (JsonPrimitive) thunderingElement;
            if(thunderingPrimitive.isBoolean()){
                boolean thundering = thunderingPrimitive.getAsBoolean();
                if(thundering != context.getOrigin().getLevel().isThundering()) return false;
            }
        }

        return true;
    }
}
