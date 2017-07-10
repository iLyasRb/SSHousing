package com.sshousing.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sshousing.R;

/**
 * Created by ilyas on 6/18/17.
 */

public class MainMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentmainmenu, container, false);
        addListenerOnBulding(view);
        addListenerOnUnit(view);
        return view;
    }

    public void addListenerOnBulding(View view) {
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButtonBuilding);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Fragment fragment = new BuildingListFragment();
                fragmentManaging(fragment);
                Toast.makeText(getActivity(), "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addListenerOnUnit(View view) {
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButtonUnit);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Fragment fragment = new UnitListFragment();
                fragmentManaging(fragment);
                Toast.makeText(getActivity(), "ImageButton is clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fragmentManaging(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}
