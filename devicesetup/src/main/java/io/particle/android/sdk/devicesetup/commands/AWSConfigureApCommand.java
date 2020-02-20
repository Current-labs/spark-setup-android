package io.particle.android.sdk.devicesetup.commands;

import com.google.gson.annotations.SerializedName;


/**
 * Configure the access point details to connect to when connect-ap is called. The AP doesn't have
 * to be in the list from scan-ap, allowing manual entry of hidden networks.
 */
public class AWSConfigureApCommand extends Command {

    public final String ssid;

    @SerializedName("pass")
    public final String password;

    @SerializedName("user")
    public final String user;

    @Override
    public String getCommandName() {
        return "WiFi.ConnectSSID";
    }

    public AWSConfigureApCommand(String ssid, String password) {
        this.ssid = ssid;
        this.password = password;
        this.user = null;
    }


    public static class Response {

        @SerializedName("result")
        public final Boolean result;  // true == OK, false == problem

        public Response(Boolean result) {
            this.result = result;
        }

        public boolean isOk() {
            return result;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "result=" + result +
                    '}';
        }
    }
}
