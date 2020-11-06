package dev.cg360.mc.nukkittables.types.entry;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.types.TableCondition;
import dev.cg360.mc.nukkittables.types.TableFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public final class TableEntryEmpty extends TableEntry {

    public TableEntryEmpty(){ }
    public TableEntryEmpty(String type, int weight, int quality, TableCondition[] conditions, TableFunction[] functions) {
        super(type, weight, quality, conditions, functions);
    }

    @Override
    public ArrayList<Item> gatherEntryItems(TableRollContext context) {
        return new ArrayList<>();
    }

    @Override
    protected boolean loadCustomPropertiesFromJson(JsonObject object) {
        return true;
    }


    @Override
    public String toString() {
        return "TableEntryEmpty{" +
                ", DEFAULT_LUCK=" + DEFAULT_LUCK +
                ", type='" + type + '\'' +
                ", conditions=" + Arrays.toString(conditions) +
                ", weight=" + weight +
                ", quality=" + quality +
                '}';
    }
}
