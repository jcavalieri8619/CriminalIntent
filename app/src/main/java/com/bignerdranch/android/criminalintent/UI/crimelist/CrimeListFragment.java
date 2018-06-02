package com.bignerdranch.android.criminalintent.UI.crimelist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.criminalintent.Injection;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.UI.ViewModelFactory;
import com.bignerdranch.android.criminalintent.UI.cimedetail.CrimePagerActivity;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link CrimeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {link CrimeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrimeListFragment extends Fragment {

    private static final String TAG = CrimeListFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SUBTITLE_VISIBLE = "SUBTITLE_VISIBLE";

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private boolean mIsSubtitleVisible=false;


    private String mParam1;

    private String mParam2;

    private OnFragmentInteractionListener mListener;


    /**
     * BELOW ARE MY MEMBERS
     */



    private RecyclerView mRecyclerView;

    private CrimeAdapter mCrimeAdapter;

    private ViewModelFactory mViewModelFactory;
    private ListViewModel mListViewModel;

    public CrimeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrimeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrimeListFragment newInstance(String param1, String param2) {
        Log.d(TAG, "newInstance: ");

        CrimeListFragment fragment = new CrimeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        Log.d(TAG, "onCreateOptionsMenu: ");

        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);

        if (mIsSubtitleVisible) {

            subtitleItem.setTitle(R.string.hide_subtitle);

        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected: ");

        switch (item.getItemId()) {
            case R.id.new_crime:

                Log.d(TAG, "onOptionsItemSelected: creating CrimePagerActivity");


                Intent intent = CrimePagerActivity.newIntent(getActivity(), null);
                startActivity(intent);
                return true;

            case R.id.show_subtitle:
                mIsSubtitleVisible = !mIsSubtitleVisible;
                getActivity().invalidateOptionsMenu();

                updateSubtitle();

                return true;
            default:

                return super.onOptionsItemSelected(item);

        }




    }



    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to { Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mIsSubtitleVisible = getArguments().getBoolean(ARG_SUBTITLE_VISIBLE);

        }

        if (savedInstanceState != null) {
            mIsSubtitleVisible = savedInstanceState.getBoolean(ARG_SUBTITLE_VISIBLE);

        }

        setHasOptionsMenu(true);
        mCrimeAdapter = new CrimeAdapter();



        mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mListViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(ListViewModel.class);

        mListViewModel.getAllCrimes().observe(this, crimes -> {
            mCrimeAdapter.setItems(crimes);

            updateUI();

        });

    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>This corresponds to {link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_SUBTITLE_VISIBLE, mIsSubtitleVisible);

    }

    private void updateUI() {


        updateSubtitle();



    }

    private void updateSubtitle() {
//        int size = mListViewModel.currCrimesList.size();

        int size = mCrimeAdapter.getItemCount();


        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plurals, size, size);

        if (!mIsSubtitleVisible) {
            subtitle = null;
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mRecyclerView = v.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setAdapter(mCrimeAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(getActivity(), "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();
                mListViewModel.deleteCrime(((BaseCrimeHolder) viewHolder).getCrimeEntity());
                mCrimeAdapter.notifyItemRemoved(position);
            }
        };


        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        touchHelper.attachToRecyclerView(mRecyclerView);


        updateUI();
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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



    private class CrimeAdapter extends RecyclerView.Adapter<BaseCrimeHolder> {

        private List<CrimeEntity> mItems = new ArrayList<>();





        CrimeAdapter() {

        }

        /**
         * Return the view type of the item at <code>position</code> for the purposes
         * of view recycling.
         * <p>
         * <p>The default implementation of this method returns 0, making the assumption of
         * a single view type for the adapter. Unlike ListView adapters, types need not
         * be contiguous. Consider using id resources to uniquely identify item view types.
         *
         * @param position position to query
         * @return integer value identifying the type of the view needed to represent the item at
         * <code>position</code>. Type codes need not be contiguous.
         */
        @Override
        public int getItemViewType(final int position) {

            return mItems.get(position).isSeriousCrime() ? 1 : 0;
        }



        /**
         * Called when RecyclerView needs a new ViewHolder of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
         * The new ViewHolder will be used to display items of the adapter using
         * onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * getItemViewType(int)
         * onBindViewHolder(ViewHolder, int)
         */
        @NonNull
        @Override
        public BaseCrimeHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
            final int PETTY_CRIME = 0;
            final int SERIOUS_CRIME = 1;

            BaseCrimeHolder VH ;
            switch (viewType) {
                case PETTY_CRIME:
                    VH=new CrimeHolder(getLayoutInflater(), parent);
                    break;
                case SERIOUS_CRIME:
                    VH= new SeriousCrimeHolder(getLayoutInflater(), parent);
                    break;

                    default:
                        VH = null;


            }
            return VH;
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the  ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         * Note that unlike {@link ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use  ViewHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         * <p>
         * Override #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
         * handle efficient partial bind.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(@NonNull final BaseCrimeHolder holder, final int position) {

            holder.bind(mItems.get(position));


        }



        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return mItems.size();
        }


        public void setItems(List<CrimeEntity> items) {
            this.mItems = items;
            notifyDataSetChanged();

        }


    }


    private abstract class BaseCrimeHolder extends RecyclerView.ViewHolder{




        public BaseCrimeHolder(final View itemView) {
            super(itemView);



        }

        abstract CrimeEntity getCrimeEntity();

        abstract void bind(CrimeEntity crime);
    }

    private class CrimeHolder extends BaseCrimeHolder implements View.OnClickListener{

        TextView mCrimeTitle;
        TextView mCrimeDate;

        ImageView mCrimeSolved;

        CrimeEntity mCrime;



        void bind(CrimeEntity crime) {
            mCrime = crime;
            mCrimeDate.setText(crime.getFormattedDate());

            mCrimeTitle.setText(crime.getTitle());
            mCrimeSolved.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);

        }

        CrimeHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            mCrimeDate = super.itemView.findViewById(R.id.crime_date);
            mCrimeTitle = super.itemView.findViewById(R.id.crime_title);
            mCrimeSolved = super.itemView.findViewById(R.id.crime_solved);

            super.itemView.setOnClickListener(this);


        }

        @Override
        CrimeEntity getCrimeEntity() {
            return mCrime;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(final View v) {
            // parameter View v is same view stored in this viewholder
            // so v == super.itemView


            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getID());


            startActivity(intent);



        }
    }


    private class SeriousCrimeHolder extends BaseCrimeHolder implements View.OnClickListener{

        TextView mCrimeTitle;
        TextView mCrimeDate;

        ImageView mCrimeSolved;

        CrimeEntity mCrime;



        void bind(CrimeEntity crime) {
            mCrime = crime;
            mCrimeDate.setText(crime.getFormattedDate());
            mCrimeTitle.setText(crime.getTitle());

            mCrimeSolved.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);


        }
        @Override
        CrimeEntity getCrimeEntity() {
            return mCrime;
        }

        SeriousCrimeHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_serious_crime, parent, false));

            mCrimeDate = super.itemView.findViewById(R.id.crime_date);
            mCrimeTitle = super.itemView.findViewById(R.id.crime_title);

            mCrimeSolved = super.itemView.findViewById(R.id.crime_solved);

            super.itemView.setOnClickListener(this);


        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(final View v) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getID());


            startActivity(intent);
        }
    }
}
