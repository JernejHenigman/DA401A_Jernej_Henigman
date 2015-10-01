package androiddevelopment.assignment_5;

import android.app.Activity;
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
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class MasterScreenFragment extends Fragment {
    ArrayList<Movie> mMovies;
    MovieAdapter mAdapter;
    ProgressBar mProgressBar;
    public static boolean[] selectedArray = new boolean[100];
    View v;

    public MasterScreenFragment() {
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
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_master_screen, container, false);
        mMovies = new ArrayList<Movie>();
        mAdapter = new MovieAdapter(v.getContext(), mMovies);
        final GridView gridView = (GridView) v.findViewById(R.id.gridview);
        gridView.setAdapter(mAdapter);
        mProgressBar = (ProgressBar) v.findViewById(R.id.pbLoading);
        new DownloadMovieInfo().execute("https://api-v2launch.trakt.tv/movies/popular?extended=images");
        DownloadMovieInfo dl = new DownloadMovieInfo();
        dl.execute("https://api-v2launch.trakt.tv/movies/popular?extended=images");


        for (int i=0; i<selectedArray.length; ++i) {
            selectedArray[i] = true;
        }

        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                mode.setTitle(gridView.getCheckedItemCount() + " Selected");
                if (checked) {
                    Log.i("Childs", "" + gridView.getChildCount());
                    Log.i("ChildsPo", "" + position);

                    selectedArray[position] = false;

                }
                else {
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
                for (int i=0; i<selectedArray.length; ++i) {
                    selectedArray[i] = true;

                }
            }

            public void deleteSelectedItems() {

                for (int i = gridView.getCount()-1; i >= 0; i--){
                    if (!selectedArray[i]) {
                        Object toRemove = mAdapter.getItem(i);
                        mMovies.remove(toRemove);

                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        return v;
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
    public void onDetach() {
        super.onDetach();
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

        if (id == R.id.info) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),"About clicked!",Toast.LENGTH_LONG);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_info, menu);
    }

    private class DownloadMovieInfo extends AsyncTask<String,Void,ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            String urlString = params[0];
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("trakt-api-version","2");
                urlConnection.setRequestProperty("trakt-api-key","492a165927bfaff86b3030454939981d4e2d94c50515e15e42f41fbf57481a44");
                try {
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    return createMovieList(inputStream); //to metodo je treba še nardit
                } finally {
                    urlConnection.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private ArrayList<Movie> createMovieList(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            JSONArray jsonArray = null;
            ArrayList<Movie> movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null) {

                sb.append(line);
            }

            String s = sb.toString();
            String pattern = Pattern.quote("},{");
            String[] parts = s.split(pattern);
            try {
                jsonArray = new JSONArray(s);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i=0; i<jsonArray.length(); ++i ) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                    JSONObject poster = jsonObject.getJSONObject("images").optJSONObject("poster");
                    Movie movie = new Movie(jsonObject.getString("title"),jsonObject.getString("year"),poster.getString("thumb"));
                    movies.add(i,movie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mMovies.addAll(movies);
            mAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        }

    }

}
