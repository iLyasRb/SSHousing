package com.sshousing.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sshousing.R;
import com.sshousing.database.DataProvider;
import com.sshousing.utilities.SpinnerAdapter;

/**
 * Created by ilyas on 6/15/17.
 */

public class PaymentFragment extends Fragment {

    EditText amount;
    Spinner tenantSpinner, buildingSpinner;
    SpinnerAdapter[] spinnerAdapterTenant, spinnerAdapterBuilding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_payment, container, false);
        amount = (EditText) view.findViewById(R.id.amountpaymenteditText);
        tenantSpinner = (Spinner) view.findViewById(R.id.tenantpaymentspinner);
        buildingSpinner = (Spinner) view.findViewById(R.id.buildingAddressleasespinner);
        Button savePayment = (Button) view.findViewById(R.id.savepaymentbutton);
        fillTenantSpinner();
//        fillBuildingSpinner(1);
        savePayment.findViewById(R.id.savepaymentbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all the information ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "amount: " + amount.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void fillTenantSpinner(){
        DataProvider dataProvider = new DataProvider(getActivity());
        dataProvider.open();
        Cursor cursor = dataProvider.listTenant();
        spinnerAdapterTenant = new SpinnerAdapter[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                spinnerAdapterTenant[i] = new SpinnerAdapter(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)));
                i = i + 1;
            } while (cursor.moveToNext());
        }
        ArrayAdapter<SpinnerAdapter> adapter =new ArrayAdapter<SpinnerAdapter>(getActivity(), android.R.layout.simple_spinner_item, spinnerAdapterTenant);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tenantSpinner.setAdapter(adapter);

        cursor.close();
        dataProvider.close();
    }

    private void fillBuildingSpinner(int adid){
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
}
