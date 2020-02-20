package io.particle.android.sdk.devicesetup.setupsteps;


import java.io.IOException;

import io.particle.android.sdk.devicesetup.commands.AWSConfigureApCommand;
import io.particle.android.sdk.devicesetup.commands.AWSScanApCommand;
import io.particle.android.sdk.devicesetup.commands.CommandClient;
import io.particle.android.sdk.devicesetup.commands.ScanApCommand;


public class AWSConfigureAPStep extends SetupStep {

    private final CommandClient commandClient;
    private final SetupStepApReconnector workerThreadApConnector;
    private final AWSScanApCommand.Scan networkToConnectTo;
    private final String networkSecretPlaintext;

    private volatile boolean commandSent = false;

    AWSConfigureAPStep(StepConfig stepConfig, CommandClient commandClient,
                       SetupStepApReconnector workerThreadApConnector,
                       ScanApCommand.Scan networkToConnectTo, String networkSecretPlaintext) {
        super(stepConfig);
        this.commandClient = commandClient;
        this.workerThreadApConnector = workerThreadApConnector;
        this.networkToConnectTo = (AWSScanApCommand.Scan) networkToConnectTo;
        this.networkSecretPlaintext = networkSecretPlaintext;
    }

    protected void onRunStep() throws SetupStepException {
        AWSConfigureApCommand command = new AWSConfigureApCommand(networkToConnectTo.ssid, networkSecretPlaintext);

        try {
            log.d("Ensuring connection to AP");
            workerThreadApConnector.ensureConnectionToSoftAp();

            AWSConfigureApCommand.Response response = commandClient.sendCommand(
                    command, AWSConfigureApCommand.Response.class);
            if (!response.isOk()) {
                throw new SetupStepException("Error response " + response.result +
                        " while configuring device");
            }
            log.d("Configure AP command returned: " + response.result);
            commandSent = true;

        } catch (IOException e) {
            throw new SetupStepException(e);
        }
    }

    public boolean isStepFulfilled() {
        return commandSent;
    }

}
