package androiddevelopment.assignment_5;

import android.graphics.drawable.Drawable;

/**
 * Created by Nejc on 17.9.2015.
 */
public class Movie {
    String title;
    String year;
    String thumbNailLink;
    Drawable poster;

    public void setPoster(Drawable poster) {
        this.poster = poster;
    }

    public Movie(String title, String year, String thumbNailLink) {
        this.title = title;
        this.year = year;
        this.thumbNailLink = thumbNailLink;
    }


}