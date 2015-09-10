package androiddevelopment.assignment_2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailScreenFragment extends Fragment {


    public DetailScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_screen, container, false);
        ArrayList<Movie> movies = (ArrayList<Movie>) getArguments().getSerializable("movieList");
        int position = getArguments().getInt("Position");

        TextView textView1 = (TextView) v.findViewById(R.id.textView1);
        textView1.setText(movies.get(position).desription);

        TextView textView2 = (TextView) v.findViewById(R.id.textView2);
        textView2.setText(movies.get(position).title);

        TextView textView3 = (TextView) v.findViewById(R.id.textView3);
        textView3.setText(movies.get(position).year);

        ImageView imageView = (ImageView) v.findViewById(R.id.imageView2);
        imageView.setImageDrawable(movies.get(position).fanart);

        return v;
    }




}
