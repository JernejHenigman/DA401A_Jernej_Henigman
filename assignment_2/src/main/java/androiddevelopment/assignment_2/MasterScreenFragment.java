package androiddevelopment.assignment_2;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterScreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MasterScreenFragment extends Fragment {

    public OnFragmentInteractionListener mListener;
    public ArrayList<Movie> mMovie;


    public MasterScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_master_screen, container, false);
        Resources res = v.getResources();
        TypedArray ta = res.obtainTypedArray(R.array.movies);
        mMovie = new ArrayList<Movie>();

        for (int i = 0; i<ta.length(); ++i) {
            int id = ta.getResourceId(i, 0);
            TypedArray result = getResources().obtainTypedArray(id);
            Movie movie = new Movie(result.getString(0),result.getString(1),result.getString(2),result.getDrawable(3),result.getDrawable(4));
            mMovie.add(i,movie);
            result.recycle();
        }

        ta.recycle();

        GridView gridView = (GridView) v.findViewById(R.id.gridview);
        gridView.setAdapter(new MovieAdapter(v.getContext(),mMovie)); // uses the view to get the context instead of getActivity().

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.i("Pos",Integer.toString(position));
                mListener.onFragmentInteraction(mMovie,position);

            }
        });

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(ArrayList<Movie> movies,int position);
    }


}
