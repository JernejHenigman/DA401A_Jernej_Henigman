package androiddevelopment.assignment_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Nejc on 8.9.2015.
 */
public class MovieAdapter extends BaseAdapter {

    private Context mContext;
    public ArrayList<Movie> mMovie;
    private static LayoutInflater inflater = null;

    public MovieAdapter(Context context,ArrayList<Movie> movies) {
        mContext = context;
        mMovie = movies;
    }

    @Override
    public int getCount() {
        return mMovie.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovie.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = new View(mContext);
        inflater = ( LayoutInflater )mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.poster_title_year, null);
        Movie movie = mMovie.get(position);
        if (convertView == null) {
            Log.i("BLA","BLA");

        } else {
            rowView = convertView;

        }

        ImageView iw = (ImageView) rowView.findViewById(R.id.imageView1);
        iw.setImageDrawable(movie.poster);
        TextView tw4 = (TextView) rowView.findViewById(R.id.textView4);
        tw4.setText(movie.title);
        TextView tw5 = (TextView) rowView.findViewById(R.id.textView5);
        tw5.setText(movie.year);

        return rowView;
    }

}
