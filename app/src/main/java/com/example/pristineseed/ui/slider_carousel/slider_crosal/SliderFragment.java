package com.example.pristineseed.ui.slider_carousel.slider_crosal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pristineseed.R;

public class SliderFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ImageView fragment_title;
    TextView heading_Text, sub_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slider, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragment_title = view.findViewById(R.id.fragment_title);
        heading_Text = view.findViewById(R.id.heading_Text);
        sub_text = view.findViewById(R.id.sub_text);
        if (getArguments() != null) {
            fragment_title.setImageDrawable(getActivity().getResources().getDrawable(getArguments().getInt("flag", 0)));
            heading_Text.setText(getArguments().getString("heading", ""));
            sub_text.setText(getArguments().getString("sub_text", ""));
        }
    }


}