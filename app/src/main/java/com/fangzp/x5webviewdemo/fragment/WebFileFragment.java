package com.fangzp.x5webviewdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fangzp.x5webviewdemo.R;


public class WebFileFragment extends Fragment {


    public WebFileFragment() {
        // Required empty public constructor
    }

    public static WebFileFragment newInstance() {
        WebFileFragment fragment = new WebFileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_file, container, false);
    }
}
