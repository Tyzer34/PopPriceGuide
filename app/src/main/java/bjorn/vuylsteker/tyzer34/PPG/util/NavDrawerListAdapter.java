package bjorn.vuylsteker.tyzer34.PPG.util;

import bjorn.vuylsteker.tyzer34.PPG.R;
import bjorn.vuylsteker.tyzer34.PPG.util.NavDrawerItem;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private ArrayList<Integer> hiddenItemIndexes = new ArrayList<Integer>();
	
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size() - hiddenItemIndexes.size();
	}

	@Override
	public Object getItem(int position) {
		for (int iHidden : hiddenItemIndexes){
			if(iHidden <= position)
				position++;
		}
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		for (int iHidden : hiddenItemIndexes){
			if(iHidden <= position)
				position++;
		}
		return position;
	}

	public int getItemPos(int position) {
		for (int iHidden : hiddenItemIndexes){
			if(iHidden <= position)
				position++;
		}
		return position;
	}

	@Override
	public void notifyDataSetChanged() {
		hiddenItemIndexes.clear();
		for (NavDrawerItem item : navDrawerItems){
			int index = navDrawerItems.indexOf(item);
			if (!item.isVisible()){
				hiddenItemIndexes.add(index);
			}
		}
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		for (int iHidden : hiddenItemIndexes){
			if(iHidden <= position)
				position++;
		}

		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

		NavDrawerItem current = navDrawerItems.get(position);

		imgIcon.setImageResource(current.getIcon());
        txtTitle.setText(current.getTitle());
        
        // displaying count
        // check whether it set visible or not
        if(current.getCounterVisibility()){
        	txtCount.setText(current.getCount());
			txtCount.setVisibility(View.VISIBLE);
        }else{
        	// hide the counter view
        	txtCount.setVisibility(View.GONE);
        }

		return convertView;
	}

}
