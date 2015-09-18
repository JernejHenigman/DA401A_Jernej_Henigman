package androiddevelopment.assignment_3g;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ZenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ZenFragment extends Fragment implements View.OnClickListener {

    QuoteAdapter mAdapter;
    ProgressBar mProgressBar;
    Context mContext;
    public ArrayList<Quote> mQuotes;
    private static LayoutInflater inflater = null;
    public static int clickCounter = 0;

    private OnFragmentInteractionListener mListener;

    public ZenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_zen, container, false);
        View button_fab = v.findViewById(R.id.fab);
        button_fab.setOnClickListener(this);
        mProgressBar = (ProgressBar) v.findViewById(R.id.pbLoading);
        mQuotes = new ArrayList<Quote>();
        mAdapter = new QuoteAdapter(v.getContext(), mQuotes);
        ListView listView = (ListView) v.findViewById(R.id.listview);
        listView.setAdapter(mAdapter); // uses the view to get the context instead of getActivity().


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
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

    @Override
    public void onClick(View v)  {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        new DownloadQuote().execute("https://api.github.com/zen?access_token=0f892e365071c7e778a020e463d715b8ccb816f5");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction();
    }

    private class DownloadQuote extends AsyncTask<String,Void,ArrayList<Quote>> {



        @Override
        protected ArrayList<Quote> doInBackground(String... params) {
            String urlString = params[0];
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    return createListOfBooks(inputStream); //to metodo je treba še nardit
                } finally {
                    urlConnection.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<Quote> quotes) {
            mQuotes.addAll(quotes);
            mAdapter.notifyDataSetChanged();
            clickCounter += 1;
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        }

        private ArrayList<Quote> createListOfBooks(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            ArrayList<Quote> quote = new ArrayList<Quote>();


            while ((line = bufferedReader.readLine()) != null) {

                sb.append(line);
            }

            String s = sb.toString();
            quote.add(0, new Quote(s));
            return quote;

        }

    }

}
