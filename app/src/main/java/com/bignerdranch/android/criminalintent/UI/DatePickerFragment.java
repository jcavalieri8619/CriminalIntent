package com.bignerdranch.android.criminalintent.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.bignerdranch.android.criminalintent.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DatePickerFragment extends DialogFragment {

    private static String ARG_DATE = DatePickerFragment.class.getCanonicalName().concat(": date");


    DatePicker mDatePicker;
    TimePicker mTimePicker;

    Calendar updatedCalendar;

    OnDatePickedListener mListener=null;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setArguments(args);
        return datePickerFragment;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        if (context instanceof OnDatePickedListener) {
            mListener = (OnDatePickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Override to build your own custom Dialog container.  This is typically
     * used to show an AlertDialog instead of a generic Dialog; when doing so,
     *  #onCreateView(LayoutInflater, ViewGroup, Bundle)} does not need
     * to be implemented since the AlertDialog takes care of its own content.
     * <p>
     * <p>This method will be called after {link #onCreate(Bundle)} and
     * before { #onCreateView(LayoutInflater, ViewGroup, Bundle)}.  The
     * default implementation simply instantiates and returns a {link Dialog}
     * class.
     * <p>
     * <p><em>Note: DialogFragment own the {@link Dialog#setOnCancelListener
     * Dialog.setOnCancelListener} and {@link Dialog#setOnDismissListener
     * Dialog.setOnDismissListener} callbacks.  You must not set them yourself.</em>
     * To find out about these events, override {#onCancel(DialogInterface)}
     * and { #onDismiss(DialogInterface)}.</p>
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(date);



        updatedCalendar = (Calendar) calendar.clone();


        View v = LayoutInflater.from(getActivity()).inflate(R.layout.datepicker_layout, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_picker)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        mListener.onDatePicked(updatedCalendar);

                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {

                    }
                });


        mTimePicker = v.findViewById(R.id.time_picker);

        mTimePicker.setIs24HourView(false);
        mTimePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setMinute(calendar.get(Calendar.MINUTE));


        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(final TimePicker view, final int hourOfDay, final int minute) {
                updatedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                updatedCalendar.set(Calendar.MINUTE, minute);



            }
        });



        mDatePicker = v.findViewById(R.id.date_picker);


        mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                updatedCalendar.set(Calendar.YEAR, year);
                updatedCalendar.set(Calendar.MONTH, monthOfYear);
                updatedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            }
        });




        return builder.create();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {



        super.onDismiss(dialog);
    }

    public interface OnDatePickedListener{
        void onDatePicked(Calendar picked_date);
    }



}
