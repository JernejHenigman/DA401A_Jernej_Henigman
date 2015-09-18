package androiddevelopment.assignment_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

/**
 * Created by Nejc on 17.9.2015.
 */
public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    public ArrayList<Movie> mMovie;
    private static LayoutInflater inflater = null;
    ProgressBar mProgressBar;




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
        View rowView1 = new View(mContext);
        inflater = ( LayoutInflater )mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.poster_title_year, null);
        rowView1 = inflater.inflate(R.layout.fragment_master_screen, null);
        mProgressBar = (ProgressBar) rowView.findViewById(R.id.pbLoading);

        Movie movie = mMovie.get(position);
        if (convertView == null) {
            Log.i("BLA", "BLA");

        } else {
            rowView = convertView;

        }

        DownloadMoviePoster dl = new DownloadMoviePoster((ImageView) rowView.findViewById(R.id.imageView1));
        dl.execute(movie.thumbNailLink);


        TextView tw4 = (TextView) rowView.findViewById(R.id.textView4);
        tw4.setText(movie.title);
        TextView tw5 = (TextView) rowView.findViewById(R.id.textView5);
        tw5.setText(movie.year);

        return rowView;
    }

    private class DownloadMoviePoster extends AsyncTask<String,Void,Bitmap> {


        ImageView bmImage;

        public DownloadMoviePoster(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String urlString = params[0];
            Bitmap poster = null;
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
                    poster = BitmapFactory.decodeStream(inputStream);
                    return poster; //to metodo je treba še nardit
                } finally {
                    urlConnection.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Bitmap poster) {
            bmImage.setImageBitmap(poster);
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);


        }

    }
}
