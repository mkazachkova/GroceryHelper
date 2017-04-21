package com.example.grocery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kiki on 4/20/17.
 */
//TODO: this frag is not used!
public class ReceiptImageFrag extends Fragment {

    public ReceiptImageFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.receipt_image_fragment, container, false);
        getActivity().setTitle("Receipt");
        return view;
    }

    public void onClick(View v){
        //TODO : add function
    }
}
