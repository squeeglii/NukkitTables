package dev.cg360.mc.nukkittables;

import dev.cg360.mc.nukkittables.basetypes.TableCondition;
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

}
