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

    public TableEntryItem(){ }
    public TableEntryItem(String type, String id, int weight, int quality, TableCondition[] conditions, TableFunction[] functions) {
        super(type, weight, quality, conditions, functions);
        this.name = id;
    }

    @Override
    protected ArrayList<Item> rollEntryItems(TableRollContext context) {
        if(name == null) return new ArrayList<>();
        ArrayList<Item> item = new ArrayList<>();
        getItem().ifPresent(item::add);
        return item;
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


        if(nameElement instanceof JsonPrimitive){
            JsonPrimitive namePrimitive = (JsonPrimitive) nameElement;

            if(!(namePrimitive.isNumber() || namePrimitive.isString())) return false;
            this.name = namePrimitive.getAsString();

            return true;
        }

        return false;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String toString() {
        return "TableEntryItem{" +
                "name='" + name + '\'' +
                ", DEFAULT_LUCK=" + DEFAULT_LUCK +
                ", type='" + type + '\'' +
                ", conditions=" + Arrays.toString(conditions) +
                ", weight=" + weight +
                ", quality=" + quality +
                '}';
    }
}
