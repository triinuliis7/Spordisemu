package spordisemu.spordisemu;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Triinu on 11/23/15.
 */


public class CommentsListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] usernames;
    private final Drawable[] images;
    private final String[] dates;
    private final String[] contents;

    public CommentsListAdapter(Activity context, String[] usernames, Drawable[] images, String[] dates, String[] contents) {
        super(context, R.layout.item_listview_comments, usernames);

        this.context=context;
        this.usernames=usernames;
        this.images=images;
        this.dates=dates;
        this.contents=contents;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_listview_comments, null, true);

        TextView commentName = (TextView) rowView.findViewById(R.id.commentName);
        ImageView commentIcon = (ImageView) rowView.findViewById(R.id.commentIcon);
        TextView commentContent = (TextView) rowView.findViewById(R.id.commentContent);
        TextView commentDate = (TextView) rowView.findViewById(R.id.commentDate);

        commentName.setText(usernames[position]);
        commentIcon.setImageDrawable(images[position]);
        commentContent.setText(contents[position]);
        commentDate.setText(dates[position]);
        return rowView;

    }
}