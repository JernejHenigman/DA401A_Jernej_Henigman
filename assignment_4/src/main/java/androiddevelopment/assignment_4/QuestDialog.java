package androiddevelopment.assignment_4;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.DeflaterInputStream;


public class QuestDialog extends DialogFragment implements DialogInterface.OnClickListener {

    int mQuest;
    String mTitle;
    int mCharSequences;
    private OnOptionSelected mListener;

    public QuestDialog() {
        // Required empty public constructor

    }

    public static interface OnOptionSelected {
        public abstract void onComplete(int option);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnOptionSelected)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnOptionSelectedr");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mQuest  = getArguments().getInt("quest");
        Log.i("QuestNumber",""+mQuest);


        switch (mQuest) {
            case 0:
                mTitle = "Who was the fist president of USA?";
                mCharSequences = R.array.quest_1;
                AlertDialog.Builder quest1 = new AlertDialog
                        .Builder(getActivity())
                        .setTitle(mTitle)
                        .setItems(mCharSequences,this);
                return quest1.create();

            case 1:
                mTitle = "Who was the second president of USA?";
                mCharSequences = R.array.quest_2;
                AlertDialog.Builder quest2 = new AlertDialog
                        .Builder(getActivity())
                        .setTitle(mTitle)
                        .setItems(mCharSequences,this);

                return quest2.create();
            case 2:
                mTitle = "Who was the third president of USA?";
                mCharSequences = R.array.quest_3;
                AlertDialog.Builder quest3 = new AlertDialog
                        .Builder(getActivity())
                        .setTitle(mTitle)
                        .setItems(mCharSequences,this);

                return quest3.create();
            case 3:
                mTitle = "Who was the forth president of USA?";
                mCharSequences = R.array.quest_4;
                AlertDialog.Builder quest4 = new AlertDialog
                        .Builder(getActivity())
                        .setTitle(mTitle)
                        .setItems(mCharSequences,this);

                return quest4.create();

        }
        AlertDialog.Builder quest5 = null;
        return quest5.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        this.mListener.onComplete(which);

        switch (mQuest) {
            case 0:
                switch (which) {
                    case 0:
                        Log.i("QuestNumber1","Wrong");
                        break;
                    case 1:
                        Log.i("QuestNumber1","Right");
                        break;
                    case 2:
                        Log.i("QuestNumber1","Wrong");
                        break;
                }
                break;
            case 1:
                switch (which) {
                    case 0:
                        Log.i("QuestNumber2","Right");
                        break;
                    case 1:
                        Log.i("QuestNumber2","Wrong");
                        break;
                    case 2:
                        Log.i("QuestNumber2","Wrong");
                        break;
                }
                break;
            case 2:
                switch (which) {
                    case 0:
                        Log.i("QuestNumber3","Wrong");
                        break;
                    case 1:
                        Log.i("QuestNumber3","Wrong");
                        break;
                    case 2:
                        Log.i("QuestNumber3","Right");
                        break;
                }
                break;
            case 3:
                switch (which) {
                    case 0:
                        Log.i("QuestNumber4","Right");
                        break;
                    case 1:
                        Log.i("QuestNumber4","Wrong");
                        break;
                    case 2:
                        Log.i("QuestNumber4","Wrong");
                        break;
                }
                break;
        }

    }

}
