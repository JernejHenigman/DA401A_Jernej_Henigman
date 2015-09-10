package androiddevelopment.assignment_2;

import android.graphics.drawable.Drawable;

/**
 * Created by Nejc on 8.9.2015.
 */
public class Movie {
    String title;
    String year;
    String desription;
    Drawable fanart;
    Drawable poster;

    public Movie(String title, String year, String desription, Drawable fanart, Drawable poster) {
        this.title = title;
        this.year = year;
        this.desription = desription;
        this.fanart = fanart;
        this.poster = poster;
    }


}
