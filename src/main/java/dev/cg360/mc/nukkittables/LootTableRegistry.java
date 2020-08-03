package dev.cg360.mc.nukkittables;

import dev.cg360.mc.nukkittables.conditions.ConditionExecutorRandomChance;
import dev.cg360.mc.nukkittables.conditions.ConditionExecutorTimeCheck;
import dev.cg360.mc.nukkittables.conditions.ConditionExecutorWeatherCheck;
import dev.cg360.mc.nukkittables.functions.FunctionExecutorSetCount;
import dev.cg360.mc.nukkittables.types.LootTable;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;
import dev.cg360.mc.nukkittables.executors.TableFunctionExecutor;
import dev.cg360.mc.nukkittables.types.entry.TableEntry;

import java.util.HashMap;

public class LootTableRegistry {

    protected HashMap<String, TableConditionExecutor> conditionExecutors;
    protected HashMap<String, TableFunctionExecutor> functionExecutors;
    protected HashMap<String, Class<? extends TableEntry>> entryTypes;

    protected HashMap<String, LootTable> lootTables;

    public LootTableRegistry (){
        this.conditionExecutors = new HashMap<>();
        this.functionExecutors = new HashMap<>();
        this.entryTypes = new HashMap<>();

        this.lootTables = new HashMap<>();

        this.registerDefaultConditions();
        this.registerDefaultFunctions();
    }

    public void registerDefaultConditions(){
        this.registerConditionExecutor(new ConditionExecutorRandomChance());
        this.registerConditionExecutor(new ConditionExecutorTimeCheck());
        this.registerConditionExecutor(new ConditionExecutorWeatherCheck());
    }

    public void registerDefaultFunctions(){
        this.registerFunctionExecutor(new FunctionExecutorSetCount());
    }


    public void registerConditionExecutor(TableConditionExecutor condition){
        conditionExecutors.put(condition.getConditionType().toLowerCase(), condition);
    }

    public void registerFunctionExecutor(TableFunctionExecutor function){
        functionExecutors.put(function.getFunctionType().toLowerCase(), function);
    }

    public void registerEntryType(TableEntry entryType){
        entryTypes.put(entryType.getType().toLowerCase(), entryType.getClass());
    }

}
