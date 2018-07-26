package com.sshousing.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sshousing.R;
import com.sshousing.database.DataProvider;

/**
 * Created by ilyas on 6/10/17.
 */

public class BuildingEditFragment extends Fragment{

    EditText address, nbrofunits, nbroffloors;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        /*return super.onCreateView(inflater, container, savedInstanceState);*/

        View view = inflater.inflate(R.layout.fragmentbuildingedit, container, false);
        address = (EditText) view.findViewById(R.id.addresseditText);
        nbrofunits = (EditText) view.findViewById(R.id.unitnbreditText);
        nbroffloors = (EditText) view.findViewById(R.id.floornbreditText);
        Button addButton = (Button) view.findViewById(R.id.addbuildingbutton);

        addButton.findViewById(R.id.addbuildingbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address.getText().toString().isEmpty() || nbrofunits.getText().toString().isEmpty() || nbroffloors.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all the information ", Toast.LENGTH_SHORT).show();
                } else {
                DataProvider dataProvider = new DataProvider(getActivity());
                dataProvider.open();
                long buidingId = dataProvider.insertBuilding(address.getText().toString(), Integer.parseInt(nbrofunits.getText().toString()), Integer.parseInt(nbroffloors.getText().toString()));
                dataProvider.close();
                Toast.makeText(getActivity(), "Building successfully add with id: " + buidingId, Toast.LENGTH_SHORT).show();
                address.setText(null);
                nbrofunits.setText(null);
                nbroffloors.setText(null);
                }
            }
        });

        return view;
    }
}
