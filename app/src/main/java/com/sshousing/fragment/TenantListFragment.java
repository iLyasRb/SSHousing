package com.sshousing.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sshousing.R;
import com.sshousing.database.DataProvider;
import com.sshousing.database.DatabaseHelper;

/**
 * Created by ilyas on 9/14/17.
 */

public class TenantListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragmenttenantlist, container, false);
        ListView listView = (ListView) view.findViewById(R.id.tenantlist);
        String[] from = new String[] {DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.PHONE};
        int[] to = new int[] {R.id.tenantIdlisttextView, R.id.tenantnamelist, R.id.tenantphonelist};
        final DataProvider dataProvider = new DataProvider(getActivity());
        dataProvider.open();
        Cursor cursor = dataProvider.listTenant();
        ListAdapter listAdapter = new SimpleCursorAdapter(getActivity(), R.layout.listtenant, cursor, from, to);
        listView.setAdapter(listAdapter);
        dataProvider.close();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
//                Toast.makeText(getActivity(), "id:" + id + ", pos: " + pos + ", real id: " + ((TextView) view.findViewById(R.id.tenantIdlisttextView)).getText().toString(), Toast.LENGTH_SHORT).show();
                dataProvider.open();
                dataProvider.deleteTenant((int) id);
                dataProvider.close();
                Toast.makeText(getActivity(), "deleted" ,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }
}
