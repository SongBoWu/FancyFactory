package com.randywu.fancyfactory;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.randywu.fancyfactory.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends PlaceholderFragment
        implements AbsListView.OnItemClickListener {

    private static final String TAG = ItemFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private int mLastFirstVisibleItem = 0;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        View addButton = view.findViewById(R.id.add_button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setMaterialLayout(addButton);
        } else {
            addButton.setBackgroundResource(R.drawable.oval);
        }

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(mOnScrollListener);

        return view;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setMaterialLayout(View addButton) {
        addButton.setOutlineProvider(new ViewOutlineProvider() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getOutline(View view, Outline outline) {
                int diameter = getResources().getDimensionPixelSize(R.dimen.button_diameter);
                outline.setOval(0, 0, diameter, diameter);
            }
        });
        addButton.setClipToOutline(true);

        addButton.setElevation(getResources().getDimension(R.dimen.elevation_low));

        // Dynamic add state list animator
        Animator press_animator = ObjectAnimator.ofFloat(addButton, "translationZ", getResources().getDimension(R.dimen.elevation_low), getResources().getDimension(R.dimen.elevation_high));
        press_animator.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));

        Animator normal_animator = ObjectAnimator.ofFloat(addButton, "translationZ", getResources().getDimension(R.dimen.elevation_high), getResources().getDimension(R.dimen.elevation_low));
        normal_animator.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));

        StateListAnimator animatorList = new StateListAnimator();
        animatorList.addState(new int[]{android.R.attr.state_pressed}, press_animator);
        animatorList.addState(new int[]{}, normal_animator);
        addButton.setStateListAnimator(animatorList);

        // Dnyamic setBackgroundResource
        addButton.setBackgroundResource(R.drawable.ripple_oval);
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach");

        super.onAttach(activity);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
        private int mLastFirstVisibleItem = 0;
        private boolean isHiding = false;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE) {
                Log.d(TAG, "SCROLL_STATE_IDLE");
            } else if (scrollState == SCROLL_STATE_FLING) {
                Log.d(TAG, "SCROLL_STATE_FLING");
            } else if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                Log.d(TAG, "SCROLL_STATE_TOUCH_SCROLL");
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //Log.d(TAG, "firstVisibleItem=" + firstVisibleItem + ", visibleItemCount=" + visibleItemCount + ", totalItemCount=" + totalItemCount);

            if ((firstVisibleItem - mLastFirstVisibleItem) > 0) {
                if (mActionBar.isShowing()) {
                    mActionBar.hide();

                }
            } else if ((firstVisibleItem - mLastFirstVisibleItem) < 0) {
                if (!mActionBar.isShowing()) {
                    mActionBar.show();
                }
            }
            mLastFirstVisibleItem = firstVisibleItem;
        }
    };

}
