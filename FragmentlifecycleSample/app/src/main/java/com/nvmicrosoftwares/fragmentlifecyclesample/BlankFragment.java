package com.nvmicrosoftwares.fragmentlifecyclesample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BlankFragment extends Fragment {


    public BlankFragment() {
        // Required empty public constructor
    }


    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("fragmentInfooo", "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("fragmentInfooo", "onCreateVeiw: ");
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("fragmentInfooo", "OnAttach: ");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("fragmentInfooo", "OnDetach: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("fragmentInfooo", "OnActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("fragemnt", "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("fragment", "ondesroyview");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("fragment", "OnStrat");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("fragment", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("fragment", "OnResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("fragment", "onStop");
    }


}
