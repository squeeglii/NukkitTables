package dev.cg360.mc.nukkittables;

import dev.cg360.mc.nukkittables.conditions.TableConditionExecutorRandomChance;
import dev.cg360.mc.nukkittables.types.LootTable;
import dev.cg360.mc.nukkittables.types.TableConditionExecutor;
import dev.cg360.mc.nukkittables.types.TableFunctionExecutor;
import dev.cg360.mc.nukkittables.types.entry.TableEntry;

import java.util.HashMap;

public class LootTableRegistry {

    protected HashMap<String, Class<? extends TableConditionExecutor>> conditions;
    protected HashMap<String, Class<? extends TableFunctionExecutor>> functions;
    protected HashMap<String, Class<? extends TableEntry>> entryTypes;

    protected HashMap<String, LootTable> lootTables;

    public LootTableRegistry (){
        this.conditions = new HashMap<>();
        this.functions = new HashMap<>();
        this.entryTypes = new HashMap<>();

        this.lootTables = new HashMap<>();


    }

    public void loadMinecraftDefaults(){
        this.registerCondition(new TableConditionExecutorRandomChance());
    }

    public void registerCondition(TableConditionExecutor condition){
        conditions.put(condition.getConditionType().toLowerCase(), condition.getClass());
    }

    public void registerFunction(TableFunctionExecutor function){
        functions.put(function.getFunctionType().toLowerCase(), function.getClass());
    }

    public void registerEntryType(TableEntry entryType){
        entryTypes.put(entryType.getType().toLowerCase(), entryType.getClass());
    }

}
