package androiddevelopment.assignment_3g;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nejc on 16.9.2015.
 */
public class QuoteAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<Quote> mQuote;
    private static LayoutInflater inflater = null;

    public QuoteAdapter(Context context,ArrayList<Quote> quotes) {
        mContext = context;
        mQuote = quotes;
    }

    @Override
    public int getCount() {
        return mQuote.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = new View(mContext);
        inflater = ( LayoutInflater )mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.image_icon_string, null);

        Quote quote = mQuote.get(position);
        if (convertView == null) {
            Log.i("BLA","BLA");

        } else {
            rowView = convertView;
        }
        ImageView iv1 = (ImageView) rowView.findViewById(R.id.imageView1);
        iv1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.quote_icon2));
        TextView tw1 = (TextView) rowView.findViewById(R.id.textView1);
        tw1.setText(quote.quoteString);
        return rowView;
    }
}
