package com.sshousing.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sshousing.R;
import com.sshousing.database.DataProvider;
import com.sshousing.utilities.SpinnerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ilyas on 6/10/17.
 */

public class UnitEditFragment extends Fragment{

    EditText unitNumber,unitFloor;
    Spinner spinner;
    int buidlingId;
    SpinnerAdapter[] spinnerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentunitedit, container, false);
        spinner = (Spinner) view.findViewById(R.id.buildingspinner);
        unitNumber = (EditText) view.findViewById(R.id.unitnumbereditText);
        unitFloor = (EditText) view.findViewById(R.id.unitflooreditText);
        Button addButton = (Button) view.findViewById(R.id.addunitbutton);
        fillspinner();
        addButton.findViewById(R.id.addunitbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataProvider dataProvider = new DataProvider(getActivity());
                dataProvider.open();
                long l = dataProvider.insertUnit(buidlingId, unitNumber.getText().toString(), Integer.parseInt(unitFloor.getText().toString()));
                dataProvider.close();
                Toast.makeText(getActivity(), "Unit successfully add with id: " + l, Toast.LENGTH_SHORT).show();
                unitNumber.setText(null);
                unitFloor.setText(null);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id: " + id + ", position: " + position + ", buildingid: " + spinnerAdapter[position].getId(), Toast.LENGTH_SHORT).show();
                buidlingId = spinnerAdapter[position].getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
    private void fillspinner(){
        DataProvider dataProvider = new DataProvider(getActivity());
        dataProvider.open();
        Cursor cursor = dataProvider.listBuilding();
        spinnerAdapter = new SpinnerAdapter[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                spinnerAdapter[i] = new SpinnerAdapter(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
                i = i + 1;
            } while (cursor.moveToNext());
        }
        ArrayAdapter<SpinnerAdapter> adapter =new ArrayAdapter<SpinnerAdapter>(getActivity(), android.R.layout.simple_spinner_item, spinnerAdapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        cursor.close();
        dataProvider.close();
    }
}
