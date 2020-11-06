## Compatibility:

**Target:** _Java 1.16 Coverage_
**Last Reviewed:** _6th November 2020_

|System|Feature|Compatible?|Since|Notes|
|---|---|---|---|---|
|Entry|minecraft:item|Yes ✔|Pre-Alpha|
|Entry|minecraft:tag|No ❌|-|Nukkit doesn't have anything like this. It'll either not be implemented or belong to a future project.|
|Entry|minecraft:loot_table|No ❌|-|
|Entry|minecraft:group|Yes ✔|Alpha 2|
|Entry|minecraft:alternatives|Yes ✔|Alpha 2|
|Entry|minecraft:sequence|Yes ✔|Alpha 2|
|Entry|minecraft:dynamic|No ❌|-|Not implementing for the time being.|
|Entry|minecraft:empty|Yes ✔|Alpha 2|The fallback for a bad parse.|

|System|Feature|Compatible?|Since|Notes|
|---|---|---|---|---|
|Function|minecraft:apply_bonus|No ❌|-|
|Function|minecraft:copy_name|No ❌|-|
|Function|minecraft:copy_nbt|No ❌|-|
|Function|minecraft:apply_state|No ❌|-|
|Function|minecraft:enchant_randomly|No ❌|-|
|Function|minecraft:enchant_with_levels|No ❌|-|
|Function|minecraft:exploration_map|No ❌|-|
|Function|minecraft:explosion_decay|No ❌|-|
|Function|minecraft:furnace_smelt|No ❌|-|
|Function|minecraft:fill_player_head|No ❌|-|
|Function|minecraft:limit_count|No ❌|-|
|Function|minecraft:looting_enchant|No ❌|-|
|Function|minecraft:set_attributes|No ❌|-|
|Function|minecraft:set_contents|No ❌|-|
|Function|minecraft:set_count|Yes ✔|Pre-Alpha|
|Function|minecraft:set_damage|No ❌|-|
|Function|minecraft:set_loot_table|No ❌|-|
|Function|minecraft:set_lore|No ❌|-|
|Function|minecraft:set_name|No ❌|-|
|Function|minecraft:set_nbt|No ❌|-|
|Function|minecraft:set_stew_effect|No ❌|-|

|System|Feature|Compatible?|Since|Notes|
|---|---|---|---|---|
|Condition|minecraft:alternative|Yes ✔|Pre-Alpha|
|Condition|minecraft:block_state_property|No ❌|-|Not supported on Nukkit 1.0 - Will be implemented in 2.0.|
|Condition|minecraft:damage_source_properties|No ❌|-|
|Condition|minecraft:entity_properties|No ❌|-|
|Condition|minecraft:entity_scores|No ❌|-|Could be implemented with a ScoreboardAPI or when Nukkit gets native support.|
|Condition|minecraft:inverted|No ❌|Yes ✔|Pre-Alpha|
|Condition|minecraft:killed_by_player|No ❌|-|
|Condition|minecraft:location_check|No ❌|-|
|Condition|minecraft:match_tool|No ❌|-|
|Condition|minecraft:random_chance|Yes ✔|Pre-Alpha|
|Condition|minecraft:random_chance_with_looting|No ❌|-|
|Condition|minecraft:reference|No ❌|-|
|Condition|minecraft:survives_explosion|No ❌|-|
|Condition|minecraft:table_bonus|No ❌|-|
|Condition|minecraft:time_check|Yes ✔|Pre-Alpha|
|Condition|minecraft:weather_check|Yes ✔|Pre-Alpha|

## Custom Features:

|System|Feature|Since|Notes|
|---|---|---|---|
|Function|nukkit:set_meta|Pre-Alpha|Will be removed if meta is transitioned out.|
|Condition|nukkit:plugin_enabled|Pre-Alpha|Might change namespace to modded as it could be universal.|