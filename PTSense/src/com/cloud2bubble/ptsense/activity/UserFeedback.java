package com.cloud2bubble.ptsense.activity;

import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.UserFeedBack;
import com.cloud2bubble.ptsense.list.ReviewItem;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class UserFeedback extends Activity implements OnClickListener {

	LinearLayout sbHappy, sbRelaxed, sbNoisy, sbCrowded, sbSmoothness, sbLight,
			sbTemperature, sbFast, sbReliable;
	UserFeedBack feedback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_feedback);

		ActionBar actionBar = getActionBar();

		View actionCustomView = getLayoutInflater().inflate(
				R.layout.action_mode_bar, null);

		actionBar.setCustomView(actionCustomView);

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		ReviewItem trip = (ReviewItem) extras.get("review_item");
		if (trip != null) {
			TextView tvService = (TextView) findViewById(R.id.tvService);
			TextView tvDirection = (TextView) findViewById(R.id.tvDirection);
			TextView tvDate = (TextView) findViewById(R.id.tvDate);
			TextView tvCloseButton = (TextView) findViewById(R.id.tvActionModeCloseButton);
			tvService.setText(trip.serviceToString());
			tvDirection.setText(trip.directionToString());
			tvDate.setText(trip.dateToString());
			tvCloseButton.setOnClickListener(this);

			TextView sepPersonal = (TextView) findViewById(R.id.sepPersonal);
			sepPersonal
					.setText(getString(R.string.feedback_separator_personal));
			TextView sepEnvironment = (TextView) findViewById(R.id.sepEnvironment);
			sepEnvironment
					.setText(getString(R.string.feedback_separator_environment));
			TextView sepService = (TextView) findViewById(R.id.sepService);
			sepService.setText(getString(R.string.feedback_separator_service));
			TextView sepFeedback = (TextView) findViewById(R.id.sepFeedback);
			sepFeedback
					.setText(getString(R.string.feedback_separator_feedback));

			final EditText etFeedback = (EditText) findViewById(R.id.eTFeedback);

			sbHappy = (LinearLayout) findViewById(R.id.sbHappy);
			TextView happyTitle = (TextView) sbHappy.findViewById(R.id.tvTitle);
			final TextView happyProgress = (TextView) sbHappy
					.findViewById(R.id.tvProgress);
			SeekBar happySB = (SeekBar) sbHappy.findViewById(R.id.sbInput);
			happyTitle.setText(getString(R.string.feedback_title_happy));
			happyProgress.setText(progressToString(happySB.getProgress()));

			happySB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					happyProgress.setText(progressToString(progress));
				}

				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});

			sbRelaxed = (LinearLayout) findViewById(R.id.sbRelaxed);
			TextView relaxedTitle = (TextView) sbRelaxed
					.findViewById(R.id.tvTitle);
			final TextView relaxedProgress = (TextView) sbRelaxed
					.findViewById(R.id.tvProgress);
			SeekBar relaxedSB = (SeekBar) sbRelaxed.findViewById(R.id.sbInput);
			relaxedTitle.setText(getString(R.string.feedback_title_relaxed));
			relaxedProgress.setText(progressToString(relaxedSB.getProgress()));

			relaxedSB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					relaxedProgress.setText(progressToString(progress));
				}

				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});

			sbNoisy = (LinearLayout) findViewById(R.id.sbNoisy);
			TextView noisyTitle = (TextView) sbNoisy.findViewById(R.id.tvTitle);
			final TextView noisyProgress = (TextView) sbNoisy
					.findViewById(R.id.tvProgress);
			SeekBar noisySB = (SeekBar) sbNoisy.findViewById(R.id.sbInput);
			noisyTitle.setText(getString(R.string.feedback_title_noisy));
			noisyProgress.setText(progressToString(noisySB.getProgress()));

			noisySB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					noisyProgress.setText(progressToString(progress));
				}

				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});

			sbCrowded = (LinearLayout) findViewById(R.id.sbCrowded);
			TextView crowdedTitle = (TextView) sbCrowded
					.findViewById(R.id.tvTitle);
			final TextView crowdedProgress = (TextView) sbCrowded
					.findViewById(R.id.tvProgress);
			SeekBar crowdedSB = (SeekBar) sbCrowded.findViewById(R.id.sbInput);
			crowdedTitle.setText(getString(R.string.feedback_title_crowded));
			crowdedProgress.setText(progressToString(crowdedSB.getProgress()));

			crowdedSB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					crowdedProgress.setText(progressToString(progress));
				}

				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});

			sbSmoothness = (LinearLayout) findViewById(R.id.sbSmoothness);
			TextView smoothnessTitle = (TextView) sbSmoothness
					.findViewById(R.id.tvTitle);
			final TextView smoothnessProgress = (TextView) sbSmoothness
					.findViewById(R.id.tvProgress);
			SeekBar smoothnessSB = (SeekBar) sbSmoothness
					.findViewById(R.id.sbInput);
			smoothnessTitle
					.setText(getString(R.string.feedback_title_smoothness));
			smoothnessProgress
					.setText(progressToString(relaxedSB.getProgress()));

			smoothnessSB
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							smoothnessProgress
									.setText(progressToString(progress));
						}

						public void onStartTrackingTouch(SeekBar seekBar) {
						}

						public void onStopTrackingTouch(SeekBar seekBar) {
						}
					});

			sbLight = (LinearLayout) findViewById(R.id.sbLight);
			TextView lightTitle = (TextView) sbLight.findViewById(R.id.tvTitle);
			final TextView lightProgress = (TextView) sbLight
					.findViewById(R.id.tvProgress);
			SeekBar lightSB = (SeekBar) sbLight.findViewById(R.id.sbInput);
			lightTitle.setText(getString(R.string.feedback_title_light));
			lightProgress.setText(progressToString(relaxedSB.getProgress()));

			lightSB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					lightProgress.setText(progressToString(progress));
				}

				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});

			sbTemperature = (LinearLayout) findViewById(R.id.sbTemperature);
			TextView temperatureTitle = (TextView) sbTemperature
					.findViewById(R.id.tvTitle);
			final TextView temperatureProgress = (TextView) sbTemperature
					.findViewById(R.id.tvProgress);
			SeekBar temperatureSB = (SeekBar) sbTemperature
					.findViewById(R.id.sbInput);
			temperatureTitle
					.setText(getString(R.string.feedback_title_temperature));
			temperatureProgress.setText(progressToString(relaxedSB
					.getProgress()));

			temperatureSB
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							temperatureProgress
									.setText(progressToString(progress));
						}

						public void onStartTrackingTouch(SeekBar seekBar) {
						}

						public void onStopTrackingTouch(SeekBar seekBar) {
						}
					});

			sbFast = (LinearLayout) findViewById(R.id.sbFast);
			TextView fastTitle = (TextView) sbFast.findViewById(R.id.tvTitle);
			final TextView fastProgress = (TextView) sbFast
					.findViewById(R.id.tvProgress);
			SeekBar fastSB = (SeekBar) sbFast.findViewById(R.id.sbInput);
			fastTitle.setText(getString(R.string.feedback_title_fast));
			fastProgress.setText(progressToString(fastSB.getProgress()));

			fastSB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					fastProgress.setText(progressToString(progress));
				}

				public void onStartTrackingTouch(SeekBar seekBar) {
				}

				public void onStopTrackingTouch(SeekBar seekBar) {
				}
			});

			sbReliable = (LinearLayout) findViewById(R.id.sbReliable);
			TextView reliableTitle = (TextView) sbReliable
					.findViewById(R.id.tvTitle);
			final TextView reliableProgress = (TextView) sbReliable
					.findViewById(R.id.tvProgress);
			SeekBar reliableSB = (SeekBar) sbReliable
					.findViewById(R.id.sbInput);
			reliableTitle.setText(getString(R.string.feedback_title_reliable));
			reliableProgress
					.setText(progressToString(reliableSB.getProgress()));

			reliableSB
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							reliableProgress
									.setText(progressToString(progress));
						}

						public void onStartTrackingTouch(SeekBar seekBar) {
						}

						public void onStopTrackingTouch(SeekBar seekBar) {
						}
					});

			View v = ((ViewGroup) findViewById(android.R.id.content))
					.getChildAt(0);
			v.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.hideSoftInputFromWindow(
							etFeedback.getWindowToken(), 0);
					etFeedback.clearFocus();
					return true;
				}
			});
		}
	}

	private String progressToString(int progress) {
		return String.valueOf(progress / 10.0);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tvActionModeCloseButton:
			Intent data = new Intent();
			setResult(RESULT_OK, data);
			finish();
			break;
		}
	}
}
