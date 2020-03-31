package com.comp5425.visiontranslator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import comp5425.sydney.edu.au.visiontranslator.R;

public class MainListAdapter extends ArrayAdapter<TranslationItem> {
    public MainListAdapter(Context context, ArrayList<TranslationItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TranslationItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list, parent, false);
        }
        // Lookup view for data population
        TextView translation = (TextView) convertView.findViewById(R.id.recognition);
        TextView recognition = (TextView) convertView.findViewById(R.id.translation);

        // Populate the data into the template view using the data object
        translation.setText(item.getTranslation());
        translation.setPadding(6,0,6,5);
        recognition.setText(Integer.toString(position+1)+". "+item.getRecogntion());
        recognition.setTextSize(20);

        // Return the completed view to render on screen
        return convertView;
    }



}
