package com.randywu.fancyfactory;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.ConcurrentHashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String TAG = PlaceholderFragment.class.getSimpleName();

    private static ConcurrentHashMap<Integer, PlaceholderFragment> mFragmentMap = new ConcurrentHashMap<Integer, PlaceholderFragment>();
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private int mSection = 0;

    protected ActionBar mActionBar;

    protected OnFragmentInteractionListener mListener;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment;
        Log.d(TAG, "newInstance sectionNumber="+sectionNumber);

        if ( !mFragmentMap.containsKey(sectionNumber)) {
            if (sectionNumber == 1) {
                fragment = new ItemFragment();
            } else if (sectionNumber == 2) {
                fragment = new PlusOneFragment();
            } else {
                fragment = new PlaceholderFragment();
            }
            mFragmentMap.put(sectionNumber, fragment);

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
        } else {
            fragment = mFragmentMap.get(sectionNumber);
        }

        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSection = getArguments().getInt(ARG_SECTION_NUMBER);

        Log.d(TAG, "onCreate " + mSection);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Section " + getArguments().getInt(ARG_SECTION_NUMBER) + " !!");
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        mActionBar = ((MainActivity) activity).getSupportActionBar();

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy " + mSection);

        if (mFragmentMap != null) {
            mFragmentMap.remove(mSection);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }
}