package com.bignerdranch.android.criminalintent.UI.crimedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;

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



import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeBinding;
import com.bignerdranch.android.criminalintent.Injection;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.UI.DatePickerFragment;

import java.util.Calendar;

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
    private static final String ARG_DATE_CRIME = CrimeFragment.class.getCanonicalName() + ":ARG_DATE_CRIME";

    private static final String TAG = CrimeFragment.class.getSimpleName().concat("::JPC");


    private OnFragmentInteractionListener mListener;


    private CheckBox mSolvedCheckBox;
    private CheckBox mIsSeriousCrime;
    private Button mDateButton;
    private EditText mTitleField;


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

        Log.d(TAG, "newInstanceForUUID: fragment created for  crime ID: " + crime_UUID.toString());


        CrimeFragment fragment = new CrimeFragment();
        Bundle args = new Bundle();

        // changed initEmptyPager to return HEADER ENTITY so now crime_UUID can only be
        // valid item ID or HEADER ID
        args.putSerializable(ARG_CRIME_UUID, crime_UUID);

        fragment.setArguments(args);

        return fragment;
    }




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

        UUID crime_UUID = null;


        assert getArguments() != null;
        crime_UUID = (UUID) getArguments().getSerializable(ARG_CRIME_UUID);


        assert crime_UUID != null;


        //valid crime ID or HEAD since initEmptyPager now returns HEADER
        Log.d(TAG, "onCreate: found ARG_CRIME_UUID frag arg: " + crime_UUID.toString());


        final DetailViewModel.Factory viewModelFactory = Injection.provideDetailModelFactory(getActivity(), crime_UUID);


        mDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel.class);




    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: lifecycle");

        FragmentCrimeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crime, container, false);


        binding.setCrimeTitleBinding(mDetailViewModel.crimeTitle);
        binding.setCrimeDateBinding(mDetailViewModel.crimeDate);
        binding.setCrimeSolvedBinding(mDetailViewModel.crimeSolved);
        binding.setCrimeSeriousBinding(mDetailViewModel.crimeSerious);
        binding.setStillLoadingBinding(mDetailViewModel.mIsLoading);

//        binding.setLifecycleOwner(getActivity());




        View v = binding.getRoot();

        setupViews(v);


        setupCallbacks();


        updateTitleUI();



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

        mDateButton = v.findViewById(R.id.date_picker_btn);

        mIsSeriousCrime = v.findViewById(R.id.serious_crime_box);

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
    }

    private void updateTitleUI() {
//        if (mDetailViewModel.getSeriousFlag()) {
//            mTitleField.setTextColor(Color.parseColor("#ffff4444"));
//
//        } else {
//            mTitleField.setTextColor(Color.parseColor("#ffffffff"));
//        }
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
