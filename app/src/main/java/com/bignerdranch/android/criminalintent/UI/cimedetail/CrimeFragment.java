package com.bignerdranch.android.criminalintent.UI.cimedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bignerdranch.android.criminalintent.Injection;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.UI.CrimeFields;
import com.bignerdranch.android.criminalintent.UI.DatePickerFragment;
import com.bignerdranch.android.criminalintent.UI.ViewModelFactory;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

import static android.widget.CompoundButton.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CrimeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CrimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrimeFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CRIME_UUID = CrimeFragment.class.getCanonicalName() + ":CRIME_UUID";
    private static final String DATE_CRIME = CrimeFragment.class.getCanonicalName() + ":DATE_CRIME";

    private static final String TAG = CrimeFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private UUID mCrime_UUID;

    private OnFragmentInteractionListener mListener;


    /**
     * BELOW ARE MY VARIABLES : ABOVE ARE BOILER PLATE
     */


    private CheckBox mSolvedCheckBox;
    private CheckBox mIsSeriousCrime;
    private Button mDateButton;
    private EditText mTitleField;


    private ViewModelFactory mViewModelFactory;

    private DetailViewModel mDetailViewModel;



    public CrimeFragment() {
        // Required empty public constructor
    }


    public void onUserChangedDateTime(Calendar new_date) {
        mDetailViewModel.setDate(new_date.getTime());

        updateDateUI();

    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @return A new instance of fragment CrimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrimeFragment newInstance(UUID crime_UUID ) {

        Log.d(TAG, "newInstance: ");


        CrimeFragment fragment = new CrimeFragment();
        Bundle args = new Bundle();

        if (crime_UUID != null) {
            Log.d(TAG, "newInstance: crime_uuid != null");
            args.putSerializable(CRIME_UUID, crime_UUID);

        }
        fragment.setArguments(args);

        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

        if (getArguments() != null) {
            mCrime_UUID = (UUID) getArguments().getSerializable(CRIME_UUID);
            Log.d(TAG, "onCreate: found CRIME_UUID frag arg");

        }

        mViewModelFactory = Injection.provideViewModelFactory(getActivity());

        mDetailViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DetailViewModel.class);


        if (mCrime_UUID != null) {
            Log.d(TAG, "onCreate: calling initFieldsFromUUID");

            mDetailViewModel.initFieldsFromUUID(mCrime_UUID);


        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: creating crime fragment view");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = v.findViewById(R.id.crime_title);
        mTitleField.setText(mDetailViewModel.getTitle());

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

                mDetailViewModel.setTitle(String.valueOf(s));

            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });


        updateTitleUI();

        mDateButton = v.findViewById(R.id.date_picker_btn);
        mDateButton.setEnabled(true);

        updateDateUI();


        mDateButton.setOnClickListener(v1 -> {

            DialogFragment dialogFragment = DatePickerFragment.newInstance(mDetailViewModel.getDate());

            dialogFragment.show(Objects.requireNonNull(getFragmentManager()), DATE_CRIME);
        });

        mIsSeriousCrime = v.findViewById(R.id.serious_crime_box);
        mIsSeriousCrime.setChecked(mDetailViewModel.getSeriousFlag());
        mIsSeriousCrime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mDetailViewModel.setSeriousFlag(isChecked);

            updateTitleUI();

        });

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mDetailViewModel.getSolvedFlag());

        mSolvedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> mDetailViewModel.setSolvedFlag(isChecked));


        return v;
    }

    private void updateTitleUI() {
        if (mDetailViewModel.getSeriousFlag()) {
            mTitleField.setTextColor(Color.parseColor("#ffff4444"));

        }else {
            mTitleField.setTextColor(Color.parseColor("#ffffffff"));
        }
    }

    private void updateDateUI() {
        mDateButton.setText(mDetailViewModel.getDateString());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onPause() {
        super.onPause();

        // this adds the crime defined by the fields
        mDetailViewModel.addCrime();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
