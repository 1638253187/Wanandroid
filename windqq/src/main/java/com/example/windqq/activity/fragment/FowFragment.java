package com.example.windqq.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windqq.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FowFragment extends Fragment {


    public FowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate (R.layout.fragment_fow, container, false);
        return inflate;
    }

}
