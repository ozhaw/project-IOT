package org.nure.julia.commands;

import org.nure.julia.generator.DeviceService;
import org.nure.julia.misc.DeviceJobStatus;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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
    public String addDevice(String deviceId) {
        return !deviceService.exists(deviceId)
                ? deviceService.addDevice(deviceId).getTransitionMessage(deviceId)
                : format("Device {%s} is exists", deviceId);
    }

    @ShellMethod(key = {"add-devices"}, value = "Add a new devices")
    public String addDevices(Set<String> deviceIds) {
        return deviceIds.stream()
                .map(this::addDevice)
                .collect(Collectors.joining(System.lineSeparator()));
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
    public Collection<String> showDevices(@ShellOption(value = {"-JS", "--jobStatus"}, defaultValue = "UNKNOWN") DeviceJobStatus jobStatus) {
        return jobStatus == DeviceJobStatus.UNKNOWN
                ? deviceService.getDeviceIds()
                : deviceService.getDeviceIdsWithStatus(jobStatus);
    }
}
