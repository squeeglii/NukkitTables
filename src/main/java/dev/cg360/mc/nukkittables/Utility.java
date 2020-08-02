package dev.cg360.mc.nukkittables;

import cn.nukkit.item.Item;
import dev.cg360.mc.nukkittables.basetypes.TableCondition;
import dev.cg360.mc.nukkittables.basetypes.TableFunction;
import dev.cg360.mc.nukkittables.context.TableContext;

public class Utility {

    public static boolean compileConditions(TableCondition[] conditions, TableContext context){
        for(TableCondition condition: conditions){
            if(!condition.isConditionPassed(context)){
                return false;
            }
        }
        return true;
    }

    public static Item applyFunctions(TableFunction[] functions, Item item, TableContext context){
        Item returnable = item;
        for(TableFunction func: functions) returnable = func.applyFunction(returnable, context);
        return returnable;
    }

}
