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

        switch (mQuest) {
            case 0:

                AlertDialog.Builder quest1 = new AlertDialog
                        .Builder(getActivity())
                        .setTitle(R.string.quest_1)
                        .setItems(R.array.quest_1,this);
                return quest1.create();

            case 1:

                AlertDialog.Builder quest2 = new AlertDialog
                        .Builder(getActivity())
                        .setTitle(R.string.quest_2)
                        .setItems(R.array.quest_2,this);

                return quest2.create();
            case 2:

                AlertDialog.Builder quest3 = new AlertDialog
                        .Builder(getActivity())
                        .setTitle(R.string.quest_3)
                        .setItems(R.array.quest_3,this);

                return quest3.create();
            case 3:

                AlertDialog.Builder quest4 = new AlertDialog
                        .Builder(getActivity())
                        .setTitle(R.string.quest_4)
                        .setItems(R.array.quest_4,this);

                return quest4.create();

        }
        return null;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        this.mListener.onComplete(which);
    }

}
