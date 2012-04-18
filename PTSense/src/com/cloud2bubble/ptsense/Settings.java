package com.cloud2bubble.ptsense;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class Settings extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Populate the activity with the top-level headers.
	 */
	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preference_headers, target);
	}

	public static class StockPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			int res = getActivity().getResources().getIdentifier(
					getArguments().getString("resource"), "xml",
					getActivity().getPackageName());

			addPreferencesFromResource(res);
		}
	}
}
