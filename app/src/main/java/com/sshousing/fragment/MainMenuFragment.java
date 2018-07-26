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
        /*addListenerOnBuilding(view);
        addListenerOnUnit(view);
        addListenerOnTenant(view);*/
        genericListener(view, R.id.imageButtonBuilding, new BuildingListFragment());
        genericListener(view, R.id.imageButtonUnit, new UnitListFragment());
        genericListener(view, R.id.imageButtonTenant, new TenantListFragment());
        genericListener(view, R.id.imageButtonLease, new LeaseListFragment());
        return view;
    }

    /*public void addListenerOnBuilding(View view) {
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButtonBuilding);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Fragment fragment = new BuildingListFragment();
                fragmentManaging(fragment);
                Toast.makeText(getActivity(), "Building ImageButton is clicked!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Unit ImageButton is clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addListenerOnTenant(View view) {
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButtonTenant);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Fragment fragment = new UnitListFragment();
                fragmentManaging(fragment);
                Toast.makeText(getActivity(), "Unit ImageButton is clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void genericListener(View view, int imageId, final Fragment fragment){
        ImageButton imageButton = (ImageButton) view.findViewById(imageId);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentManaging(fragment);
                Toast.makeText(getActivity(), fragment.getClass().getName()+ " ImageButton is clicked!", Toast.LENGTH_SHORT).show();
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
