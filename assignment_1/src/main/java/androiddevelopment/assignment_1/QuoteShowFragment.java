package androiddevelopment.assignment_1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static androiddevelopment.assignment_1.Helpers.getDate;


public class QuoteShowFragment extends Fragment {

    public QuoteShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quote_show, container, false);
        String quote = getArguments().getString("Quote");
        TextView quoteTextView = (TextView) v.findViewById(R.id.textView3);
        quoteTextView.setText(quote);

        TextView dateTextView = (TextView) v.findViewById(R.id.textView4);
        String Date = getDate();
        dateTextView.setText(Date);

        return v;
    }



}

