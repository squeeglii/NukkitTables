package dev.cg360.mc.nukkittables;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.math.FloatRange;
import dev.cg360.mc.nukkittables.math.IntegerRange;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;
import dev.cg360.mc.nukkittables.executors.TableFunctionExecutor;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.types.TableCondition;
import dev.cg360.mc.nukkittables.types.TableFunction;
import dev.cg360.mc.nukkittables.types.entry.TableEntry;

import java.util.Optional;
import java.util.Random;

public class Utility {

    public static boolean compileConditions(TableCondition[] conditions, TableRollContext context){
        for(TableCondition condition: conditions){
            if(!condition.isConditionPassed(context)){
                return false;
            }
        }
        return true;
    }

    public static Item applyFunctions(TableFunction[] functions, Item item, TableRollContext context){
        Item returnable = item;
        for(TableFunction func: functions) returnable = func.applyFunction(returnable, context);
        return returnable;
    }

    public static Optional<IntegerRange> getIntegerRangeFromData(JsonObject data){
        JsonElement minimumObject = data.get("min");
        JsonElement maximumObject = data.get("max");
        if(minimumObject instanceof JsonPrimitive && maximumObject instanceof JsonPrimitive){
            JsonPrimitive minimum = (JsonPrimitive) minimumObject;
            JsonPrimitive maximum = (JsonPrimitive) maximumObject;
            if(minimum.isNumber() && maximum.isNumber()){
                int min = minimum.getAsInt();
                int max = maximum.getAsInt();

                return Optional.of(new IntegerRange(min, max));
            }
        }
        return Optional.empty();
    }

    public static Optional<TableEntry> parseEntry(JsonObject entryObject){
        JsonElement typeElement = entryObject.get("type");

        if (typeElement instanceof JsonPrimitive){
            JsonPrimitive typePrimitive = (JsonPrimitive) typeElement;

            if(typePrimitive.isString()){
                String entryType = typePrimitive.getAsString();
                Optional<Class<? extends TableEntry>> pc = LootTableRegistry.get().getEntryTypeClass(entryType.toLowerCase().trim());

                if(pc.isPresent()){
                    Class<? extends TableEntry> entryClass = pc.get();
                    try{
                        TableEntry e = entryClass.newInstance();
                        if(e.loadPropertiesFromJson(entryObject)) return Optional.of(e);

                    } catch (Exception err){
                        Server.getInstance().getLogger().warning(String.format("Error loading type [%s] in a lootTable. Badly coded? Skipping entry.", entryType));
                        err.printStackTrace();
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<FloatRange> getFloatRangeFromData(JsonObject data){
        JsonElement minimumObject = data.get("min");
        JsonElement maximumObject = data.get("max");
        if(minimumObject instanceof JsonPrimitive && maximumObject instanceof JsonPrimitive){
            JsonPrimitive minimum = (JsonPrimitive) minimumObject;
            JsonPrimitive maximum = (JsonPrimitive) maximumObject;
            if(minimum.isNumber() && maximum.isNumber()){
                float min = minimum.getAsFloat();
                float max = maximum.getAsFloat();

                return Optional.of(new FloatRange(min, max));
            }
        }
        return Optional.empty();
    }

    public static int getRandomIntBetweenInclusiveBounds(int min, int max){
        if(min == max) return min;

        int upperRandomBound = (max - min) + 1;
        return new Random().nextInt(upperRandomBound) + min;
    }

}
