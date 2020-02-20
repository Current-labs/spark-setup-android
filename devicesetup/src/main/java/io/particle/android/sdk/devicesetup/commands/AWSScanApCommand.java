package io.particle.android.sdk.devicesetup.commands;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import io.particle.android.sdk.devicesetup.commands.data.WifiSecurity;

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

        public Integer getParticleWifiSecurityType() {
            switch (wifiSecurityType) {
                case 0:
                    return WifiSecurity.OPEN.asInt();
                case 1:
                    return WifiSecurity.WEP_PSK.asInt();
                case 2:
                    return WifiSecurity.WPA_MIXED_PSK.asInt();
                case 3:
                case 4:
                    return WifiSecurity.WPA2_MIXED_PSK.asInt();
                case 5:
                    return 0x02000000;
                default:
                    return 69;
            }
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
