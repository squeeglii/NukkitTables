package dev.cg360.mc.nukkittables.functions;

import cn.nukkit.item.Item;
import cn.nukkit.math.MathHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.executors.TableFunctionExecutor;
import dev.cg360.mc.nukkittables.math.IntegerRange;

import java.util.Optional;
import java.util.Random;

public class FunctionExecutorSetMeta extends TableFunctionExecutor {

    @Override
    protected Item applyFunctionToItem(Item item, JsonObject data) {
        JsonElement meta = data.get("value");
        if(meta instanceof JsonPrimitive){
            JsonPrimitive m = (JsonPrimitive) meta;
            if(m.isNumber()){
                item.setDamage(MathHelper.clamp(m.getAsNumber().intValue(), 0, 15));
            }
        }
        return item;
    }

    @Override
    public String getFunctionType() {
        return "nukkit:set_meta";
    }

}
