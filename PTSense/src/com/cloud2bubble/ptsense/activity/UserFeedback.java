package com.cloud2bubble.ptsense.activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.cloud2bubble.ptsense.PTSense;
import com.cloud2bubble.ptsense.R;
import com.cloud2bubble.ptsense.database.DatabaseHandler;
import com.cloud2bubble.ptsense.database.TripFeedback;
import com.cloud2bubble.ptsense.list.ReviewItem;
import com.cloud2bubble.ptsense.servercommunication.C2BClient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class UserFeedback extends SherlockActivity implements OnClickListener {

	ReviewItem trip;
	Map<String, SeekBar> inputs = new HashMap<String, SeekBar>();
	EditText etFeedback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_feedback);

		ActionBar actionBar = getSupportActionBar();
		View actionCustomView = getLayoutInflater().inflate(
				R.layout.action_mode_bar, null);
		TextView done = (TextView) actionCustomView.findViewById(R.id.tvActionModeCloseButton);
		done.setText(getString(R.string.done).toUpperCase());
		actionBar.setCustomView(actionCustomView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		PTSense app = (PTSense) getApplication();
		app.resetNotificationCount(PTSense.FEEDBACK_NOTIFICATION);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		trip = (ReviewItem) extras.get("review_item");
		
		Log.d("UserFeedback", "Reviewing item for trip with id:" + trip.getId());
		
		if (trip != null) {
			TextView tvService = (TextView) findViewById(R.id.tvService);
			TextView tvDirection = (TextView) findViewById(R.id.tvDirection);
			TextView tvDate = (TextView) findViewById(R.id.tvDate);
			TextView tvCloseButton = (TextView) findViewById(R.id.tvActionModeCloseButton);
			tvService.setText(trip.serviceToString());
			tvDirection.setText(trip.directionToString());
			tvDate.setText(trip.endTimeToString());
			tvCloseButton.setOnClickListener(this);

			TextView question = (TextView) findViewById(R.id.tvQuestion);
			question.setText(getString(R.string.feedback_question).toUpperCase());
			
			TextView sepPersonal = (TextView) findViewById(R.id.sepPersonal);
			sepPersonal
					.setText(getString(R.string.feedback_separator_personal).toUpperCase());
			TextView sepEnvironment = (TextView) findViewById(R.id.sepEnvironment);
			sepEnvironment
					.setText(getString(R.string.feedback_separator_environment).toUpperCase());
			TextView sepService = (TextView) findViewById(R.id.sepService);
			sepService.setText(getString(R.string.feedback_separator_service).toUpperCase());
			TextView sepFeedback = (TextView) findViewById(R.id.sepFeedback);
			sepFeedback
					.setText(getString(R.string.feedback_separator_feedback).toUpperCase());

			etFeedback = (EditText) findViewById(R.id.eTFeedback);

			LinearLayout sbHappy = (LinearLayout) findViewById(R.id.sbHappy);
			TextView happyTitle = (TextView) sbHappy.findViewById(R.id.tvTitle);
			final TextView happyProgress = (TextView) sbHappy
					.findViewById(R.id.tvProgress);
			SeekBar happySB = (SeekBar) sbHappy.findViewById(R.id.sbInput);
			happyTitle.setText(getString(R.string.feedback_title_happy));
			happyProgress.setText(progressToString(happySB.getProgress()));
			inputs.put(getString(R.string.feedback_key_happy), happySB);

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

			LinearLayout sbRelaxed = (LinearLayout) findViewById(R.id.sbRelaxed);
			TextView relaxedTitle = (TextView) sbRelaxed
					.findViewById(R.id.tvTitle);
			final TextView relaxedProgress = (TextView) sbRelaxed
					.findViewById(R.id.tvProgress);
			SeekBar relaxedSB = (SeekBar) sbRelaxed.findViewById(R.id.sbInput);
			relaxedTitle.setText(getString(R.string.feedback_title_relaxed));
			relaxedProgress.setText(progressToString(relaxedSB.getProgress()));
			inputs.put(getString(R.string.feedback_key_relaxed), relaxedSB);

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

			LinearLayout sbNoisy = (LinearLayout) findViewById(R.id.sbNoisy);
			TextView noisyTitle = (TextView) sbNoisy.findViewById(R.id.tvTitle);
			final TextView noisyProgress = (TextView) sbNoisy
					.findViewById(R.id.tvProgress);
			SeekBar noisySB = (SeekBar) sbNoisy.findViewById(R.id.sbInput);
			noisyTitle.setText(getString(R.string.feedback_title_noisy));
			noisyProgress.setText(progressToString(noisySB.getProgress()));
			inputs.put(getString(R.string.feedback_key_noisy), noisySB);

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

			LinearLayout sbCrowded = (LinearLayout) findViewById(R.id.sbCrowded);
			TextView crowdedTitle = (TextView) sbCrowded
					.findViewById(R.id.tvTitle);
			final TextView crowdedProgress = (TextView) sbCrowded
					.findViewById(R.id.tvProgress);
			SeekBar crowdedSB = (SeekBar) sbCrowded.findViewById(R.id.sbInput);
			crowdedTitle.setText(getString(R.string.feedback_title_crowded));
			crowdedProgress.setText(progressToString(crowdedSB.getProgress()));
			inputs.put(getString(R.string.feedback_key_crowded), crowdedSB);

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

			LinearLayout sbSmoothness = (LinearLayout) findViewById(R.id.sbSmoothness);
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
			inputs.put(getString(R.string.feedback_key_smoothness),
					smoothnessSB);

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

			LinearLayout sbAmbience = (LinearLayout) findViewById(R.id.sbAmbience);
			TextView ambienceTitle = (TextView) sbAmbience
					.findViewById(R.id.tvTitle);
			final TextView ambienceProgress = (TextView) sbAmbience
					.findViewById(R.id.tvProgress);
			SeekBar ambienceSB = (SeekBar) sbAmbience
					.findViewById(R.id.sbInput);
			ambienceTitle.setText(getString(R.string.feedback_title_ambience));
			ambienceProgress.setText(progressToString(relaxedSB.getProgress()));
			inputs.put(getString(R.string.feedback_key_ambience), ambienceSB);

			ambienceSB
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							ambienceProgress
									.setText(progressToString(progress));
						}

						public void onStartTrackingTouch(SeekBar seekBar) {
						}

						public void onStopTrackingTouch(SeekBar seekBar) {
						}
					});

			LinearLayout sbFast = (LinearLayout) findViewById(R.id.sbFast);
			TextView fastTitle = (TextView) sbFast.findViewById(R.id.tvTitle);
			final TextView fastProgress = (TextView) sbFast
					.findViewById(R.id.tvProgress);
			SeekBar fastSB = (SeekBar) sbFast.findViewById(R.id.sbInput);
			fastTitle.setText(getString(R.string.feedback_title_fast));
			fastProgress.setText(progressToString(fastSB.getProgress()));
			inputs.put(getString(R.string.feedback_key_fast), fastSB);

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

			LinearLayout sbReliable = (LinearLayout) findViewById(R.id.sbReliable);
			TextView reliableTitle = (TextView) sbReliable
					.findViewById(R.id.tvTitle);
			final TextView reliableProgress = (TextView) sbReliable
					.findViewById(R.id.tvProgress);
			SeekBar reliableSB = (SeekBar) sbReliable
					.findViewById(R.id.sbInput);
			reliableTitle.setText(getString(R.string.feedback_title_reliable));
			reliableProgress
					.setText(progressToString(reliableSB.getProgress()));
			inputs.put(getString(R.string.feedback_key_reliable), reliableSB);

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
			TripFeedback feedback = processUserInput();
			if (insertFeedbackIntoDatabase(feedback)) {
				Toast.makeText(UserFeedback.this, R.string.feedback_saved,
						Toast.LENGTH_SHORT).show();
			}

			setResult(RESULT_OK);
			finish();
			break;
		}
	}

	private TripFeedback processUserInput() {
		TripFeedback feedback = new TripFeedback(trip);
		Iterator<Entry<String, SeekBar>> it = inputs.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, SeekBar> input = (Map.Entry<String, SeekBar>) it
					.next();
			SeekBar seekBar = input.getValue();
			feedback.addInput(input.getKey(), seekBar.getProgress() / 10.0);
		}
		feedback.addComment(etFeedback.getText().toString());
		feedback.getTrip().setReviewed();
		return feedback;
	}

	private boolean insertFeedbackIntoDatabase(TripFeedback feedback) {
		PTSense app = (PTSense) getApplication();
		DatabaseHandler database = app.getDatabase();
		
		ReviewItem oldReview = feedback.getTrip();
		database.updateReviewAsReviewed(oldReview);
		database.addPendingFeedback(feedback);
		Intent serviceIntent = new Intent(this, C2BClient.class);
		this.startService(serviceIntent);
		return true;
	}
}
