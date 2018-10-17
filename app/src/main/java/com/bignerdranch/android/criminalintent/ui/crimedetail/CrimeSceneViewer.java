package com.bignerdranch.android.criminalintent.ui.crimedetail;


import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bignerdranch.android.criminalintent.PictureUtils;
import com.bignerdranch.android.criminalintent.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrimeSceneViewer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrimeSceneViewer extends DialogFragment {
    private static final String ARG_PHOTOPATH = "param1";

    private String mPhotoFilePath;

    private ImageView mImageView;

    public CrimeSceneViewer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     ** @param param1 Parameter 1.
     * @return A new instance of fragment CrimeSceneViewer.
     */
    public static DialogFragment newInstance(String param1) {
        DialogFragment fragment = new CrimeSceneViewer();
        Bundle args = new Bundle();
        args.putString(ARG_PHOTOPATH, param1);
        fragment.setArguments(args);
        return fragment;
    }


    private Bitmap fetchAndScaleImage(String pathToImage) {
        Bitmap bitmap = PictureUtils.getScaledBitmap(pathToImage, getActivity());

        return bitmap;
    }


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        if (getArguments() != null) {
            mPhotoFilePath = getArguments().getString(ARG_PHOTOPATH);
        }

        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_crimescene_photo, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v).setTitle("Crime Scene Photo").setPositiveButton("Close", null);

        AlertDialog dialog = builder.create();

//        dialog.getWindow().setLayout(200, 600);
        mImageView = v.findViewById(R.id.crimescene_image);

        mImageView.setImageBitmap(fetchAndScaleImage(mPhotoFilePath));

        return dialog;
    }
}
