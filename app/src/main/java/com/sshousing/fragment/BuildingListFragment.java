package com.sshousing.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.sshousing.R;
import com.sshousing.database.DataProvider;
import com.sshousing.database.DatabaseHelper;

/**
 * Created by ilyas on 7/9/17.
 */

public class BuildingListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentbuildinglist, container, false);
        ListView listView = (ListView) view.findViewById(R.id.buildinglist);
        String[] from = new String[] {/*DatabaseHelper.BUILDINGID, */DatabaseHelper.ADDRESS, DatabaseHelper.NBROFUNIT};
        int[] to = new int[] {R.id.addressbuildinglist, R.id.unitnbrlist};
        DataProvider dataProvider = new DataProvider(getActivity());
        dataProvider.open();
        Cursor cursor = dataProvider.listBuilding();
        ListAdapter listAdapter = new SimpleCursorAdapter(getActivity(), R.layout.listbuilding, cursor, from, to);
        listView.setAdapter(listAdapter);
        dataProvider.close();

        return view;
    }
}
