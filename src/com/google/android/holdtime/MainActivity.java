package com.google.android.holdtime;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	class TeamTimer {
		public int deciSeconds = 0;
		public int seconds = 0;
		public int minutes = 0;
	}

	TeamTimer team[];
	int currentTeam = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		team = new TeamTimer[2];
		team[0] = new TeamTimer();
		team[1] = new TeamTimer();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						team[currentTeam].deciSeconds++;
						if (team[currentTeam].deciSeconds == 10) {
							team[currentTeam].deciSeconds = 0;
							team[currentTeam].seconds++;
							if (team[currentTeam].seconds == 60) {
								team[currentTeam].minutes++;
							}
						}
						
						int[] timers = {R.id.team1Timer, R.id.team2Timer};
						
						for(int i=0; i<2; i++) {
							TextView tv = (TextView) findViewById(timers[i]);
							tv.setText(String.valueOf(team[i].minutes) + ":"
									+ String.valueOf(team[i].seconds) + "."
									+ String.valueOf(team[i].deciSeconds));
						}
					}
				});
			}
		}, 0, 100);
	}
	
	public void onToggleClicked(View v) {
		if(((ToggleButton) v).isChecked()) {
			currentTeam = 1;
		}
		else {
			currentTeam = 0;
		}
	}
	
	public void changeTeam(View v) {
		ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);
		
		switch(v.getId()) {
		case R.id.team1button:
			currentTeam = 0;
			tb.setChecked(false);
			break;
		case R.id.team2button:
			currentTeam = 1;
			tb.setChecked(true);
			break;
		}
	}
}
