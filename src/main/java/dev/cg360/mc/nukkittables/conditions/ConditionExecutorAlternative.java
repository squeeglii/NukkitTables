package dev.cg360.mc.nukkittables.conditions;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;
import dev.cg360.mc.nukkittables.types.TableCondition;

import java.util.Optional;

public class ConditionExecutorAlternative extends TableConditionExecutor {

    @Override
    public boolean isConditionPassed(TableRollContext context, JsonObject data) {
        JsonElement termsElement = data.get("terms");
        if(termsElement instanceof JsonArray) {
            JsonArray termsArray = (JsonArray) termsElement;
            if(termsArray.size() < 1) return true;
            int falseReturns = 0;

            for(JsonElement conditionElement: termsArray) {
                if (conditionElement instanceof JsonObject) {
                    JsonObject conditionObject = (JsonObject) conditionElement;
                    Optional<TableCondition> pc = TableCondition.loadConditionFromJsonObject(conditionObject);
                    if (pc.isPresent()) {
                        if(pc.get().isConditionPassed(context)){
                            return true;
                        } else {
                            falseReturns++;
                        }
                    }
                }
            }

            return falseReturns <= 0;
        }
        return true;
    }

    @Override
    public String getConditionType() {
        return "minecraft:alternative";
    }
}
