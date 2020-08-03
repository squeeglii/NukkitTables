package dev.cg360.mc.nukkittables.functions;

import cn.nukkit.item.Item;
import cn.nukkit.math.MathHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.math.IntegerRange;
import dev.cg360.mc.nukkittables.types.TableConditionExecutor;
import dev.cg360.mc.nukkittables.types.TableFunctionExecutor;

import java.util.Optional;
import java.util.Random;

public class TableFunctionExecutorSetCount extends TableFunctionExecutor {

    public TableFunctionExecutorSetCount(JsonObject data, TableConditionExecutor... conditions) {
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
        Optional<IntegerRange> ir = Utility.getIntegerRangeFromData(data);
        if(ir.isPresent()) {
            IntegerRange range = ir.get();
            if (range.getMin() < 0) range.setMin(0);
            if (range.getMax() < 1) return Optional.of(0);

            return Optional.of(Utility.getRandomIntBetweenInclusiveBounds(range.getMin(), range.getMax()));
        }
        return Optional.empty();
    }

    protected Optional<Integer> getAsBinomial(JsonObject data){
        JsonElement nObject = data.get("n");
        JsonElement pObject = data.get("p");
        if(nObject instanceof JsonPrimitive && pObject instanceof JsonPrimitive){
            JsonPrimitive nValue = (JsonPrimitive) nObject;
            JsonPrimitive pValue = (JsonPrimitive) pObject;
            if(nValue.isNumber() && pValue.isNumber()){
                int n = nValue.getAsInt();
                float p = pValue.getAsFloat();

                if(n < 1) return Optional.of(0);
                if(p <= 0) return Optional.of(0);
                if(p >= 1) return Optional.of(n);

                Random random = new Random();
                int tally = 0;
                for(int i = 0; i < n; i++){
                    if(random.nextFloat() <= p){
                        tally++;
                    }
                }

                return Optional.of(tally);
            }
        }
        return Optional.empty();
    }

}
