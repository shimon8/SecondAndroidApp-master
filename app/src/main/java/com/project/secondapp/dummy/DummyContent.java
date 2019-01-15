package com.project.secondapp.dummy;

import com.project.secondapp.controller.model.entities.Travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public final List<Travel> items = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public final Map<String, Travel> item_map = new HashMap<>();

    public DummyContent (List<Travel> travels){
        for (Travel travel : travels){
            addItem(travel);
        }
    }
    private void addItem(Travel item) {
        items.add(item);
        item_map.put(item.getId(), item);
    }
    private String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}
