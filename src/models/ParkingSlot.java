package models;

import enums.ParkingSlotStatus;
import enums.VehicleType;

import java.util.List;

public class ParkingSlot extends BaseModel {
    private String slotNumber;
    private List<VehicleType> allowedVehicleTypes;
    private ParkingSlotStatus parkingSlotStatus;
    private ParkingFloor parkingFloorNumber;

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public List<VehicleType> getAllowedVehicleTypes() {
        return allowedVehicleTypes;
    }

    public void setAllowedVehicleTypes(List<VehicleType> allowedVehicleTypes) {
        this.allowedVehicleTypes = allowedVehicleTypes;
    }

    public ParkingSlotStatus getParkingSlotStatus() {
        return parkingSlotStatus;
    }

    public void setParkingSlotStatus(ParkingSlotStatus parkingSlotStatus) {
        this.parkingSlotStatus = parkingSlotStatus;
    }

    public ParkingFloor getParkingFloorNumber() {
        return parkingFloorNumber;
    }

    public void setParkingFloorNumber(ParkingFloor parkingFloorNumber) {
        this.parkingFloorNumber = parkingFloorNumber;
    }
}
