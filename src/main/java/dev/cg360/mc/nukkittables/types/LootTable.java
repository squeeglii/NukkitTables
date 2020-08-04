package dev.cg360.mc.nukkittables.types;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import com.google.gson.*;
import dev.cg360.mc.nukkittables.LootTableRegistry;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.math.FloatRange;
import dev.cg360.mc.nukkittables.math.IntegerRange;
import dev.cg360.mc.nukkittables.types.entry.TableEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class LootTable {

    protected String type;
    protected TablePool[] pools;

    public LootTable(String type, TablePool... pools){
        this.type = type.toLowerCase().trim();
        this.pools = pools;
    }

    public Item[] rollLootTable(TableRollContext context){
        ArrayList<Item> items = new ArrayList<>();
        for(TablePool pool: pools){
            if(Utility.compileConditions(pool.getConditions(), context)){
                items.addAll(Arrays.asList(pool.rollPool(context)));
            }
        }
        return items.toArray(new Item[0]);
    }

    public static Optional<LootTable> createLootTableFromString(String jsonData) {
        JsonElement rootElement = JsonParser.parseString(jsonData);

        if(rootElement instanceof JsonObject){
            JsonObject rootObject = (JsonObject) rootElement;
            JsonElement tableTypeElement = rootObject.get("type");
            JsonElement poolsElement = rootObject.get("pools");

            if(poolsElement instanceof JsonArray){
                JsonArray poolsArray = (JsonArray) poolsElement;
                ArrayList<TablePool> pools = new ArrayList<>();

                for(JsonElement poolElement: poolsArray){
                    if(poolElement instanceof JsonObject){
                        JsonObject poolObject = (JsonObject) poolElement;

                        JsonElement conditionsElement = poolObject.get("conditions");
                        JsonElement functionsElement = poolObject.get("functions");
                        JsonElement rollsElement = poolObject.get("rolls");
                        JsonElement bonusRollsElement = poolObject.get("bonus_rolls");
                        JsonElement entriesElement = poolObject.get("entries");

                        if(entriesElement instanceof JsonArray){
                            JsonArray entriesArray = (JsonArray) entriesElement;
                            ArrayList<TableEntry> entries = new ArrayList<>();

                            for(JsonElement entryElement: entriesArray){
                                if(entryElement instanceof JsonObject){
                                    JsonObject entryObject = (JsonObject) entryElement;
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
                                                    if(e.loadPropertiesFromJson(entryObject)) entries.add(e);

                                                } catch (Exception err){
                                                    Server.getInstance().getLogger().warning(String.format("Error loading type [%s] in a lootTable. Badly coded? Skipping entry.", entryType));
                                                    err.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if(entriesArray.size() > 0){
                                ArrayList<TableCondition> conditions = new ArrayList<>();
                                ArrayList<TableFunction> functions = new ArrayList<>();

                                if(conditionsElement instanceof JsonArray){
                                    JsonArray conditionsArray = (JsonArray) conditionsElement;
                                    for(JsonElement c: conditionsArray){
                                        if(c instanceof JsonObject){
                                            TableCondition.loadConditionFromJsonObject((JsonObject) c).ifPresent(conditions::add);
                                        }
                                    }
                                }

                                if(functionsElement instanceof JsonArray){
                                    JsonArray functionsArray = (JsonArray) functionsElement;
                                    for(JsonElement c: functionsArray){
                                        if(c instanceof JsonObject){
                                            TableFunction.loadFromJsonObject((JsonObject) c).ifPresent(functions::add);
                                        }
                                    }
                                }

                                TablePool pool = new TablePool(
                                        conditions.toArray(new TableCondition[0]),
                                        functions.toArray(new TableFunction[0]),
                                        entries.toArray(new TableEntry[0])
                                );

                                if(bonusRollsElement instanceof JsonPrimitive){
                                    JsonPrimitive bonusRollsPrimitive = (JsonPrimitive) bonusRollsElement;

                                    if(bonusRollsPrimitive.isNumber()){
                                        pool.fixedBonusRolls = bonusRollsPrimitive.getAsFloat();
                                    }
                                } else if (bonusRollsElement instanceof JsonObject){
                                    JsonObject bonusRollsObject = (JsonObject) bonusRollsElement;
                                    JsonElement minElement = bonusRollsObject.get("min");
                                    JsonElement maxElement = bonusRollsObject.get("max");

                                    if(minElement instanceof JsonPrimitive && maxElement instanceof JsonPrimitive){
                                        JsonPrimitive minPrimitive = (JsonPrimitive) minElement;
                                        JsonPrimitive maxPrimitive = (JsonPrimitive) maxElement;

                                        if(minPrimitive.isNumber() && maxPrimitive.isNumber()){
                                            pool.variableBonusRolls = new FloatRange(minPrimitive.getAsFloat(), maxPrimitive.getAsFloat());
                                        }
                                    }
                                }

                                if(rollsElement instanceof JsonPrimitive){
                                    JsonPrimitive rollsPrimitive = (JsonPrimitive) rollsElement;

                                    if(rollsPrimitive.isNumber()){
                                        pool.fixedRolls = rollsPrimitive.getAsInt();
                                        pools.add(pool);
                                    }
                                } else if (rollsElement instanceof JsonObject){
                                    JsonObject rollsObject = (JsonObject) rollsElement;
                                    JsonElement minElement = rollsObject.get("min");
                                    JsonElement maxElement = rollsObject.get("max");

                                    if(minElement instanceof JsonPrimitive && maxElement instanceof JsonPrimitive){
                                        JsonPrimitive minPrimitive = (JsonPrimitive) minElement;
                                        JsonPrimitive maxPrimitive = (JsonPrimitive) maxElement;

                                        if(minPrimitive.isNumber() && maxPrimitive.isNumber()){
                                            pool.variableRolls = new IntegerRange(minPrimitive.getAsInt(), maxPrimitive.getAsInt());
                                            pools.add(pool);
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

                String tableType = "generic";
                if(tableTypeElement instanceof JsonPrimitive){
                    JsonPrimitive typePrimitive = (JsonPrimitive) tableTypeElement;

                    if(typePrimitive.isString()) {
                        tableType = typePrimitive.getAsString();
                    }
                }

                LootTable table = new LootTable(tableType, pools.toArray(new TablePool[0]));
                return Optional.of(table);
            }
        }
        //TODO:
        // - Load type (String)
        // - Load pools.
        //   - load conditions inside each pool.
        //   - load functions inside each pool.
        //   - Load entries inside each pool.
        return Optional.empty();
    }

    public String getType() { return type; }
    public TablePool[] getPools() { return pools; }
}
