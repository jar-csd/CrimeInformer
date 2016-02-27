package csd.jar.crimeinformer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 27/2/2559.
 */
public class UserAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] creimeStrings, categoryStrings, dateStrings;

    public UserAdapter(Context context, String[] creimeStrings, String[] categoryStrings, String[] dateStrings) {
        this.context = context;
        this.creimeStrings = creimeStrings;
        this.categoryStrings = categoryStrings;
        this.dateStrings = dateStrings;
    } // Constructor

    @Override
    public int getCount() {
        return creimeStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.crime_listview_user, viewGroup, false);

        TextView crimeTextView = (TextView) view1.findViewById(R.id.textView16);
        crimeTextView.setText(creimeStrings[i]);

        TextView categoryTextView = (TextView) view1.findViewById(R.id.textView17);
        categoryTextView.setText(categoryStrings[i]);

        TextView dateTextView = (TextView) view1.findViewById(R.id.textView18);
        dateTextView.setText(dateStrings[i]);

        return view1;
    }
} //Main Class
