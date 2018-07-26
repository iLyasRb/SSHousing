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
 * Created by ilyas on 6/15/17.
 */

public class TenantEditFragment extends Fragment{

    EditText tenantname,tenantphone;
//    TODO add the contact import
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragmenttenantedit, container, false);
        tenantname = (EditText) view.findViewById(R.id.tenantnameeditText);
        tenantphone = (EditText) view.findViewById(R.id.tenantphoneeditText);
        Button addButton = (Button) view.findViewById(R.id.addtenantbutton);

        addButton.findViewById(R.id.addtenantbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tenantname.getText().toString().isEmpty() || tenantphone.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all the information ", Toast.LENGTH_SHORT).show();
                } else {
                    DataProvider dataProvider = new DataProvider(getActivity());
                    dataProvider.open();
                    long tenantId = dataProvider.insertTenant(tenantname.getText().toString(), tenantphone.getText().toString());
                    dataProvider.close();
                    Toast.makeText(getActivity(), "Building successfully add with id: " + tenantId, Toast.LENGTH_SHORT).show();
                    tenantname.setText(null);
                    tenantphone.setText(null);
                }
            }
        });
        return view;
    }
}
