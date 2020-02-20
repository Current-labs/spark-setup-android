package io.particle.android.sdk.devicesetup.commands;

import com.google.gson.annotations.SerializedName;

/**
 * Retrieves the unique device ID as a 6-digit hex string
 */
public class AWSDeviceIdCommand extends NoArgsCommand {

    @Override
    public String getCommandName() {
        return "Sys.GetInfo";
    }


    public static class Response {

        @SerializedName("id")
        public final String deviceId;

        @SerializedName("app")
        public final String product;


        public Response(String deviceId, String product) {
            this.deviceId = deviceId;
            this.product = product;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "deviceId='" + deviceId + '\'' +
                    ", product=" + product +
                    '}';
        }
    }

}
