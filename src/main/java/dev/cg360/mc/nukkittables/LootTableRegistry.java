package dev.cg360.mc.nukkittables;

import cn.nukkit.Nukkit;
import cn.nukkit.Server;
import cn.nukkit.plugin.PluginLogger;
import com.google.gson.*;
import dev.cg360.mc.nukkittables.conditions.*;
import dev.cg360.mc.nukkittables.functions.FunctionExecutorSetCount;
import dev.cg360.mc.nukkittables.functions.FunctionExecutorSetMeta;
import dev.cg360.mc.nukkittables.types.LootTable;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;
import dev.cg360.mc.nukkittables.executors.TableFunctionExecutor;
import dev.cg360.mc.nukkittables.types.TablePool;
import dev.cg360.mc.nukkittables.types.entry.TableEntry;
import dev.cg360.mc.nukkittables.types.entry.TableEntryItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

public class LootTableRegistry {

    public static final LootTableRegistry INSTANCE = new LootTableRegistry();

    protected HashMap<String, TableConditionExecutor> conditionExecutors;
    protected HashMap<String, TableFunctionExecutor> functionExecutors;
    protected HashMap<String, Class<? extends TableEntry>> entryTypes;

    protected HashMap<String, LootTable> lootTables;

    private LootTableRegistry (){
        this.conditionExecutors = new HashMap<>();
        this.functionExecutors = new HashMap<>();
        this.entryTypes = new HashMap<>();

        this.lootTables = new HashMap<>();

        this.registerDefaultConditions();
        this.registerDefaultFunctions();
    }

    public void registerDefaultConditions(){
        // minecraft namespace
        this.registerConditionExecutor(new ConditionExecutorAlternative());
        this.registerConditionExecutor(new ConditionExecutorInverted());
        this.registerConditionExecutor(new ConditionExecutorRandomChance());
        this.registerConditionExecutor(new ConditionExecutorTimeCheck());
        this.registerConditionExecutor(new ConditionExecutorWeatherCheck());

        // nukkit namespace
        this.registerConditionExecutor(new ConditionExecutorPluginEnabled());
    }

    public void registerDefaultFunctions(){
        // minecraft namespace
        this.registerFunctionExecutor(new FunctionExecutorSetCount());

        // nukkit namespace
        this.registerFunctionExecutor(new FunctionExecutorSetMeta());
    }

    public void registerDefaultTypes(){
        this.registerEntryType("item", TableEntryItem.class);
    }

    public void loadAllLootTablesFromStorage(String name, boolean includeSubfolders){
        File root = new File(Server.getInstance().getDataPath() + "loottables/" + name);
        if(root.exists() && root.isDirectory()){
            try {
                for (File file : root.listFiles()) {
                    if(file.isDirectory() && includeSubfolders){
                        loadAllLootTablesFromStorage(name+"/"+file.getName(), true);
                    } else {
                        try {
                            registerStoredLootTable(name+"/"+file.getName());
                        } catch (Exception err){
                            Server.getInstance().getLogger().error("Error loading loot table at: "+file.getAbsolutePath());
                            err.printStackTrace();
                        }
                    }
                }
            } catch (Exception err){
                Server.getInstance().getLogger().error("Error loading loot tables in: "+root.getAbsolutePath());
                err.printStackTrace();
            }
        }
    }

    public boolean registerStoredLootTable(String name) throws FileNotFoundException {
        File file = new File(Server.getInstance().getDataPath() + "loottables/" + name);
        if((file.exists() && file.isFile()) && file.getName().toLowerCase().endsWith(".json")){
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String finalStr = "";
            Iterator<String> i = reader.lines().iterator();
            while (i.hasNext()){
                finalStr = finalStr.concat(i.next());
            }
            return registerLootTableFromString(name.toLowerCase().substring(0, name.length() - 6), finalStr);
        }
        return false;
    }

    public boolean registerLootTableFromString(String name, String jsonData) {
        Optional<LootTable> pt = LootTable.createLootTableFromString(jsonData);
        if(pt.isPresent()){
            lootTables.put(name.toLowerCase().trim(), pt.get());
            return true;
        }
        return false;
    }

    public void registerConditionExecutor(TableConditionExecutor condition){
        conditionExecutors.put(condition.getConditionType().toLowerCase(), condition);
    }

    public void registerFunctionExecutor(TableFunctionExecutor function){
        functionExecutors.put(function.getFunctionType().toLowerCase(), function);
    }

    public void registerEntryType(String id, Class<? extends TableEntry> entryType){
        entryTypes.put(id.toLowerCase(), entryType);
    }

    public Optional<TableConditionExecutor> getConditionExecutor(String id){ return Optional.ofNullable(conditionExecutors.get(id.toLowerCase())); }
    public Optional<TableFunctionExecutor> getFunctionExecutor(String id){ return Optional.ofNullable(functionExecutors.get(id.toLowerCase())); }
    public Optional<Class<? extends TableEntry>> getEntryTypeClass(String id){ return Optional.ofNullable(entryTypes.get(id.toLowerCase())); }
    public Optional<LootTable> getLootTable(String id){ return Optional.ofNullable(lootTables.get(id.toLowerCase())); }

    public ArrayList<String> getConditionExecutors() { return new ArrayList<>(conditionExecutors.keySet()); }
    public ArrayList<String> getFunctionExecutors() { return new ArrayList<>(functionExecutors.keySet()); }
    public ArrayList<String> getEntryTypes() { return new ArrayList<>(entryTypes.keySet()); }
    public ArrayList<String> getLootTables() { return new ArrayList<>(lootTables.keySet()); }

    public static LootTableRegistry get(){
        return INSTANCE;
    }

}
