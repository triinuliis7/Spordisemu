package spordisemu.spordisemu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ingrid on 10/25/15.
 */


public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] date;
    private final String[] location;

    public CustomListAdapter(Activity context, String[] itemname, String[] date, String[] location, Integer[] imgid) {
        super(context, R.layout.listview_item, itemname);


        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.date=date;
        this.location=location;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_item, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.texti1);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        extratxt.setText(date[position]+ " - " + location[position]);
        return rowView;

    };
}