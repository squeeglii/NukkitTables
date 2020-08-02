package dev.cg360.mc.nukkittables.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.math.IntegerRange;
import dev.cg360.mc.nukkittables.types.TableCondition;

import java.util.Optional;

public class TableConditionWeatherCheck extends TableCondition {

    public TableConditionWeatherCheck(JsonObject data) {
        super("minecraft:weather_check", data);
    }

    @Override
    public boolean isConditionPassed(TableRollContext context) {
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
