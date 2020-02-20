package io.particle.android.sdk.devicesetup.model;

import io.particle.android.sdk.devicesetup.commands.AWSScanApCommand;
import io.particle.android.sdk.devicesetup.commands.data.WifiSecurity;
import io.particle.android.sdk.utils.SSID;


// FIXME: this naming is not ideal.
public class AWSScanAPCommandResult extends ScanAPCommandResult {

    public final AWSScanApCommand.Scan scan;
    public final SSID ssid;

    public AWSScanAPCommandResult(AWSScanApCommand.Scan scan) {
        super(scan);
        this.scan = scan;
        ssid = SSID.from(scan.ssid);
    }

    @Override
    public SSID getSsid() {
        return ssid;
    }

    @Override
    public boolean isSecured() {
        return scan.wifiSecurityType != WifiSecurity.OPEN.asInt();
    }

    @Override
    public String toString() {
        return "AWSScanAPCommandResult{" +
                "scan=" + scan +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AWSScanAPCommandResult that = (AWSScanAPCommandResult) o;

        return getSsid() != null ? getSsid().equals(that.getSsid()) : that.getSsid() == null;
    }

    @Override
    public int hashCode() {
        return getSsid() != null ? getSsid().hashCode() : 0;
    }

}
