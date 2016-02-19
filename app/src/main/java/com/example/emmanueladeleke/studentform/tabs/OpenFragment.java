package com.example.emmanueladeleke.studentform.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emmanueladeleke.studentform.R;
import com.example.emmanueladeleke.studentform.UserDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenFragment extends Fragment {

    public OpenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UserDialog.showMessageToUser(getActivity(), "HEY");
        return inflater.inflate(R.layout.fragment_open, container, false);
    }

}
