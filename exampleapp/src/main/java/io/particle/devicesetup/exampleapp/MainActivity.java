package io.particle.devicesetup.exampleapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.particle.android.sdk.devicesetup.ParticleDeviceSetupLibrary;
import io.particle.android.sdk.devicesetup.model.DeviceCustomization;
import io.particle.android.sdk.devicesetup.ui.DeviceSetupState;
import io.particle.android.sdk.utils.ui.Ui;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_SETUP_LAUNCHED_TIME = "io.particle.devicesetup.exampleapp.SETUP_LAUNCHED_TIME";

    public DeviceCustomization deviceCustomization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceCustomization = new DeviceCustomization();

        ParticleDeviceSetupLibrary.initWithSetupOnly(this.getApplicationContext());

        Ui.findView(this, R.id.start_setup_button).setOnClickListener(view -> invokeDeviceSetupAWS());
        Ui.findView(this, R.id.start_setup_custom_intent_button).setOnClickListener(v -> invokeDeviceSetupParticle());

        String setupLaunchTime = this.getIntent().getStringExtra(EXTRA_SETUP_LAUNCHED_TIME);

        if (setupLaunchTime != null) {
            TextView label = Ui.findView(this, R.id.textView);

            label.setText(String.format(getString(R.string.welcome_back), setupLaunchTime));
        }
    }

    public void invokeDeviceSetupAWS() {
        deviceCustomization.setParticleDevice(false);
        deviceCustomization.setNetworkNamePrefix(R.string.aws_ssid_prefix);
        ParticleDeviceSetupLibrary.startDeviceSetup(this, MainActivity.class, deviceCustomization);
    }

    private void invokeDeviceSetupParticle() {
        DeviceSetupState.productInfo.setParticleDevice(true);
        deviceCustomization.setNetworkNamePrefix(R.string.particle_ssid_prefix);
        ParticleDeviceSetupLibrary.startDeviceSetup(this, MainActivity.class, deviceCustomization);
    }

}
