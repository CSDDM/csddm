package com.example.csddm.menu;

import android.content.Intent;
import android.util.Log;

import com.example.csddm.LoginActivity;
import com.example.csddm.web.GetMenuListWeb;
import com.example.csddm.web.LoginWeb;

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
    private static  List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static  ArrayList<String> SONGIDS = new ArrayList<String>();
    private static  ArrayList<String> SONGNAMES = new ArrayList<String>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static int COUNT = 0;

    public DummyContent() {
        //initial menu value-lists
        // 子线程接收数据，主线程修改数据
        class MyGetMenuListThread extends Thread {
            private boolean isDone = false;
            @Override
            public void run() {
                ArrayList<ArrayList<String>> info;
                info = GetMenuListWeb.getMenuList();
                if (info==null) {
                    Log.i("DummyContent","music menu is empty!");
                } else {
                    COUNT=info.size();
                    for(int i=0;i<COUNT;i++){
                        SONGIDS.add(info.get(i).get(0));
                        SONGNAMES.add(info.get(i).get(1));
                    }
                    Log.i("DummyContent",COUNT+"");
                }
                isDone = true;
            }

            public boolean getIsDone(){
                return isDone;
            }
        }
        // 创建子线程，分别进行Get和Post传输
        MyGetMenuListThread thread =  new MyGetMenuListThread();
        thread.start();
        while(!thread.getIsDone()){

        }

        // Add some sample items.
        for (int i = 0; i < COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    public List<DummyItem> getItems(){
        return ITEMS;
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), SONGNAMES.get(position), makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
