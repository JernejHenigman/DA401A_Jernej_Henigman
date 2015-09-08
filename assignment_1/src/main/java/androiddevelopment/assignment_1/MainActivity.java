package androiddevelopment.assignment_1;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import static androiddevelopment.assignment_1.Helpers.randomizeQuote;



public class MainActivity extends AppCompatActivity implements QuoteGetFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Create", "Activity created!");
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            QuoteGetFragment firstFragment = new QuoteGetFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }
    }


    public void onFragmentInteraction() {
        QuoteShowFragment quoteShowFrag = (QuoteShowFragment) getSupportFragmentManager().findFragmentById(R.id.guote_show);

        // Create fragment and give it an argument for the selected article
        QuoteShowFragment newFragment = new QuoteShowFragment();

        Resources res = getResources();
        String[] quotes = res.getStringArray(R.array.quotes);

        String quote = randomizeQuote(quotes);

        Bundle args = new Bundle();
        args.putString("Quote", quote);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onStart()  {
        super.onStart();
        Log.i("Start", "Activity started!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass
        Log.i("Destroy", "Activity destroyed!");

        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass
        Log.i("Pause", "Activity paused!");
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass
        Log.i("Resume", "Activity resumed!");
    }

    @Override
    public void onStop() {
        super.onStop();  // Always call the superclass
        Log.i("Stop", "Activity stopped!");
    }


}
