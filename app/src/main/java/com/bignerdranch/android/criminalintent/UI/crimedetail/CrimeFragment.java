package com.bignerdranch.android.criminalintent.UI.crimedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.EditText;


import com.bignerdranch.android.criminalintent.BR;

import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeBinding;
import com.bignerdranch.android.criminalintent.Injection;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.UI.DatePickerFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CrimeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CrimeFragment#newInstanceForUUID} factory method to
 * create an instance of this fragment.
 */
public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_UUID = CrimeFragment.class.getCanonicalName() + ":ARG_CRIME_UUID";
    private static final String ARG_ADDNEW_FLAG = CrimeFragment.class.getCanonicalName().concat(" ARG_ADDNEW_CRIME");
    private static final String ARG_DATE_CRIME = CrimeFragment.class.getCanonicalName() + ":ARG_DATE_CRIME";

    private static final String TAG = CrimeFragment.class.getSimpleName().concat("::JPC");


    private UUID mCrime_UUID;

    private OnFragmentInteractionListener mListener;


    private CheckBox mSolvedCheckBox;
    private CheckBox mIsSeriousCrime;
    private Button mDateButton;
    private EditText mTitleField;


//    private boolean mAddingNewCrime;

    private DetailViewModel.Factory mViewModelFactory;

    private DetailViewModel mDetailViewModel;


    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {link Activity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: lifecycle");

    }


    /**
     * Called when the view previously created by {link #onCreateView} has
     * been detached from the fragment.  The next time the fragment needs
     * to be displayed, a new view will be created.  This is called
     * after {link #onStop()} and before {link #onDestroy()}.  It is called
     * <em>regardless</em> of whether {link #onCreateView} returned a
     * non-null view.  Internally it is called after the view's state has
     * been saved but before it has been removed from its parent.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: lifecycle");

    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: lifecycle");

    }

    /**
     * Called when the Fragment is no longer started.  This is generally
     * tied to link Activity#onStop() Activity.onStop} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: lifecycle");

    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {link #onStop()} and before {link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: lifecycle");

    }

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
    public static CrimeFragment newInstanceForUUID(UUID crime_UUID) {

        Log.d(TAG, "newInstanceForUUID: fragment created for updating existing crime ID: " + crime_UUID.toString());


        CrimeFragment fragment = new CrimeFragment();
        Bundle args = new Bundle();

        // changed initEmptyPager to return HEADER ENTITY so now crime_UUID can only be
        // valid item ID or HEADER ID
        args.putSerializable(ARG_CRIME_UUID, crime_UUID);

        fragment.setArguments(args);

        return fragment;
    }

//    public static CrimeFragment newInstanceToAddNewCrime() {
//        Log.d(TAG, "newInstanceToAddNewCrime: fragment created for adding new crime");
//
//
//        CrimeFragment fragment = new CrimeFragment();
//        Bundle args = new Bundle();
//        args.putBoolean(ARG_ADDNEW_FLAG, true);
//        fragment.setArguments(args);
//
//        return fragment;
//
//
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: lifecycle");

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: lifecycle");


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: lifecycle");

        mCrime_UUID = null;


        assert getArguments() != null;
        mCrime_UUID = (UUID) getArguments().getSerializable(ARG_CRIME_UUID);


        assert mCrime_UUID != null;


        //valid crime ID or HEAD since initEmptyPager now returns HEADER
        Log.d(TAG, "onCreate: found ARG_CRIME_UUID frag arg: " + mCrime_UUID.toString());


        mViewModelFactory = Injection.provideDetailModelFactory(getActivity(), mCrime_UUID);


        mDetailViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DetailViewModel.class);




    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: lifecycle");

//        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        FragmentCrimeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crime, container, false);


        binding.setCrimeTitleBinding(mDetailViewModel.crimeTitle);
        binding.setCrimeDateBinding(mDetailViewModel.crimeDate);
        binding.setCrimeSolvedBinding(mDetailViewModel.crimeSolved);
        binding.setCrimeSeriousBinding(mDetailViewModel.crimeSerious);
        binding.setStillLoadingBinding(mDetailViewModel.mIsLoading);

//        binding.setLifecycleOwner(getActivity());






        mDetailViewModel.mIsLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(final Observable sender, final int propertyId) {





            }
        });



        View v = binding.getRoot();

        setupViews(v);


        setupCallbacks();


        updateTitleUI();


        updateDateUI();

        return v;
    }

    private void setupCallbacks() {
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


        mDateButton.setOnClickListener(v1 -> {

            DialogFragment dialogFragment = DatePickerFragment.newInstance(mDetailViewModel.getDate());

            dialogFragment.show(Objects.requireNonNull(getFragmentManager()), ARG_DATE_CRIME);
        });


        mIsSeriousCrime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mDetailViewModel.setSeriousFlag(isChecked);

            updateTitleUI();

        });


        mSolvedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> mDetailViewModel.setSolvedFlag(isChecked));
    }

    private void setupViews(final View v) {
        mTitleField = v.findViewById(R.id.crime_title);
//        mTitleField.setText(mDetailViewModel.getTitle());

        mDateButton = v.findViewById(R.id.date_picker_btn);
//        mDateButton.setEnabled(true);

        mIsSeriousCrime = v.findViewById(R.id.serious_crime_box);
//        mIsSeriousCrime.setChecked(mDetailViewModel.getSeriousFlag());

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
//        mSolvedCheckBox.setChecked(mDetailViewModel.getSolvedFlag());
    }

    private void updateTitleUI() {
//        if (mDetailViewModel.getSeriousFlag()) {
//            mTitleField.setTextColor(Color.parseColor("#ffff4444"));
//
//        } else {
//            mTitleField.setTextColor(Color.parseColor("#ffffffff"));
//        }
    }

    private void updateDateUI() {
//        mDateButton.setText(mDetailViewModel.getDateString());

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d(TAG, "onDetach: lifecycle");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: lifecycle");

        Log.d(TAG, "onPause: upserting crime");

        if (!mTitleField.getText().toString().isEmpty()) {

            mDetailViewModel.addCrime();

        }

//        Log.d(TAG, "onPause:  ADDING NEW CRIME? == " + mDetailViewModel.isAddingNewCrime());

//        if (mDetailViewModel.isAddingNewCrime() && !mTitleField.getText().toString().isEmpty()) {
//
//            Log.d(TAG, "onPause: INSERTING CRIME TO DB ");
//
//            mDetailViewModel.addCrime();
////            mAddingNewCrime=false;
//
//
//            //FIXME once everything is working I wont need to check if that string is empty
//        } else if (!mDetailViewModel.isAddingNewCrime() && !mTitleField.getText().toString().isEmpty()) {
//
//            //can also check inside DetailModelView if we made any changes
//            //before pushing update to repo but for now just make sure title != null
//
//            Log.d(TAG, "onPause: UPDATING CRIME");
//
//            mDetailViewModel.updateCrime();
//
//        }
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
