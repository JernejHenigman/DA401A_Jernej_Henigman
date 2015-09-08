package androiddevelopment.assignment_1;

import java.util.Random;


public class Helpers {

    public Helpers() {

    }

    public static final String randomizeQuote(String[] quotes) {

        Random rand = new Random();
        int randomQuote = rand.nextInt(quotes.length);
        String quote = quotes[randomQuote];

        return quote;
    }

    public static final String getDate() {

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String date = df.format("dd MMMM yyyy", new java.util.Date()).toString();

        return date;

    }
}
