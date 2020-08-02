package dev.cg360.mc.nukkittables;

import cn.nukkit.item.Item;
import dev.cg360.mc.nukkittables.types.TableCondition;
import dev.cg360.mc.nukkittables.types.TableFunction;
import dev.cg360.mc.nukkittables.context.TableRollContext;

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

    public static int getRandomIntBetweenInclusiveBounds(int min, int max){
        if(min == max) return min;

        int upperRandomBound = (max - min) + 1;
        return new Random().nextInt(upperRandomBound) + min;
    }

}
