package androiddevelopment.assignment_3;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.net.MalformedURLException;
import java.util.regex.Pattern;


public class MasterScreenFragment extends Fragment {
    ArrayList<Movie> mMovies;
    MovieAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    ProgressBar mProgressBar;

    public MasterScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_master_screen, container, false);
        mMovies = new ArrayList<Movie>();
        mAdapter = new MovieAdapter(v.getContext(), mMovies);
        GridView gridView = (GridView) v.findViewById(R.id.gridview);
        gridView.setAdapter(mAdapter);
        mProgressBar = (ProgressBar) v.findViewById(R.id.pbLoading);
        new DownloadMovieInfo().execute("https://api-v2launch.trakt.tv/movies/popular?extended=images");
        DownloadMovieInfo dl = new DownloadMovieInfo();
        dl.execute("https://api-v2launch.trakt.tv/movies/popular?extended=images");


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
        public void onFragmentInteraction(Uri uri);
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
