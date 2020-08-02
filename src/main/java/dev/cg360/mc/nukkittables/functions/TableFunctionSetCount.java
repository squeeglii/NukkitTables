package dev.cg360.mc.nukkittables.functions;

import cn.nukkit.item.Item;
import cn.nukkit.math.MathHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.basetypes.TableCondition;
import dev.cg360.mc.nukkittables.basetypes.TableFunction;

import java.util.Optional;
import java.util.Random;

public class TableFunctionSetCount extends TableFunction {

    public TableFunctionSetCount(JsonObject data, TableCondition... conditions) {
        super("minecraft:set_count", data, conditions);
    }

    @Override
    protected Item applyFunctionToItem(Item item) {
        getCount(data).ifPresent(count -> item.setCount(MathHelper.clamp(count,0, 64)));
        return item;
    }

    protected Optional<Integer> getCount(JsonObject data){
        JsonElement count = data.get("count");
        if(count instanceof JsonPrimitive){
            JsonPrimitive c = (JsonPrimitive) count;
            return c.isNumber() ? Optional.of(c.getAsNumber().intValue()) : Optional.empty();
        } else if (count instanceof JsonObject){
            JsonObject c = (JsonObject) count;
            JsonElement t = c.get("type");
            if(t instanceof JsonPrimitive){
                String type = t.getAsString().toLowerCase();
                switch (type){
                    case "uniform":
                        return getAsUniform(data);
                    case "binomial":
                        return getAsBinomial(data);
                }
            }
        }
        return Optional.empty();
    }

    protected Optional<Integer> getAsUniform(JsonObject data){
        JsonElement minimumObject = data.get("min");
        JsonElement maximumObject = data.get("max");
        if(minimumObject instanceof JsonPrimitive && maximumObject instanceof JsonPrimitive){
            JsonPrimitive minimum = (JsonPrimitive) minimumObject;
            JsonPrimitive maximum = (JsonPrimitive) maximumObject;
            if(minimum.isNumber() && maximum.isNumber()){
                int min = minimum.getAsInt();
                int max = maximum.getAsInt();
                if(min > max) return Optional.empty();
                if(min < 0 ) min = 0;
                if(max < 1) return Optional.of(0);

                return Optional.of(Utility.getRandomIntBetweenInclusiveBounds(min, max));
            }
        }
        return Optional.empty();
    }

    protected Optional<Integer> getAsBinomial(JsonObject data){
        return null;
    }

}
