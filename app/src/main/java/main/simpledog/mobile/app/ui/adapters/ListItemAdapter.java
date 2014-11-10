package main.simpledog.mobile.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.Item;

import java.util.List;


public class ListItemAdapter extends ArrayAdapter<Item> {



    static class ViewHolder {
        ImageView item_image_view;
        TextView item_title_view;
    }

    public ListItemAdapter(Context context, List<Item> objects) {

        super(context,R.layout.item_base,objects);
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_base,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item_image_view = (ImageView)convertView.findViewById(R.id.itemIcon);
            viewHolder.item_title_view = (TextView)convertView.findViewById(R.id.itemListTitle);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Item item = getItem(position);
        viewHolder.item_title_view.setText(item.getItem_title());

        return  convertView;
    }
}
