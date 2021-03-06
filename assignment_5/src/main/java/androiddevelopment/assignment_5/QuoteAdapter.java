package androiddevelopment.assignment_5;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


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
        View rowView;
        inflater = ( LayoutInflater )mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.image_icon_string, null);

        Quote quote = mQuote.get(position);
        Log.i("BLA", ""+position);
        if (convertView == null) {

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
