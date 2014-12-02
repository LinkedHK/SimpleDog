package main.simpledog.mobile.app.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import main.simpledog.mobile.app.R;
import main.simpledog.mobile.app.rest.entities.ItemCategory;

import java.util.ArrayList;
import java.util.List;


import java.util.List;

public class ListCategoriesAdapter extends ArrayAdapter<ItemCategory> {

    private List<ItemCategory> entries = new ArrayList<ItemCategory>();

    static class ViewHolder {
        TextView categoryName;
        TextView itemCount;
    }

   public ListCategoriesAdapter(Context context, List<ItemCategory> objects){
        super(context, R.layout.category_item,objects);
       entries = objects;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_item,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.categoryName = (TextView)convertView.findViewById(R.id.categoryNameText);
            viewHolder.itemCount = (TextView)convertView.findViewById(R.id.categoryCountText);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        ItemCategory item = getItem(position);
        viewHolder.categoryName.setText(item.name);
        viewHolder.itemCount.setText("(" + item.items_count + ")");

        return  convertView;
    }
}
