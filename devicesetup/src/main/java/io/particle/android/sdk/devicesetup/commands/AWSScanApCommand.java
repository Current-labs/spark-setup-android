package io.particle.android.sdk.devicesetup.commands;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class AWSScanApCommand extends NoArgsCommand {

    @Override
    public String getCommandName() {
        return "WiFi.PortalScan";
    }


    public static class Response {

        // using an array here instead of a generic
        // collection makes Gson usage simpler
        public final Scan[] scans;

        public Response(Scan[] scans) {
            this.scans = scans;
        }

        public List<Scan> getScans() {
            return Arrays.asList(scans);
        }

        @Override
        public String toString() {
            return "Response{" +
                    "scans=" + Arrays.toString(scans) +
                    '}';
        }
    }


    public static class Scan extends ScanApCommand.Scan {
        @SerializedName("auth")
        public final Integer wifiSecurityType;

        @SerializedName("channel")
        public final Integer channel;

        public Scan(String ssid, Integer wifiSecurityType, Integer channel) {
            super(ssid, wifiSecurityType, channel);
            this.wifiSecurityType = wifiSecurityType;
            this.channel = channel;
        }

        @Override
        public String toString() {
            return "Scan{" +
                    "ssid='" + ssid + '\'' +
                    ", wifiSecurityType=" + wifiSecurityType +
                    ", channel=" + channel +
                    '}';
        }
    }

}
