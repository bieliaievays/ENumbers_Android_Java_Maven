package com.ashomok.eNumbers.activities.categories;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ashomok.eNumbers.R;

/**
 * Created by iuliia on 8/8/16.
 */

//Activity A
public class SubcategoriesListActivity extends AppCompatActivity implements SubcategoriesListFragment.OnItemSelectedListener {

    private static final String TAG = SubcategoriesListActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.subcategories_list_activity_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Check whether the activity is using the layout version with
        // the list_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.list_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            SubcategoriesListFragment firstFragment = new SubcategoriesListFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            updateActivityLabel(((Row) getIntent().getExtras().getSerializable(Row.TAG)));

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction().add(R.id.list_container, firstFragment)
                    .commit();
        } else {
            Log.e(TAG, "R.id.list_container not found in layout file.");
        }
        if (findViewById(R.id.details_container) != null) {
            SubcategoryFragment secondFragment = new SubcategoryFragment();
            getFragmentManager().beginTransaction().add(R.id.details_container, secondFragment)
                    .commit();
        } else {
            Log.d(TAG, "handset device. R.id.details_container not found in layout file");
        }
    }

    /**
     * This is a callback that the list fragment (Fragment A)
     * calls when a list item is selected
     */
    @Override
    public void onItemSelected(Row row) {
        SubcategoryFragment subcategoryFragment = (SubcategoryFragment) getFragmentManager()
                .findFragmentById(R.id.details_container);
        if (subcategoryFragment == null) {

            // SubcategoryFragment (Fragment B) is not in the layout (handset layout),
            // replace the fragment
            SubcategoryFragment newFragment = new SubcategoryFragment();
            Bundle args = new Bundle();
            args.putSerializable(Row.TAG, row);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            //replace whatever is in the list_container view with this fragment
            //add transaction to the back stack so the user can navigate back

            transaction.replace(R.id.list_container, newFragment);
            transaction.addToBackStack(null)
                    .commit();

        } else {
            // DisplayFragment (Fragment B) is in the layout (tablet layout),
            // so tell the fragment to update
            subcategoryFragment.updateContent(row);
        }

        updateActivityLabel(row);
    }

    private void updateActivityLabel(Row row) {
        try {
            String label = getResources().getString(row.getTitleResourceID());
            setTitle(label);
        } catch (Exception e) {
            Log.w(TAG, "Can't set label for activity's action bar");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
