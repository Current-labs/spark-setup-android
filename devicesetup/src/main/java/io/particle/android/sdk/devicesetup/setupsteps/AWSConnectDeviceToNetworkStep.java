package io.particle.android.sdk.devicesetup.setupsteps;

import io.particle.android.sdk.utils.EZ;

public class AWSConnectDeviceToNetworkStep extends ConnectDeviceToNetworkStep {

    private volatile boolean commandSent = false;

    AWSConnectDeviceToNetworkStep(StepConfig stepConfig) {
        super(stepConfig, null, null);
    }

    @Override
    protected void onRunStep() throws SetupStepException {
        log.d("DO ABSOLUTELY NOTHING LOL!");
        EZ.threadSleep(1000);

        commandSent = true;
    }

    @Override
    public boolean isStepFulfilled() {
        return commandSent;
    }

}
