package dev.cg360.mc.nukkittables.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.math.IntegerRange;
import dev.cg360.mc.nukkittables.types.TableConditionExecutor;

import java.util.Optional;

public class ConditionExecutorTimeCheck extends TableConditionExecutor {

    public ConditionExecutorTimeCheck(JsonObject data) {
        super("minecraft:time_check");
    }

    @Override
    public boolean isConditionPassed(TableRollContext context, JsonObject data) {
        int time = context.getOrigin().getLevel().getTime();

        JsonElement elementPeriod = data.get("period");
        if(elementPeriod instanceof JsonPrimitive){
            JsonPrimitive primitivePeriod = (JsonPrimitive) elementPeriod;
            if(primitivePeriod.isNumber()){
                int period = primitivePeriod.getAsNumber().intValue();
                if(period > 0)  time %= period;
            }
        }

        JsonElement elementValue = data.get("value");
        if(elementValue instanceof JsonPrimitive){
            JsonPrimitive primitiveValue = (JsonPrimitive) elementValue;
            if(primitiveValue.isNumber()){
                int value = primitiveValue.getAsNumber().intValue();
                return value == time;
            }
        } else if (elementValue instanceof JsonObject){
            Optional<IntegerRange> ir = Utility.getIntegerRangeFromData(data);
            if(ir.isPresent()){
                IntegerRange valueRange = ir.get();
                return valueRange.isWithinRange(time);
            }
        }

        return true;
    }
}
