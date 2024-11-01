package strategies;

import enums.ParkingSlotStatus;
import enums.VehicleType;
import models.ParkingFloor;
import models.ParkingLot;
import models.ParkingSlot;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RandomSlotAssignmentStrategy implements SlotAssignmentStrategy {
    @Override
    public Optional<ParkingSlot> getSlot(VehicleType vehicleType, ParkingLot parkingLot) {
        for (ParkingFloor parkingFloor : parkingLot.getParkingFloors()) {
        	List<ParkingSlot> slots = parkingFloor.getParkingSlots();
        	Collections.shuffle(slots);
            for (ParkingSlot parkingSlot : slots) {
                if (parkingSlot.getParkingSlotStatus().equals(ParkingSlotStatus.UNOCCUPIED)
                        && parkingSlot.getAllowedVehicleTypes().contains(vehicleType)) {
                    return Optional.of(parkingSlot);
                }
            }
        }

        return Optional.empty();
    }
}
