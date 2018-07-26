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

/**
 * Created by ilyas on 6/10/17.
 */

public class UnitEditFragment extends Fragment{

    EditText unitNumber,unitFloor;
    Spinner buildingSpinner, unitFloorSpinner;
    int buildingId = 0 , floorNumber = 0;
    SpinnerAdapter[] spinnerAdapterBuilding, spinnerAdapterFloor;

    //TODO this is shit code, do better!
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentunitedit, container, false);
        buildingSpinner = (Spinner) view.findViewById(R.id.buildingspinner);
        unitFloorSpinner = (Spinner) view.findViewById(R.id.floorspinner);
        unitNumber = (EditText) view.findViewById(R.id.unitnumbereditText);
//        unitFloor = (EditText) view.findViewById(R.id.unitflooreditText);
        Button addButton = (Button) view.findViewById(R.id.addunitbutton);
        fillSpinner();
        addButton.findViewById(R.id.addunitbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (unitNumber.getText().toString().isEmpty() || buildingId == 0 || floorNumber == 0) {
                    Toast.makeText(getActivity(), "Please fill in all the information ", Toast.LENGTH_SHORT).show();
                } else {
                    DataProvider dataProvider = new DataProvider(getActivity());
                    dataProvider.open();
                    long l = dataProvider.insertUnit(buildingId, unitNumber.getText().toString(), floorNumber);
                    dataProvider.close();
                    Toast.makeText(getActivity(), "Unit successfully add with id: " + l, Toast.LENGTH_SHORT).show();
                    unitNumber.setText(null);
//                unitFloor.setText(null);
                }
            }
        });
        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id: " + id + ", position: " + position + ", buildingid: " + spinnerAdapterBuilding[position].getId(), Toast.LENGTH_SHORT).show();
                buildingId = spinnerAdapterBuilding[position].getId();
                resizeSpinner(spinnerAdapterBuilding[position].getFloors());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //this shouldnt happen
            }
        });
        unitFloorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "floor number: " + id + ", floor id: " + spinnerAdapterFloor[position].getId(), Toast.LENGTH_SHORT).show();
                floorNumber = spinnerAdapterFloor[position].getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //this shouldnt happen
            }
        });

        return view;
    }

    private void fillSpinner(){
        DataProvider dataProvider = new DataProvider(getActivity());
        dataProvider.open();
        Cursor cursor = dataProvider.listBuilding();
        spinnerAdapterBuilding = new SpinnerAdapter[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                spinnerAdapterBuilding[i] = new SpinnerAdapter(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(3)));
                i = i + 1;
            } while (cursor.moveToNext());
        }
        ArrayAdapter<SpinnerAdapter> adapter =new ArrayAdapter<SpinnerAdapter>(getActivity(), android.R.layout.simple_spinner_item, spinnerAdapterBuilding);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(adapter);

        cursor.close();
        dataProvider.close();
    }

    private void resizeSpinner(int size){
        spinnerAdapterFloor = new SpinnerAdapter[size];
        for(int i=0; i < size; i++){
            spinnerAdapterFloor[i] = new SpinnerAdapter(i+1, String.valueOf(i+1), i+1);
        }
        ArrayAdapter<SpinnerAdapter> adapter =new ArrayAdapter<SpinnerAdapter>(getActivity(), android.R.layout.simple_spinner_item, spinnerAdapterFloor);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitFloorSpinner.setAdapter(adapter);
    }
}
