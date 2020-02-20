package io.particle.android.sdk.devicesetup.commands;

import io.particle.android.sdk.devicesetup.R;
import io.particle.android.sdk.devicesetup.ui.DeviceSetupState;
import io.particle.android.sdk.utils.SSID;
import io.particle.android.sdk.utils.WifiFacade;

import static io.particle.android.sdk.devicesetup.commands.CommandClient.DEFAULT_TIMEOUT_SECONDS;

public class CommandClientFactory {

    public CommandClient newClient(WifiFacade wifiFacade, SSID softApSSID, String ipAddress, int port) {
        return new CommandClient(ipAddress, port,
                new NetworkBindingSocketFactory(wifiFacade, softApSSID, DEFAULT_TIMEOUT_SECONDS * 1000));
    }

    public CommandClient newAWSClient(WifiFacade wifiFacade, SSID softApSSID, String ipAddress, int port) {
        return new AWSCommandClient(ipAddress, port, null);
    }

    // FIXME: set these defaults in a resource file?
    public CommandClient newClientUsingDefaultsForDevices(WifiFacade wifiFacade, SSID softApSSID) {
        if (DeviceSetupState.productInfo.getCloudProvider() == R.string.cloud_provider_particle) {
            return newClient(wifiFacade, softApSSID, "192.168.0.1", 5609);
        } else {
            return newAWSClient(wifiFacade, softApSSID, "192.168.4.1", 80);
        }
    }

}
