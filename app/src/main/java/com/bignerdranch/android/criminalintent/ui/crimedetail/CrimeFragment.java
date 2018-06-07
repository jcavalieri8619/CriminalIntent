package com.bignerdranch.android.criminalintent.ui.crimedetail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.databinding.DataBindingUtil;

import android.databinding.Observable;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.criminalintent.PictureUtils;
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeBinding;
import com.bignerdranch.android.criminalintent.Injection;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.ui.DatePickerFragment;
import com.bignerdranch.android.criminalintent.model.Crime;

import java.io.File;
import java.util.Calendar;

import java.util.List;
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

    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;


    private OnFragmentInteractionListener mListener;


    private CheckBox mSolvedCheckBox;
    private Button mDateButton;
    private EditText mTitleField;


    private DetailViewModel mDetailViewModel;
    private ViewStub mViewStub;
    private ImageButton mPhotoCaptureBtn;
    private ImageView mPhotoView;


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


        setHasOptionsMenu(true);


        UUID crime_UUID = null;


        assert getArguments() != null;
        crime_UUID = (UUID) getArguments().getSerializable(ARG_CRIME_UUID);


        assert crime_UUID != null;


        //valid crime ID or HEAD since initEmptyPager now returns HEADER
        Log.d(TAG, "onCreate: found ARG_CRIME_UUID frag arg: " + crime_UUID.toString());


        final DetailViewModel.Factory viewModelFactory = Injection.provideDetailModelFactory(getContext(), crime_UUID);


        mDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel.class);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: lifecycle");

        FragmentCrimeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crime, container, false);


        binding.setStillLoadingBinding(mDetailViewModel.mIsLoading);
        binding.setObservableCrime(mDetailViewModel.observableEntity);


        binding.setLifecycleOwner(this);


        View v = binding.getRoot();

        setupViews(v);


        setupCallbacks();



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


        mDetailViewModel.mIsLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(final Observable sender, final int propertyId) {
                Log.d(TAG, "onPropertyChanged: update photo on isLoading changed");

                if (mDetailViewModel.getSuspectName() != null && !mDetailViewModel.getSuspectName().isEmpty()) {
                    inflateSuspectBadge(mDetailViewModel.getSuspectName());

                }
                updatePhotoView();
            }
        });

        mDateButton.setOnClickListener(v1 -> {

            DialogFragment dialogFragment = DatePickerFragment.newInstance(mDetailViewModel.getDate());

            dialogFragment.show(Objects.requireNonNull(getFragmentManager()), ARG_DATE_CRIME);
        });


        mPhotoCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File photoFile = retrievePhotoFile();
                Log.d(TAG, "onClick PhotoCaptureButton: photo file " + photoFile.getPath().toString());

                boolean canTakePhoto = photoFile != null &&
                        null != cameraIntent.resolveActivity(getActivity().getPackageManager());

                if ( ! canTakePhoto){

                    mPhotoCaptureBtn.setEnabled(false);

                    Toast.makeText(getContext(), "Camera Not Available", Toast.LENGTH_SHORT).show();

                    return;
                }


                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.bignerdranch.android.criminalintent.fileprovider",
                        photoFile);

                Log.d(TAG, "onClick: photo file uri " + uri.getPath().toString());

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager()
                        .queryIntentActivities(cameraIntent,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo info :
                        cameraActivities) {
                    getActivity().grantUriPermission(info.activityInfo.packageName,
                            uri,
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


                }

                startActivityForResult(cameraIntent, REQUEST_PHOTO);


            }
        });

        mSolvedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> mDetailViewModel.setSolvedFlag(isChecked));
    }

    private void setupViews(final View v) {
        mTitleField = v.findViewById(R.id.crime_title);

        mDateButton = v.findViewById(R.id.date_picker_btn);

        mSolvedCheckBox = v.findViewById(R.id.crime_solved);

        mViewStub = v.findViewById(R.id.viewstub);

        mPhotoCaptureBtn = v.findViewById(R.id.photo_capture_btn);

        mPhotoView = v.findViewById(R.id.photo_view);

        if (mDetailViewModel.getSuspectName() != null && !mDetailViewModel.getSuspectName().isEmpty()) {
            inflateSuspectBadge(mDetailViewModel.getSuspectName());

        }
        if (mDetailViewModel.hasPhoto()) {

            updatePhotoView();

        }

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

    private String buildCrimeReport() {

        Crime crime = mDetailViewModel.getCurrentCrime();

        String solvedString;

        if (crime.isSolved()) {

            solvedString = getString(R.string.crime_report_solved);
        } else {

            solvedString = getString(R.string.crime_report_unsolved);
        }

        String suspect = crime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {

            suspect = getString(R.string.crime_report_suspect, suspect);
        }


        return getString(R.string.crime_report, crime.getTitle(),
                Crime.crimeDateToString(crime.getDate()),
                solvedString, suspect);


    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_crime, menu);

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.find_suspects_item:
                final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                if (null == getActivity().getPackageManager().resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY)) {

                    item.setEnabled(false);
                    Toast.makeText(getContext(), "No Contacts App Available", Toast.LENGTH_SHORT).show();

                } else {

                    startActivityForResult(pickContact, REQUEST_CONTACT);
                }


                return true;

            case R.id.send_report_item:

                Intent implicitIntent = new Intent(Intent.ACTION_SEND);
                implicitIntent.setType("text/plain");
                implicitIntent.putExtra(Intent.EXTRA_TEXT,
                        buildCrimeReport());
                implicitIntent.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject));

                Intent chooserIntent = Intent.createChooser(implicitIntent, getString(R.string.send_report));

                startActivity(chooserIntent);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactURI = data.getData();

            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};

            try (Cursor c = getActivity().getContentResolver().query(contactURI, queryFields, null, null, null)) {

                if (c == null || c.getCount() == 0) {
                    return;
                }


                c.moveToFirst();
                String suspect = c.getString(0);
                mDetailViewModel.setSuspectName(suspect);
                inflateSuspectBadge(suspect);

            } catch (Exception e) {
                Log.d(TAG, "onActivityResult: exception occurred while fetching contact data", e);

            }


        }else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.bignerdranch.android.criminalintent.fileprovider",
                    retrievePhotoFile());

            Log.d(TAG, "onActivityResult: storing captured photo at URI: " + uri.getPath().toString());

            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();

            mDetailViewModel.setHasPhoto(true);

        }


    }

    private void inflateSuspectBadge(final String suspect) {
        ConstraintLayout subtree = (ConstraintLayout) mViewStub.inflate();
        TextView suspect_name = subtree.findViewById(R.id.suspect_pic_label);
        suspect_name.setText(suspect);
    }

    private void updatePhotoView() {
        File photoFile = retrievePhotoFile();
        Log.d(TAG, "updatePhotoView: photo file " + photoFile.getPath().toString());
        if (!photoFile.exists()) {
//            mPhotoView.setImageDrawable(null);
            Log.d(TAG, "updatePhotoView: PHOTO FILE DOESNT EXIST");
            mDetailViewModel.setHasPhoto(false);

        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
            mDetailViewModel.setHasPhoto(true);

        }

    }

    private File retrievePhotoFile() {
        File filesDir = getContext().getFilesDir();
        return new File(filesDir, mDetailViewModel.getCurrentCrime().buildPhotoPath());
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
