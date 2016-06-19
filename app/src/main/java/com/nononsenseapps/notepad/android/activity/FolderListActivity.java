/*
 * Copyright (c) 2015 Jonas Kalderstam.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nononsenseapps.notepad.android.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nononsenseapps.notepad.R;
import com.nononsenseapps.notepad.android.adapter.FragmentAdapter;
import com.nononsenseapps.notepad.android.fragment.FolderListFragment;
import com.nononsenseapps.notepad.android.provider.ProviderContract;

/**
 * This activity displays the content of a folder-item (an item which contains sub-items).
 * It not the root and thus will have an "up-arrow" to navigate back.
 */
public class FolderListActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_folder);

        // Set support toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        // Set up-navigation
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
            //ab.setDisplayShowTitleEnabled(false);
        }

        // Setup tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        if (navigationDrawerFragment != null) {
            navigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout),
                    mToolbar);
        }*/

        if (getIntent().hasExtra(ProviderContract.COLUMN_TITLE)) {
            setTitle(getIntent().getStringExtra(ProviderContract.COLUMN_TITLE));
        }
    }

    protected void setupViewPager(ViewPager viewPager) {
        // Load fragments
        // TODO savedInstanceState?
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(FolderListFragment.newInstance(getIntent()),
                "Sub items");

        // TODO Details fragment
        adapter.addFragment(FolderListFragment.newInstance(getIntent()),
                 "Details");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}