package bjorn.vuylsteker.tyzer34.PPG;

import bjorn.vuylsteker.tyzer34.PPG.util.Global;
import bjorn.vuylsteker.tyzer34.PPG.util.NavDrawerItem;
import bjorn.vuylsteker.tyzer34.PPG.util.NavDrawerListAdapter;
import bjorn.vuylsteker.tyzer34.PPG.util.db.DataHandler;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private static ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private static NavDrawerListAdapter adapter;

	public static NavDrawerItem friendsItem;
	public static NavDrawerItem loginItem;
	public static NavDrawerItem collectionItem;
	public static NavDrawerItem wantlistItem;
	public static NavDrawerItem statsItem;
	public static NavDrawerItem forumItem;
	public static NavDrawerItem registerItem;

	public static MainActivity current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loadDatabase();

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Search
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Collection
		collectionItem = new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, "");
		navDrawerItems.add(collectionItem);
		// Wanted List
		wantlistItem = new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "");
		navDrawerItems.add(wantlistItem);
		// Stats
		statsItem = new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1));
		navDrawerItems.add(statsItem);
		// Friends
		friendsItem = new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, String.valueOf(getFriendCount()));
		navDrawerItems.add(friendsItem);
		// Upcoming
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		// Forum
		forumItem = new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1));
		navDrawerItems.add(forumItem);
		// Settings
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));
		// Login
		loginItem = new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(9, -1));
		navDrawerItems.add(loginItem);
		// Register
		registerItem = new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(10, -1));
		navDrawerItems.add(registerItem);


		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				//getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				//getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			Fragment fragment = new HomeFragment();
			FragmentManager manager = getFragmentManager();
			String backStateName = fragment.getClass().getName();
			String fragmentTag = backStateName;
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(R.id.frame_container, fragment, fragmentTag);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(backStateName);
			ft.commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(0, true);
			mDrawerList.setSelection(0);
			setTitle(navMenuTitles[0]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		current = this;
		Global.loadSettings();
	}

	// CREATE DB HANDLER AND LOAD DATA FROM CSV FILE
	private void loadDatabase() {
		new Thread(new Runnable() {
			public void run() {
				Global.setDbHandler(new DataHandler(getBaseContext()));
			}
		}).start();
	}

	/**
	 * Slide menu item click listener
	 */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
								long id) {
			// display view for selected nav drawer item
			displayView(adapter.getItemPos(position));
			setPosition(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
			case R.id.action_settings:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new SearchFragment();
				break;
			case 2:
				fragment = new CollectionFragment();
				break;
			case 3:
				fragment = new WantedlistFragment();
				break;
			case 4:
				fragment = new StatsFragment();
				break;
			case 5:
				fragment = new FriendsFragment();
				break;
			case 6:
				fragment = new UpcomingFragment();
				break;
			case 7:
				fragment = new ForumFragment();
				break;
			case 8:
				fragment = new SettingsFragment();
				break;
			case 9:
				fragment = new LoginFragment();
				break;
			case 10:
				fragment = new RegisterFragment();
				break;
			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();

			for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
				fragmentManager.popBackStack();
			}

			Fragment homeFragment = new HomeFragment();
			String backStateName = homeFragment.getClass().getName();
			String fragmentTag = backStateName;
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.replace(R.id.frame_container, homeFragment, fragmentTag);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(backStateName);
			ft.commit();

			String backStateName2 = fragment.getClass().getName();
			String fragmentTag2 = backStateName;
			FragmentTransaction ft2 = fragmentManager.beginTransaction();
			ft2.replace(R.id.frame_container, fragment, fragmentTag2);
			ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft2.addToBackStack(backStateName2);
			ft2.commit();
			// update selected item and title, then close the drawer
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		//getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	public void hideSoftKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}

	public int getFriendCount() {
		String filename = FriendsFragment.filename;
		int count = 0;
		try {
			FileOutputStream outputStream = openFileOutput(filename, Context.MODE_APPEND);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(openFileInput(filename));
			BufferedReader r = new BufferedReader(inputStreamReader);
			String line;
			while ((line = r.readLine()) != null) {
				if (!line.equals(""))
					count++;
			}
			r.close();
			inputStreamReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public static void update() {
		adapter.notifyDataSetChanged();
	}

	public static void setPosition(int position) {
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
	}

	public static boolean isNetworkOnline() {
		boolean status = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) MainActivity.current.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getNetworkInfo(0);
			if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
				status = true;
			} else {
				netInfo = cm.getNetworkInfo(1);
				if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
					status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return status;

	}

	@Override
	public void onBackPressed() {
		Fragment fragment = getFragmentManager().findFragmentByTag(new HomeFragment().getClass().getName());
		HomeFragment homeFragment = null;
		ForumFragment forumFragment = null;
		if (fragment != null && fragment instanceof HomeFragment) {
			homeFragment = (HomeFragment) fragment;
		} else if (fragment != null && fragment instanceof ForumFragment) {
			forumFragment = (ForumFragment) fragment;
		}
		if (homeFragment != null && homeFragment.isVisible()) {
			// Go back on webview
			if (homeFragment.canGoBack()) {
				homeFragment.goBack();
			} else {
				System.exit(0);
			}
		} else if (forumFragment != null && forumFragment.isVisible()) {
			// Go back on webview
			if (forumFragment.canGoBack()) {
				forumFragment.goBack();
			} else {
				if (getFragmentManager().getBackStackEntryCount() == 1) {
					System.exit(0);
				} else {
					super.onBackPressed();
				}
			}
		} else if (getFragmentManager().getBackStackEntryCount() == 1) {
			System.exit(0);
		} else {
			super.onBackPressed();
		}
	}

	public static void replaceFragment(Fragment fragment) {
		String backStateName = fragment.getClass().getName();
		String fragmentTag = backStateName;

		FragmentManager manager = current.getFragmentManager();
		boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

		if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(R.id.frame_container, fragment, fragmentTag);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(backStateName);
			ft.commit();
		}
	}
}