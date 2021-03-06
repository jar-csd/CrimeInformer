package csd.jar.crimeinformer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;

/**
 * Created by Administrator on 29/1/2559.
 */
public class MyExpanableListView extends BaseExpandableListAdapter{

    //Explicit

    private Context objContext;
    private MyData objMyData = new MyData();
    private String[] parentStrings = objMyData.mainHeadStrings;
    private String[][] childStrings = objMyData.subHeadStrings;


    public MyExpanableListView(Context context) {
        objContext = context;

    }//Constructor


    @Override
    public int getGroupCount() {
        return parentStrings.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return childStrings[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return i;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        CheckedTextView objCheckedTextView = new CheckedTextView(objContext);
        objCheckedTextView.setText(parentStrings[i]);


        return objCheckedTextView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        CheckedTextView objCheckedTextView = new CheckedTextView(objContext);
        objCheckedTextView.setText("     ==> " + childStrings[i][i1]);

        return objCheckedTextView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
} //Main Class
