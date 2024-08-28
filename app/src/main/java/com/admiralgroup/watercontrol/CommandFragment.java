package com.admiralgroup.watercontrol;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Map;

public class CommandFragment extends Fragment {
    private static final String ARG_ACTION = "action";

    private PermissionsResponse.Action action;

    public static CommandFragment newInstance(PermissionsResponse.Action action) {
        CommandFragment fragment = new CommandFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACTION, action);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_command, container, false);

        LinearLayout commandContainer = view.findViewById(R.id.commandContainer);
        if (action != null) {
            for (Map.Entry<String, PermissionsResponse.Action.Command> entry : action.getItems().entrySet()) {
                PermissionsResponse.Action.Command command = entry.getValue();
                addCommandView(commandContainer, command);
            }
        }
        return view;
    }

    private void addCommandView(LinearLayout container, PermissionsResponse.Action.Command command) {
        TextView commandLabel = new TextView(getContext());
        commandLabel.setText(command.getLabel());
        container.addView(commandLabel);

        Button executeButton = new Button(getContext());
        executeButton.setText("Execute " + command.getLabel());
        executeButton.setOnClickListener(v -> executeCommand(command));
        container.addView(executeButton);
    }

    private void executeCommand(PermissionsResponse.Action.Command command) {
        // Generate payload with parameters and send it to the Bluetooth device
        String payload = generatePayload(command);
        sendPayloadToBluetoothDevice(payload);
    }

    private String generatePayload(PermissionsResponse.Action.Command command) {
        String payload = command.getPayload();
        // Replace {HOLDERS} in payload with actual values
        if (command.getParameters() != null) {
            for (Map.Entry<String, PermissionsResponse.Action.Command.Parameter> entry : command.getParameters().entrySet()) {
                String key = entry.getKey();
                PermissionsResponse.Action.Command.Parameter parameter = entry.getValue();
                String value = getParameterValue(parameter);
                payload = payload.replace("{" + key + "}", value);
            }
        }
        return payload;
    }

    private String getParameterValue(PermissionsResponse.Action.Command.Parameter parameter) {
        // Handle parameter types like text, int, checksum, etc.
        String value = ""; // Default value

        switch (parameter.getType()) {
            case "text":
                value = parameter.getValue();
                break;
            case "int":
                value = String.format("%04x", Integer.parseInt(parameter.getValue()));
                break;
            case "checksum":
                // Calculate checksum value
                value = calculateChecksum(parameter.getValue());
                break;
            // Handle other cases like IP, equal, etc.
        }

        return value;
    }

    private String calculateChecksum(String data) {
        // Implement checksum calculation logic here
        return "AA"; // Placeholder checksum value
    }

    private void sendPayloadToBluetoothDevice(String payload) {
        // Implement Bluetooth communication here
        // For example: Send the payload via Bluetooth to the selected device
        // BluetoothDevice device = getSelectedBluetoothDevice();
        Log.d("CommandFragment", "Sending payload: " + payload);
    }
}
