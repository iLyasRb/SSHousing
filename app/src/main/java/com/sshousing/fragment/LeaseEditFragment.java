package com.sshousing.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sshousing.R;
import com.sshousing.database.DataProvider;
import com.sshousing.utilities.SpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ilyas on 6/15/17.
 */

public class LeaseEditFragment extends Fragment {


    Spinner tenantSpinner, buildingSpinner, unitSpinner;
    EditText floorNumber, startDate, endDate, rentamount, deposit, note, dueDate;
    SpinnerAdapter[] spinnerAdapterTenant, spinnerAdapterBuilding, spinnerAdapterUnit;
    Calendar myCalendar = Calendar.getInstance();
    int unitId, tenantId, buildingId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragmentleaseedit, container, false);
        floorNumber = (EditText) view.findViewById(R.id.floorleaseeditText);
        startDate = (EditText) view.findViewById(R.id.startdateleaseeditText);
        endDate = (EditText) view.findViewById(R.id.enddateleaseeditText);
        dueDate = (EditText) view.findViewById(R.id.rentduedateleaseeditText);
        rentamount = (EditText) view.findViewById(R.id.rentamountleaseeditText);
        deposit = (EditText) view.findViewById(R.id.depositleaseeditText);
        note = (EditText) view.findViewById(R.id.noteleaseeditText);
        tenantSpinner = (Spinner) view.findViewById(R.id.tenantleasespinner);
        buildingSpinner = (Spinner) view.findViewById(R.id.buildingAddressleasespinner);
        unitSpinner = (Spinner) view.findViewById(R.id.unitleasespinner);
        Button addButton = (Button) view.findViewById(R.id.addleasebutton);
        fillTenantSpinner();
        fillBuildingSpinner();
        tenantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id: " + id + ", position: " + position + ", buildingid: " + spinnerAdapterTenant[position].getId(), Toast.LENGTH_SHORT).show();
                tenantId = spinnerAdapterTenant[position].getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id: " + id + ", position: " + position + ", buildingid: " + spinnerAdapterBuilding[position].getId(), Toast.LENGTH_SHORT).show();
                buildingId = spinnerAdapterBuilding[position].getId();
                fillUnitSpinner(buildingId);
//                floorNumber.setText(String.valueOf(spinnerAdapterBuilding[position].getFloors()));
//                resizeSpinner(spinnerAdapterBuilding[position].getFloors());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //this shouldnt happen
            }
        });
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id: " + id + ", position: " + position + ", buildingid: " + spinnerAdapterBuilding[position].getId(), Toast.LENGTH_SHORT).show();
                unitId = spinnerAdapterUnit[position].getId();
                floorNumber.setText(String.valueOf(spinnerAdapterUnit[position].getFloors()));
//                resizeSpinner(spinnerAdapterBuilding[position].getFloors());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //this shouldnt happen
            }
        });

        final DatePickerDialog startDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDate.setText(new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(myCalendar.getTime()));
                Toast.makeText(getActivity(), "datePickerDialog: " + new SimpleDateFormat("", Locale.US).format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
            }

        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

        final DatePickerDialog endDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endDate.setText(new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(myCalendar.getTime()));
                Toast.makeText(getActivity(), "datePickerDialog: " + new SimpleDateFormat("", Locale.US).format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
            }

        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePickerDialog.show();
                Calendar mcurrentTime = Calendar.getInstance();
                Toast.makeText(getActivity(), "startDay: " + new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
                }});

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDatePickerDialog.show();
                Calendar mcurrentTime = Calendar.getInstance();
                Toast.makeText(getActivity(), "startDay: " + new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
            }});

        addButton.findViewById(R.id.addleasebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floorNumber.getText().toString().isEmpty() || startDate.getText().toString().isEmpty() ||  deposit.getText().toString().isEmpty()
                        ||  rentamount.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all the information ", Toast.LENGTH_SHORT).show();
                } else {
                    DataProvider dataProvider = new DataProvider(getActivity());
                    dataProvider.open();
                    long l = dataProvider.insertLease(buildingId, unitId, tenantId, startDate.getText().toString(), endDate.getText().toString(),
                                                      Double.parseDouble(rentamount.getText().toString()), dueDate.getText().toString(),
                                                      Double.parseDouble(deposit.getText().toString()), note.getText().toString());
                    dataProvider.close();
                    Toast.makeText(getActivity(), "testing: " + l, Toast.LENGTH_SHORT).show();
                    floorNumber.setText(null);
                    startDate.setText(null);
                    endDate.setText(null);
                    dueDate.setText(null);
                    rentamount.setText(null);
                    deposit.setText(null);
                    note.setText(null);
                }
            }
        });

 /*  startDay.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                datePickerDialog.show();
                Calendar mcurrentTime = Calendar.getInstance();
                Toast.makeText(getActivity(), "startDay: " + new SimpleDateFormat("MM-dd-yyyy", Locale.US).format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();

                return false;
            }
        });*/

/* int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startDay.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
*/


                /*DatePickerDialog  StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        activitydate.setText(dateFormatter.format(newDate.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                btn_checkin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartTime .show():

                    });



                DatePickerDialog.OnDateSetListener datePickerDialogListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        startDay.setText(new SimpleDateFormat("", Locale.US).format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(getActivity(), datePickerDialog, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/
//        nbroffloors = (EditText) view.findViewById(R.id.unitnbreditText);
//        Button addButton = (Button) view.findViewById(R.id.addbuildingbutton);

//        addButton.findViewById(R.id.addbuildingbutton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DataProvider dataProvider = new DataProvider(getActivity());
//                dataProvider.open();
//                long buidingId = dataProvider.insertBuilding(address.getText().toString(), Integer.parseInt(nbrofunits.getText().toString()), Integer.parseInt(nbroffloors.getText().toString()));
//                dataProvider.close();
//                Toast.makeText(getActivity(), "Building successfully add with id: " + buidingId, Toast.LENGTH_SHORT).show();
//                address.setText(null);
//                nbrofunits.setText(null);
//                nbroffloors.setText(null);
//            }
//        });

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

    private void fillBuildingSpinner(){
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
    private void fillUnitSpinner(int _id){
        DataProvider dataProvider = new DataProvider(getActivity());
        dataProvider.open();
        Cursor cursor = dataProvider.listUnitByBuilding(_id);
        spinnerAdapterUnit = new SpinnerAdapter[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                spinnerAdapterUnit[i] = new SpinnerAdapter(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)));
                i = i + 1;
            } while (cursor.moveToNext());
        }
        ArrayAdapter<SpinnerAdapter> adapter =new ArrayAdapter<SpinnerAdapter>(getActivity(), android.R.layout.simple_spinner_item, spinnerAdapterUnit);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        cursor.close();
        dataProvider.close();
    }
}
