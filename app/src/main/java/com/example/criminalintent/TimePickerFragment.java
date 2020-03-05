package com.example.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {

    private static final String ARG_TIME = "time_of_day";
    public static final String EXTRA_TIME = "com.example.criminalintent";
    private TimePicker timePicker;
    private DatePicker datePicker;
    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time_of_day,null);

        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

         final int year = calendar.get(Calendar.YEAR);
         final int month = calendar.get(Calendar.MONTH);
         final int day = calendar.get(Calendar.DAY_OF_MONTH);
         int hour = calendar.get(Calendar.HOUR_OF_DAY);
         int minute = calendar.get(Calendar.MINUTE);

        timePicker = v.findViewById(R.id.dialog_time_of_day_picker);
        timePicker.setHour(hour);
        timePicker.setMinute(minute);
       // datePicker = v.findViewById(R.id.dialog_date_picker);
      //  datePicker.init(year,month,day, null);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();
                        Date date = new GregorianCalendar(year, month, day,hour,minute).getTime();

                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);

        getTargetFragment().
                onActivityResult(getTargetRequestCode(), resultCode, intent);

    }
}
