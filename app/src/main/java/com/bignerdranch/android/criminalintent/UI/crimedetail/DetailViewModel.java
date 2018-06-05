package com.bignerdranch.android.criminalintent.UI.crimedetail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.bignerdranch.android.criminalintent.HeaderGenerator;
import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.repository.CrimeRepository;


import java.util.Date;
import java.util.UUID;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends ViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName().concat("::JPC");

    private final CrimeRepository mDataSource;


    private CrimeEntity backingEntity = new CrimeEntity();

    public final ObservableBoolean mIsLoading = new ObservableBoolean();


    public final ObservableField<String> crimeTitle = new ObservableField<>();
    public final ObservableField<String> crimeDate = new ObservableField<>();
    public final ObservableField<UUID> crimeID = new ObservableField<>();
    public final ObservableBoolean crimeSolved = new ObservableBoolean();
    public final ObservableBoolean crimeSerious = new ObservableBoolean();



    private boolean mAddingNewCrime;

    private UUID mUUID;

    DetailViewModel(final CrimeRepository dataSource, final UUID ID) {
        Log.d(TAG, "DetailViewModel: viewModel lifecycle " +System.identityHashCode(this) +": START");

        mDataSource = dataSource;


        mUUID = ID;

        mIsLoading.set(true);


        if (mUUID.equals(HeaderGenerator.getHeaderEntity().getID())) {
            mAddingNewCrime = true;
            initObservableFields(backingEntity);

            mIsLoading.set(false);


        }else{
            mAddingNewCrime = false;

            mDataSource.getForID(mUUID)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<CrimeEntity>() {
                        @Override
                        public void onSubscribe(final Disposable d) {

                            Log.d(TAG, "onSubscribe: attempting to fetch crime ID: " + mUUID);
                        }

                        @Override
                        public void onSuccess(final CrimeEntity crimeEntity) {

                            Log.d(TAG, "onSuccess: sucessfully fetched crime from repo: " + crimeEntity);

                            backingEntity = crimeEntity;
                            initObservableFields(crimeEntity);

                            mIsLoading.set(false);


                            Log.d(TAG, "onSuccess: TESTING adding new crime = false");


                        }

                        @Override
                        public void onError(final Throwable e) {

                            // DEFINITELY an error to be here since ID == HEAD || X
                            // ; where X is any valid crime ID already present in Repo

                            // not clear if its an error to be since initEmptyPager will
                            // result in random ID

                            Log.d(TAG, "onError: failed to fetch crime from repo with ID: " + mUUID, e);
                            Log.d(TAG, "onError: TESTING adding new crime = true");

                            mAddingNewCrime = true;
                            initObservableFields(backingEntity);

                            mIsLoading.set(false);
                        }
                    });


        }






        /**
         * TESTING W/ RXJAVA and regular java streams
         */

//        Log.d(TAG, "DetailViewModel: JPCRXJAVA START");
//
//        Observable<String> strObservable = Observable.just("Hello old boy");
//
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(final Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(final String s) {
//                Log.d(TAG, "JPCRXJAVA onNext: emitted value: " + s);
//            }
//
//            @Override
//            public void onError(final Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//
//
////        strObservable.subscribe(observer);
//
//        strObservable.concatWith(Observable.just("abcd", "abc", "RXJaVaStuFf", "abdefg", "a", "abcdefghijklmnop", "johnnyBoy"))
//                .sorted(Comparator.comparingInt(String::length)).map(s -> s.toUpperCase())
//                .delay(5, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//
//
//        String s = "HelloOldBoyHowAreYou";
//        String[] sarr = new String[]{"hello", "old", "boy"};
//
//        final Function<String, Stream<String>> toLetters = s1 -> IntStream.range(0, s1.length()).mapToObj(value -> s1.substring(value, value + 1));
//
//
//        List<Stream<String>> lss = Arrays.stream(sarr).map(s1 -> toLetters.apply(s)).collect(Collectors.toList());
//
//        for (Stream<String> elem :
//                lss) {
//
//
//            elem.forEach(s1 -> Log.d(TAG, "JAVASTREAM: " + s1));
//        }
//
//        Stream.generate(Math::random).limit(12).sorted(Comparator.reverseOrder()).forEach(aDouble ->
//                Log.d(TAG, "JAVASTREAM randstream: " + aDouble));
//
//
//        Optional<String> ostr = Stream.of("john", "johnny", "jumping", "jack").filter(Predicate.isEqual("atmosphere"))
//                .findAny();
//
//        ostr.ifPresent(s1 -> Log.d(TAG, "JAVASTREAM optional: " + s1));
//
//
//        Log.d(TAG, "JAVASTREAM optional2: " + ostr.orElseGet(() -> System.getProperty("user.dir")));
//
//
//        Function<Double, Optional<Double>> inverse = aDouble -> aDouble == 0 ? Optional.empty() : Optional.of(1 / aDouble);
//
//        Function<Double, Optional<Double>> sqrroot = aDouble -> aDouble < 0 ? Optional.empty() : Optional.of(Math.sqrt(aDouble));
//
//        double d = 100.00;
//
//        Function<Double, Optional<Double>> inv_sqrt = aDouble -> inverse.apply(aDouble).flatMap(sqrroot::apply);
//
//
//        Log.d(TAG, "JAVASTREAM invroot: " + inv_sqrt.apply(d));
//
//        for (Double dval :
//                Arrays.asList(1.0, 100.0, 4.0, -1.0, 0.0, -100.0)) {
//            Log.d(TAG, "JAVASTREAM invroot for: : " + dval);
//            Log.d(TAG, "JAVASTREAM invroot result: " + (inv_sqrt.apply(dval).toString()));
//
//        }
//
//
//        Stream<Locale> localeStream = Stream.of(Locale.getAvailableLocales());
//
//
//        Map<String, String> localeMap = localeStream.collect(Collectors.toMap(Locale::getDisplayCountry, Locale::getDisplayLanguage,
//                (s1, s2) -> s1));
//
//
//        localeMap.entrySet().stream().forEach(entry ->
//                Log.d(TAG, "JAVASTREAM LOCALE ITR: " + entry.getKey() + " : " + entry.getValue()));
//
//
//        Observable.range(0, 25)
//                .doAfterNext(integer -> logThreadSignature("JPC RXJAVA THREAD---"))
//                .flatMap(integer -> Observable.just(integer)
//                        .doOnNext(integer1 -> logThreadSignature("JPC RXJAVA THREAD---"))
//                        .subscribeOn(Schedulers.computation())
//                        .map(ObsInt -> ObsInt << 4))
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(final Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(final Integer integer) {
//
//                    }
//
//                    @Override
//                    public void onError(final Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//


//        Observable.range(0,100)
//                .observeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.computation())
//                .doOnNext(arg -> Log.d(TAG, "doOnNext: RXJAVA2: "+getThreadName()))
//                .map(integer -> Math.sqrt(integer*1.0))
//                .map(aDouble -> Math.pow(aDouble,2))
////                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Double>() {
//                    @Override
//                    public void onSubscribe(final Disposable d) {
//                        Log.d(TAG, "onSubscribe: RXJAVA2 - " + getThreadName());
//                    }
//
//                    @Override
//                    public void onNext(final Double aDouble) {
//
//                        Log.d(TAG, "onNext: JPC RXJAVA2 val= "+aDouble +"; thread="+ getThreadName());
//
//                    }
//
//                    @Override
//                    public void onError(final Throwable e) {
//
//                        Log.d(TAG, "onError: RXJAVA2 - " + getThreadName());
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                        Log.d(TAG, "onComplete: RXJAVA2 - "+ getThreadName());
//                    }
//                });


//        final Observable<Integer> demoObs = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "subscribe<INSIDE>: RXJAVA3 " + getThreadName());
//                SystemClock.sleep(100); // simulate delay
//                emitter.onNext(5);
//                emitter.onComplete();
//            }
//        });
//
//        demoObs.subscribeOn(Schedulers.io())
//                .map(integer -> integer * integer * integer)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(final Disposable d) {
//                        Log.d(TAG, "onSubscribe: RXJAVA3" + getThreadName());
//                    }
//
//                    @Override
//                    public void onNext(final Integer integer) {
//
//                        Log.d(TAG, "onNext: RXJAVA3 next value: " + integer + ", on thread: " + getThreadName());
//                    }
//
//                    @Override
//                    public void onError(final Throwable e) {
//
//                        Log.d(TAG, "onError: RXJAVA3 " + getThreadName(), e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                        Log.d(TAG, "onComplete: RXJAVA3 " + getThreadName());
//                    }
//                });


        /**
         * TESTING W/ RXJAVA and regular java streams; delete all testing code when finished
         *
         */


    }

    private void initObservableFields(CrimeEntity entity) {


        crimeDate.set(Crime.crimeDateToString(entity.getDate()));
        crimeTitle.set(entity.getTitle());
        crimeID.set(entity.getID());
        crimeSerious.set(entity.isSeriousCrime());
        crimeSolved.set(entity.isSolved());
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    @Override
    protected void onCleared() {
        super.onCleared();

        Log.d(TAG, "onCleared: viewModel lifecycle "+System.identityHashCode(this) +" END ");

    }




    public void setTitle(String title) {


        backingEntity.setTitle(title);

    }

    public String getTitle() {
        return backingEntity.getTitle();
    }


    public Date getDate() {
//        return Crime.crimeDateFromString(mCrime.getDate());
        return backingEntity.getDate();
    }

    public String getDateString() {
        return Crime.crimeDateToString(backingEntity.getDate());
    }

    public void setDate(Date date) {
        backingEntity.setDate(date);
        crimeDate.set(Crime.crimeDateToString(date));

    }

    public void setSeriousFlag(boolean isSerious) {

        backingEntity.setSeriousCrime(isSerious);
    }

    public boolean getSeriousFlag() {
        return backingEntity.isSeriousCrime();
    }

    public void setSolvedFlag(boolean isSolved) {
        backingEntity.setSolved(isSolved);

    }

    public boolean getSolvedFlag() {
        return backingEntity.isSolved();
    }


//    public void injectNextID(UUID uuid) {
//        mUUID = uuid;
//
//        // if we are injecting the next ID to be paged then can't possibly be adding new crime
//        mAddingNewCrime = false;
//
//
//        mDataSource.getForID(mUUID)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<CrimeEntity>() {
//                    @Override
//                    public void onSubscribe(final Disposable d) {
//
//                        Log.d(TAG, "onSubscribe: attempting to fetch crime ID: " + mUUID);
//                    }
//
//                    @Override
//                    public void onSuccess(final CrimeEntity crimeEntity) {
//
//                        Log.d(TAG, "onSuccess: sucessfully fetched crime from repo: " + crimeEntity);
////                        crime_to_ObservableFields(crimeEntity);
//
//
//                        backingEntity = crimeEntity;
//                        initObservableFields(crimeEntity);
//
//
//                        mIsLoading.set(false);
//
////                        mAddingNewCrime = false;
//                    }
//
//                    @Override
//                    public void onError(final Throwable e) {
//
//                        // FIXME this is probably a true error if we end up here
//                        // b/c we either received the HEADER ID or a valid entity's ID
//                        // and if header then we don't even attempt to query repo
//
//
//                        Log.d(TAG, "onError: failed to fetch crime from repo with ID: " + mUUID, e);
////                        mAddingNewCrime = true;
//                    }
//                });
//    }

    /**
     * this methods act on the state of the viewModel so the crime
     * to be updated is the crime representing by the CrimeFields
     * within this viewmodel
     */
    public void updateCrime() {
        Log.d(TAG, "updateCrime: updating crime in repo\n" + backingEntity.toString());

        mDataSource.update(backingEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: successful update crime in repo on thread: "
                                + Thread.currentThread().getName());


                    }

                    @Override
                    public void onError(final Throwable e) {

                        Log.d(TAG, "onError: failed to update crime in Repo", e);

                    }
                });
    }


    public void addCrime() {

        Log.d(TAG, "addCrime: attempting insert new crime in repo\n" + backingEntity.toString());


        mDataSource.insert(backingEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(final Disposable d) {


                        Log.d(TAG, "onSubscribe: add crime ");
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: successful added crime to Repo on thread: "
                                + Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.d(TAG, "onError:  failed to add crime to Repo", e);
                        //FIXME if we failed to insert then we can update here
                        // a-la to Upserting

                        Log.d(TAG, "onError: updating via upsert");
                        updateCrime();

                    }
                });

    }

    public boolean isAddingNewCrime() {
        return mAddingNewCrime;
    }


    public static class Factory implements ViewModelProvider.Factory {

        private final UUID mID;
        private final CrimeRepository mDataSource;

        public Factory(final UUID ID, final CrimeRepository dataSource) {
            this.mID = ID;
            mDataSource = dataSource;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(DetailViewModel.class)) {
                return (T) new DetailViewModel(mDataSource, mID);

            }

            throw new IllegalArgumentException("Unknown ViewModel class");

        }

    }


}
