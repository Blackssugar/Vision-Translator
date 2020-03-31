package com.comp5425.visiontranslator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import comp5425.sydney.edu.au.visiontranslator.R;

public class TranslationItemAdapter extends ArrayAdapter<TranslationItem> {
    public TranslationItemAdapter(Context context, ArrayList<TranslationItem> items) {
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
        TextView translation = (TextView) convertView.findViewById(R.id.translation);
        TextView recognition = (TextView) convertView.findViewById(R.id.recognition);

        // Populate the data into the template view using the data object
        translation.setText(item.getTranslation());
        recognition.setText(item.getRecogntion());

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = TranslationItemDB.getDatabase(getContext());
                translationItemDao = db.translationItemDao();
                todo = new Translation(item.getTranslation(),item.getRecogntion());
                saveItemToDatabase();
                Toast.makeText(getContext(),"Translation saved.",Toast.LENGTH_SHORT).show();
                setImageButtonEnabled(getContext(),false,button,R.drawable.download);
            }
        });*/
        // Return the completed view to render on screen
        return convertView;
    }

    public static void setImageButtonEnabled(Context ctxt, boolean enabled, ImageButton item,
                                             int iconResId) {
        item.setEnabled(enabled);
        Drawable originalIcon = ctxt.getResources().getDrawable(iconResId);
        Drawable icon = enabled ? originalIcon : convertDrawableToGrayScale(originalIcon);
        item.setImageDrawable(icon);
    }

    public static Drawable convertDrawableToGrayScale(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Drawable res = drawable.mutate();
        res.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        return res;
    }


}
