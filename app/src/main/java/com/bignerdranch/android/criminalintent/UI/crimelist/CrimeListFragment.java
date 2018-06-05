package com.bignerdranch.android.criminalintent.UI.crimelist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.bignerdranch.android.criminalintent.HeaderGenerator;
import com.bignerdranch.android.criminalintent.Injection;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.UI.ViewModelFactory;
import com.bignerdranch.android.criminalintent.UI.crimedetail.CrimePagerActivity;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.repository.CrimeRepository;

import java.util.ArrayList;
import java.util.List;


public class CrimeListFragment extends Fragment {

    private static final String TAG = CrimeListFragment.class.getSimpleName().concat("::JPC");


    private OnFragmentInteractionListener mListener;


    private RecyclerView mRecyclerView;

    private CrimeAdapter mCrimeAdapter;

    private ViewModelFactory mViewModelFactory;
    private ListViewModel mListViewModel;
    private ItemTouchHelper.SimpleCallback mSimpleItemTouchCallback;

    public CrimeListFragment() {
        // Required empty public constructor
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: lifecycle");

    }

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
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: lifecycle");

    }

    /**
     * Called when the Fragment is no longer started.  This is generally
     * tied to {link Activity#onStop() Activity.onStop} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: lifecycle");

    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: lifecycle");

    }

    public static CrimeListFragment newInstance() {
        Log.d(TAG, "NewInstance: ");

        CrimeListFragment fragment = new CrimeListFragment();
        Bundle args = new Bundle();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: lifecycle");


        setHasOptionsMenu(true);


        mCrimeAdapter = new CrimeAdapter();


        mViewModelFactory = Injection.provideViewModelFactory(getActivity());
        mListViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(ListViewModel.class);

        subscribeToModel();

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: lifecycle");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        setupListViews(v);
        setupRecyclerView();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: lifecycle");

        updateUI();

    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: lifecycle");

        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.fragment_crime_list, menu);

        setupMenuViews(menu);

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {


        switch (item.getItemId()) {
            case R.id.new_crime:

                Log.d(TAG, "onOptionsItemSelected: creating CrimePagerActivity");


                Intent intent = CrimePagerActivity.newIntentForNewCrime(getActivity());
                startActivity(intent);
                return true;

            case R.id.show_subtitle:
                mListViewModel.negateSubtitleVisiblity();

                getActivity().invalidateOptionsMenu();

                updateSubtitle();

                return true;
            default:

                return super.onOptionsItemSelected(item);

        }

    }


    private void subscribeToModel() {
        mListViewModel.getAllCrimes().observe(this, crimes -> {
            if (crimes != null && crimes.size() !=0) {

                Log.d(TAG, "subscribeToModel: received crimes of size: " + crimes.size());
                mCrimeAdapter.setItems(crimes);

                updateUI();

                CrimeRepository dataSource = Injection.provideCrimeRepository(getContext());

                //TODO remove this testing code
//                dataSource.getForID(crimes.get(0).getID())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new SingleObserver<CrimeEntity>() {
//                            @Override
//                            public void onSubscribe(final Disposable d) {
//
//                                Log.d(TAG, "onSubscribe: JPC TESTING");
//
//                            }
//
//                            @Override
//                            public void onSuccess(final CrimeEntity entity) {
//
//                                Log.d(TAG, "onSuccess: JPC TESTING retrieved entity: "+entity);
//
//                            }
//
//                            @Override
//                            public void onError(final Throwable e) {
//
//                                Log.d(TAG, "onError: JPC TESTING", e);
//
//                            }
//                        });


            } else {

                Log.d(TAG, "subscribeToModel: received crimes NULL list: ");

            }

        });
    }


    private void setupMenuViews(final Menu menu) {
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);

        updateMenu_UI(subtitleItem);
    }

    private void updateMenu_UI(final MenuItem subtitleItem) {
        if (mListViewModel.isSubtitleVisible()) {

            subtitleItem.setTitle(R.string.hide_subtitle);

        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }


    private void updateSubtitle() {

        int size = mCrimeAdapter.getItemCount();


        String subtitle = null;

        if (mListViewModel.isSubtitleVisible()) {

            subtitle = getResources().getQuantityString(R.plurals.subtitle_plurals, size, size);
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);


    }

    private void updateUI() {


        updateSubtitle();


    }

    private void setupListViews(final View v) {
        mRecyclerView = v.findViewById(R.id.crime_recycler_view);
    }

    private void setupRecyclerView() {
        mRecyclerView.setAdapter(mCrimeAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setUpCallbacks();


        ItemTouchHelper touchHelper = new ItemTouchHelper(mSimpleItemTouchCallback);

        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void setUpCallbacks() {
        mSimpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "onMove not implemented", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(getActivity(), "Deleted Crime", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();
                mListViewModel.deleteCrime(((BaseCrimeHolder) viewHolder).getCrimeInstance());
                mCrimeAdapter.notifyItemRemoved(position);
            }
        };
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public class CrimeAdapter extends RecyclerView.Adapter<BaseCrimeHolder> {

        private final String TAG_CA = CrimeAdapter.class.getSimpleName().concat("::JPC");
        private List<? extends Crime> mItems = new ArrayList<>();


        CrimeAdapter() {

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
            final int CRIME = 0;
            final int SERIOUS_CRIME = 1;

            BaseCrimeHolder VH;
            switch (viewType) {

                case SERIOUS_CRIME:
                    VH = new SeriousCrimeHolder(getLayoutInflater(), parent);
                    break;

                default:
                case CRIME:
                    VH = new CrimeHolder(getLayoutInflater(), parent);
                    break;


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

        @Override
        public int getItemCount() {
            return mItems.size();
        }


        public void setItems(List<CrimeEntity> items) {
            this.mItems = items;
            notifyDataSetChanged();

        }


    }


    private abstract class BaseCrimeHolder extends RecyclerView.ViewHolder {


        final String TAG_VH = BaseCrimeHolder.class.getSimpleName().concat("::JPC");

        public BaseCrimeHolder(final View itemView) {
            super(itemView);


        }

        abstract Crime getCrimeInstance();

        abstract void bind(Crime crime);
    }

    private class CrimeHolder extends BaseCrimeHolder implements View.OnClickListener {


        TextView mCrimeTitle;
        TextView mCrimeDate;

        ImageView mCrimeSolved;



        Crime mCrime;


        CrimeHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            mCrimeDate = super.itemView.findViewById(R.id.crime_date);
            mCrimeTitle = super.itemView.findViewById(R.id.crime_title);
            mCrimeSolved = super.itemView.findViewById(R.id.crime_solved);

            super.itemView.setOnClickListener(this);


        }

        void bind(Crime crime) {
            if (crime.getID().equals(HeaderGenerator.getHeaderEntity().getID())) {
                super.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
                super.itemView.setVisibility(View.GONE);
                return;
            }

            mCrime = crime;
            mCrimeDate.setText(Crime.crimeDateToString(crime.getDate()));

            mCrimeTitle.setText(crime.getTitle());
            mCrimeSolved.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);

        }

        @Override
        Crime getCrimeInstance() {
            return mCrime;
        }

        @Override
        public void onClick(final View v) {
            // parameter View v is same view stored in this viewholder
            // so v == super.itemView


            Intent intent = CrimePagerActivity.newIntentForUUID(getActivity(), mCrime.getID());
            Log.d(TAG_VH, "onClick: creating PagerActivity for UUID "
                    + mCrime.getID().toString());


            startActivity(intent);


        }


    }


    private class SeriousCrimeHolder extends BaseCrimeHolder implements View.OnClickListener {

        TextView mCrimeTitle;
        TextView mCrimeDate;

        ImageView mCrimeSolved;

        Crime mCrime;


        SeriousCrimeHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_serious_crime, parent, false));

            mCrimeDate = super.itemView.findViewById(R.id.crime_date);
            mCrimeTitle = super.itemView.findViewById(R.id.crime_title);

            mCrimeSolved = super.itemView.findViewById(R.id.crime_solved);

            super.itemView.setOnClickListener(this);


        }

        void bind(Crime crime) {
            mCrime = crime;
            mCrimeDate.setText(Crime.crimeDateToString(crime.getDate()));
            mCrimeTitle.setText(crime.getTitle());

            mCrimeSolved.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);


        }

        @Override
        Crime getCrimeInstance() {
            return mCrime;
        }


        @Override
        public void onClick(final View v) {
            Intent intent = CrimePagerActivity.newIntentForUUID(getActivity(), mCrime.getID());
            Log.d(TAG_VH, "onClick: creating PagerActivity for UUID "
                    + mCrime.getID().toString());

            startActivity(intent);
        }
    }
}
