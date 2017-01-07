package com.queatz.littlepiratesister;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.queatz.littlepiratesister.Game;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.hideStatusBar = true;
        config.useImmersiveMode = true;
		initialize(new Game(), config);
	}

    @Override
    protected void onPause() {
        super.onPause();
    }
}
