package main.simpledog.mobile.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ListItemAdapter extends ArrayAdapter<Item> {

    private List<Item> entries = new ArrayList<Item>();

    static class ViewHolder {
        ImageView item_image_view;
        TextView item_title_view;
    }

    public ListItemAdapter(Context context, List<Item> objects) {
        super(context,R.layout.item,objects);
        entries = objects;
    }
    public void addEntriesToTop(List<Item> entries) {
        // Add entries in reversed order to achieve a sequence used in most of messaging/chat apps
        if (entries != null) {
            Collections.reverse(entries);
        }
        // Add entries to the top of the list
        this.entries.addAll(0, entries);
        notifyDataSetChanged();
    }

    public void addEntriesToBottom(List<Item> entries) {
        // Add entries to the bottom of the list
        this.entries.addAll(entries);

        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item_image_view = (ImageView)convertView.findViewById(R.id.itemIcon);
            viewHolder.item_title_view = (TextView)convertView.findViewById(R.id.itemListTitle);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Item item = getItem(position);
        viewHolder.item_title_view.setText("["+ position + "] " + item.getItem_title());

        return  convertView;
    }
}
