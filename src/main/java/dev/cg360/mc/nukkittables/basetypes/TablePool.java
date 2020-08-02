package dev.cg360.mc.nukkittables.basetypes;

import cn.nukkit.item.Item;
import cn.nukkit.math.MathHelper;
import dev.cg360.mc.nukkittables.Utility;
import dev.cg360.mc.nukkittables.context.TableRollContext;
import dev.cg360.mc.nukkittables.math.FloatRange;
import dev.cg360.mc.nukkittables.math.IntegerRange;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class TablePool {

    protected TableCondition[] conditions;
    protected TableFunction[] functions;

    protected int fixedRolls;
    protected IntegerRange variableRolls;

    protected float fixedBonusRolls;
    protected FloatRange variableBonusRolls;

    protected TableEntry[] entries;

    protected Optional<Item> rollPoolOnce(TableRollContext context, int maxWeight, ArrayList<TableEntry> passedEntries){
        int selection = maxWeight > 0 ? new Random().nextInt(maxWeight) : 0;
        int cumulativeWeightChecked = 1;
        for(TableEntry entry: passedEntries){
            //TODO: Get luck from context.
            if(selection <= (cumulativeWeightChecked + entry.getModifiedWeight())){
                return entry.rollEntry(context);
            }
            cumulativeWeightChecked += entry.getModifiedWeight();
        }
        return Optional.empty();
    }

    public Item[] rollPool(TableRollContext context){
        if(Utility.compileConditions(conditions, context)) {
            ArrayList<TableEntry> passedEntries = getPassedEntries(context);
            int maxWeight = 0;
            for(TableEntry entry : passedEntries) maxWeight += entry.getModifiedWeight(); //TODO: Get luck from context.

            if(passedEntries.size() > 0) {
                int rollAmount = getRandomRolls(0); //TODO: Get luck from context.
                ArrayList<Item> items = new ArrayList<>();

                for (int i = 0; i < rollAmount; i++) {
                    rollPoolOnce(context, maxWeight, passedEntries).ifPresent(item -> {
                        Item finalItem = Utility.applyFunctions(functions, item, context);
                        items.add(finalItem);
                    });
                }
                return items.toArray(new Item[0]);
            }
        }
        return new Item[0];
    }

    protected ArrayList<TableEntry> getPassedEntries(TableRollContext context){
        ArrayList<TableEntry> passedEntries = new ArrayList<>();
        for(TableEntry entry : entries){
            if(Utility.compileConditions(entry.getConditions(), context)) passedEntries.add(entry);
        }
        return passedEntries;
    }

    public int getRandomBaseRolls(){
        if(variableRolls == null){
            return fixedRolls;
        } else {
            Random random = new Random();
            int upperRandomBound = (variableRolls.getMax() - variableRolls.getMin()) + 1;
            return random.nextInt(upperRandomBound) + variableRolls.getMin();
        }
    }

    public float getRandomBonusRolls(float luck){
        if(variableBonusRolls == null){
            return fixedBonusRolls * luck;
        } else {
            Random random = new Random();
            float difference = variableBonusRolls.getMax() - variableBonusRolls.getMin();
            float minoffset = difference * random.nextFloat();
            return variableRolls.getMin()+minoffset;
        }
    }

    public int getRandomRolls(float luck){
        return  Math.max(MathHelper.floor_float_int(getRandomBaseRolls() - getRandomBonusRolls(luck)), 0);
    }

}
