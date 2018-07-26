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
 * Created by ilyas on 9/14/17.
 */

public class LeaseListFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentleaselist, container, false);
        ListView listView = (ListView) view.findViewById(R.id.leaselist);
        String[] from = new String[] {/*DatabaseHelper.BUILDINGID, */DatabaseHelper._ID, DatabaseHelper.LEASESTART};
        int[] to = new int[] {R.id.oneleasetextView, R.id.twoleaselisttextView};
        DataProvider dataProvider = new DataProvider(getActivity());
        dataProvider.open();
        Cursor cursor = dataProvider.listLease();
        ListAdapter listAdapter = new SimpleCursorAdapter(getActivity(), R.layout.listlease, cursor, from, to);
        listView.setAdapter(listAdapter);
        dataProvider.close();

        return view;
    }
}
