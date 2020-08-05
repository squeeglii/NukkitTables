package dev.cg360.mc.nukkittables.types.entry;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.executors.TableConditionExecutor;
import dev.cg360.mc.nukkittables.executors.TableFunctionExecutor;
import dev.cg360.mc.nukkittables.types.TableCondition;
import dev.cg360.mc.nukkittables.types.TableFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class TableEntryItem extends TableEntry implements NamedTableEntry {

    protected String name;
    protected TableFunction[] functions;

    public TableEntryItem(){ }
    public TableEntryItem(String type, String id, int weight, int quality, TableFunction[] functions, TableCondition... conditions) {
        super(type, weight, quality, conditions);
        this.name = id;
        this.functions = functions;
    }

    @Override
    protected Optional<Item> rollEntryItems(TableRollContext context) {
        if(type == null) return Optional.empty();
        Optional<Item> it = getItem();
        if(it.isPresent()){
            Item item = Utility.applyFunctions(functions, it.get(), context);
            return Optional.of(item);
        }
        return Optional.empty();
    }

    private Optional<Item> getItem(){
        int id;
        try {
            id = Integer.parseInt(name);
        } catch (Exception err){
            return Optional.empty();
        }

        if(id < 256 && Block.list[id] != null) return Optional.of(Item.get(id));
        if(id < 65535 && Item.list[id] != null) return Optional.of(Item.get(id));

        return Optional.empty();
    }

    @Override
    protected boolean loadCustomPropertiesFromJson(JsonObject object) {
        JsonElement nameElement = object.get("name");
        JsonElement functionsElement = object.get("functions");

        if(nameElement instanceof JsonPrimitive && functionsElement instanceof JsonArray){
            JsonPrimitive namePrimitive = (JsonPrimitive) nameElement;
            JsonArray functionsArray = (JsonArray) functionsElement;

            if(!(namePrimitive.isNumber() || namePrimitive.isString())) return false;

            ArrayList<TableFunction> funcs = new ArrayList<>();

            for(JsonElement f: functionsArray){
                if(f.isJsonObject()){
                    JsonObject func = (JsonObject) f;
                    TableFunction.loadFromJsonObject(func).ifPresent(funcs::add);
                }
            }

            this.name = namePrimitive.getAsString();
            this.functions = funcs.toArray(new TableFunction[0]);
            return true;
        }

        return false;
    }

    @Override
    public String getName() { return name; }
    public TableFunction[] getFunctions() { return functions; }

    @Override
    public String toString() {
        return "TableEntryItem{" +
                "name='" + name + '\'' +
                ", functions=" + Arrays.toString(functions) +
                ", DEFAULT_LUCK=" + DEFAULT_LUCK +
                ", type='" + type + '\'' +
                ", conditions=" + Arrays.toString(conditions) +
                ", weight=" + weight +
                ", quality=" + quality +
                '}';
    }
}
