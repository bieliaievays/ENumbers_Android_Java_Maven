package com.ashomok.eNumbers.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.ashomok.eNumbers.R;
import com.ashomok.eNumbers.data_load.EN;

import java.util.List;

/**
 * Created by y.belyaeva on 28.07.2015.
 */
class ENumberListAdapter extends ArrayAdapter<EN> {

    private static final String TAG = ENumberListAdapter.class.getSimpleName();
    private Context context;
    public ENumberListAdapter(Context context, int resource) {
        super(context, resource);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.enumb_row, parent, false);
        } else {
            view = convertView;
        }

        try {
            // Find fields to populate in inflated template
            TextView ecode = (TextView) view.findViewById(R.id.ECode);
            TextView ename = (TextView) view.findViewById(R.id.EName);
            TextView epurpose = (TextView) view.findViewById(R.id.EPurpose);

            ENumbFlag flag = (ENumbFlag) view.findViewById(R.id.enumbFlag_enumb_proxy_list_row_layout);

      //      todo reduntant?
            flag.setmIsGreen(false);
            flag.setmIsYellow(false);
            flag.setmIsRed(false);
            flag.setmIsGrey(false);

            EN item = getItem(position);
            // Extract properties from cursor
            String code = item.getCode();

            String name = item.getName();
            String purpose = item.getPurpose();
            String dangerLevel = item.getDangerLevel();

            // Populate fields with extracted properties
            ecode.setText(code);
            ename.setText(name);
            epurpose.setText(purpose);

            switch (dangerLevel) {
                case "safe":
                    flag.setmIsGreen(true);
                    break;
                case "medium":
                    flag.setmIsYellow(true);
                    break;
                case "hight":
                    flag.setmIsRed(true);
                    break;
                case "unknown":
                    flag.setmIsGrey(true);
                    break;
                default:
                    flag.setmIsGrey(true);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
      return view;
    }

    public void setData(List<EN> data) {
        clear();
        if (data != null) {
            addAll(data);
        }
    }
}
