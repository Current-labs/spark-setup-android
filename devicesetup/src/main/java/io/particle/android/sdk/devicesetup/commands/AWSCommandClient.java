package io.particle.android.sdk.devicesetup.commands;

import android.support.annotation.CheckResult;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.net.SocketFactory;

import io.particle.android.sdk.utils.TLog;


// Super hacky extension because ain't nobody got time for this...
public class AWSCommandClient extends CommandClient {
    static final int DEFAULT_TIMEOUT_SECONDS = 10;

    private static final TLog log = TLog.get(AWSCommandClient.class);
    private static final Gson gson = new Gson();

    private final String ipAddress;

    AWSCommandClient(String ipAddress, int port, SocketFactory socketFactory) {
        super(ipAddress, port, socketFactory);
        this.ipAddress = ipAddress;
    }

    public void sendCommand(Command command) throws IOException {
        sendAndMaybeReceive(command, Void.class);
    }

    @CheckResult
    public <T> T sendCommand(Command command, Class<T> responseType) throws IOException {
        return sendAndMaybeReceive(command, responseType);
    }


    private <T> T sendAndMaybeReceive(Command command, Class<T> responseType) throws IOException {
        log.i("Preparing to send command '" + command.getCommandName() + "'");

        String commandName = command.getCommandName();
        String commandArgs = command.argsAsJsonString(gson);

        OkHttpClient client = new OkHttpClient();
        String url = "http://" + ipAddress + "/rpc/" + commandName;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        // if no response defined, just exit early.
        if (responseType.equals(Void.class)) {
            log.d("Done.");
            return null;
        }

        String responseString = response.body().string();
        log.d("Command response (raw): " + responseString);
        return gson.fromJson(responseString, responseType);
    }
}
