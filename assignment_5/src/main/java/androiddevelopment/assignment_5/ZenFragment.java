package androiddevelopment.assignment_5;

/**
 * Created by Nejc on 27.9.2015.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ZenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ZenFragment extends Fragment {

    QuoteAdapter mAdapter;
    ProgressBar mProgressBar;
    Context mContext;
    public ArrayList<Quote> mQuotes;
    public static int clickCounter = 0;
    public static boolean[] selectedArray = new boolean[100];
    View v;
    private OnFragmentInteractionListener mListener;

    public ZenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment,
        v = inflater.inflate(R.layout.fragment_zen, container, false);
        mProgressBar = (ProgressBar) v.findViewById(R.id.pbLoading);
        mQuotes = new ArrayList<Quote>();
        mAdapter = new QuoteAdapter(v.getContext(), mQuotes);
        final ListView listView = (ListView) v.findViewById(R.id.listview);
        listView.setAdapter(mAdapter); // uses the view to get the context instead of getActivity().


        for (int i=0; i<selectedArray.length; ++i) {
            selectedArray[i] = true;
        }

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                Log.i("ID", "" + id);
                Log.i("Pos", "" + position);
                Log.i("Chld",""+listView.getChildCount());

                mode.setTitle(listView.getCheckedItemCount() + " Selected");
                if (checked) {
                    listView.getChildAt(position).setBackgroundResource(R.color.pressed_color);
                    selectedArray[position] = false;

                } else {
                    listView.getChildAt(position).setBackgroundColor(Color.WHITE);
                    selectedArray[position] = true;
                }

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_delete, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        deleteSelectedItems();
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                for (int i = 0; i < selectedArray.length; ++i) {
                    selectedArray[i] = true;

                }

            }

            public void deleteSelectedItems() {

                for (int i = listView.getCount() - 1; i >= 0; i--) {
                    if (!selectedArray[i]) {
                        listView.getChildAt(i).setBackgroundColor(Color.WHITE);
                        Object toRemove = mAdapter.getItem(i);
                        mQuotes.remove(toRemove);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });


        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_delete) {
            return true;
        }

        if (id == R.id.add) {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
            new DownloadQuote().execute("https://api.github.com/zen?access_token=1e0742d1ae90b53386bfd60acdbaf97b383b5228");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_quote, menu);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
            Log.i("DL",""+quotes.size());
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
