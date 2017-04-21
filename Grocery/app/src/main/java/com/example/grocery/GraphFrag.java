package com.example.grocery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JSK on 4/18/17.
 */

public class GraphFrag extends Fragment {
    public static GraphFrag newInstance() {
        GraphFrag fragment = new GraphFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_graph_fragment, container, false);
    }

}
