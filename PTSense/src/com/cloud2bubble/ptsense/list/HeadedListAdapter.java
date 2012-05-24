package com.cloud2bubble.ptsense.list;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HeadedListAdapter extends ArrayAdapter<Item> {

	private ArrayList<Item> items;
	private LayoutInflater vi;

	public HeadedListAdapter(Context context, ArrayList<Item> items) {
		super(context, 0, items);
		this.items = items;
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		Item i = items.get(position);
		if (i != null) {
			switch (i.getType()) {
			case Item.SECTION:
				v = vi.inflate(android.R.layout.preference_category, null);

				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);

				TextView separator = (TextView) v
						.findViewById(android.R.id.title);
				separator.setText(((SectionItem) i).getTitle());
				break;
			case Item.ENTRY:
				EntryItem ei = (EntryItem) i;
				v = vi.inflate(android.R.layout.simple_list_item_2, null);
				TextView title = (TextView) v.findViewById(android.R.id.text1);
				TextView value = (TextView) v.findViewById(android.R.id.text2);

				title.setText(ei.title);
				value.setText(ei.toString());
				break;
			case Item.EMPTY:
				v = vi.inflate(android.R.layout.simple_list_item_1, null);
				TextView text = (TextView) v.findViewById(android.R.id.text1);

				text.setText(((EmptyItem) i).getText());
				break;
			}
		}
		return v;
	}

}
