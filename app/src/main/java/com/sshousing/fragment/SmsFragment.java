package com.sshousing.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.sshousing.R;
import com.sshousing.database.DataProvider;
import com.sshousing.database.DatabaseHelper;

public class SmsFragment extends Fragment {

    EditText number, message;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_sms, container, false);
        number = (EditText) view.findViewById(R.id.numbersmseditText);
        message = (EditText) view.findViewById(R.id.messagesmseditText);
        Button sendButton = (Button) view.findViewById(R.id.sendsmsbutton);

        sendButton.findViewById(R.id.sendsmsbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number.getText().toString().isEmpty() || message.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all the information ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "number: " + number.getText().toString() + ", message: " + message.getText().toString(), Toast.LENGTH_SHORT).show();
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number.getText().toString(), null,  message.getText().toString(), null, null);

                }
            }
        });
        return view;
    }
}
