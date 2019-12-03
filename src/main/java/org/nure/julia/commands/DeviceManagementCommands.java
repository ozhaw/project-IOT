package org.nure.julia.commands;

import org.nure.julia.generator.DeviceService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@ShellComponent
public class DeviceManagementCommands {
    private final DeviceService deviceService;

    public DeviceManagementCommands(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @ShellMethod(key = {"add-device"}, value = "Add a new device")
    public void addDevice(String deviceId) {
        if (!deviceService.exists(deviceId)) {
            deviceService.addDevice(deviceId);
        } else {
            System.out.println(format("Device {%s} exists", deviceId));
        }
    }

    @ShellMethod(key = {"add-devices"}, value = "Add a new devices")
    public void addDevices(Set<String> deviceIds) {
        deviceIds.forEach(this::addDevice);
    }

    @ShellMethod(key = {"remove-device"}, value = "Remove device")
    public String removeDevice(String deviceId) {
        return deviceService.remove(deviceId)
                ? format("Device {%s} was removed", deviceId)
                : format("Device {%s} not exist", deviceId);
    }

    @ShellMethod(key = {"remove-devices"}, value = "Remove devices")
    public String removeDevices(Set<String> deviceIds) {
        return deviceIds.stream()
                .map(this::removeDevice)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(key = {"show-devices"}, value = "Show devices")
    public Collection<String> showDevices() {
        return deviceService.getDeviceIds();
    }
}
