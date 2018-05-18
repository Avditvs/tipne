package com.souillard.Fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.souillard.R;






public class ChangeClassFragment extends DialogFragment {

    private Button confirmationButton;
    private Button annulationButton;

     public interface FragmentChangeCallback {
        void onClickValidationButton();
        void onClickAnnulationButton();
    }

    public ChangeClassFragment() {
        // Required empty public constructor
    }



    private FragmentChangeCallback mCallback;



    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mCallback = (FragmentChangeCallback)activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_class, container, false);
        confirmationButton = view.findViewById(R.id.confirmationButon);
        confirmationButton.setOnClickListener(buttonValidationClickListener);
        annulationButton = view.findViewById(R.id.annulationButon);
        annulationButton.setOnClickListener(getButtonAnnulationClickListener);
        return view;
    }


    private View.OnClickListener buttonValidationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCallback.onClickValidationButton();


        }
    };

    private View.OnClickListener getButtonAnnulationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCallback.onClickAnnulationButton();

        }
    };

    @Override
    public void onDetach(){
        super.onDetach();

    }


}
