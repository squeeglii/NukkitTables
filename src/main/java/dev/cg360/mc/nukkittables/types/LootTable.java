package dev.cg360.mc.nukkittables.types;

import cn.nukkit.item.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.types.entry.TableEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class LootTable {

    protected String type;
    protected TablePool[] pools;

    public LootTable(String type, TablePool... pools){
        this.type = type.toLowerCase();
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
            JsonElement typeElement = rootObject.get("type");
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


                            }
                        }
                    }
                }
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
