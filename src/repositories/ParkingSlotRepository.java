package repositories;

import enums.ParkingSlotStatus;
import enums.VehicleType;
import exceptions.ParkingFloorNotFoundException;
import models.ParkingFloor;
import models.ParkingSlot;

import java.util.*;

public class ParkingSlotRepository {
    private final Map<Long, ParkingSlot> parkingSlotMap;
    private static Long previousId = 0L;

    public ParkingSlotRepository() {
        parkingSlotMap = new TreeMap<>();
    }

    public void populateDummyParkingSlots(ParkingFloorRepository parkingFloorRepository) throws ParkingFloorNotFoundException {
        Optional<ParkingFloor> parkingFloorOptional = parkingFloorRepository.getParkingFloorsById(1L);
        if (parkingFloorOptional.isEmpty()) {
            throw new ParkingFloorNotFoundException();
        }
        ParkingFloor parkingFloor = parkingFloorOptional.get();

        ParkingSlot parkingSlot_1 = new ParkingSlot();
        parkingSlot_1.setSlotNumber("BIK");
        parkingSlot_1.setAllowedVehicleTypes(List.of(VehicleType.BIKE));
        parkingSlot_1.setParkingSlotStatus(ParkingSlotStatus.UNOCCUPIED);
        parkingSlot_1.setParkingFloorNumber(parkingFloor);
        saveParkingSlot(parkingSlot_1);

        ParkingSlot parkingSlot_2 = new ParkingSlot();
        parkingSlot_2.setSlotNumber("INA");
        parkingSlot_2.setAllowedVehicleTypes(Arrays.asList(VehicleType.CAR, VehicleType.BIKE));
        parkingSlot_2.setParkingSlotStatus(ParkingSlotStatus.UNOCCUPIED);
        parkingSlot_2.setParkingFloorNumber(parkingFloor);
        saveParkingSlot(parkingSlot_2);
        
        ParkingSlot parkingSlot_3 = new ParkingSlot();
        parkingSlot_3.setSlotNumber("PDM");
        parkingSlot_3.setAllowedVehicleTypes(Arrays.asList(VehicleType.CAR, VehicleType.BIKE));
        parkingSlot_3.setParkingSlotStatus(ParkingSlotStatus.UNOCCUPIED);
        parkingSlot_3.setParkingFloorNumber(parkingFloor);
        saveParkingSlot(parkingSlot_3);
        
        ParkingSlot parkingSlot_4 = new ParkingSlot();
        parkingSlot_4.setSlotNumber("CGA");
        parkingSlot_4.setAllowedVehicleTypes(Arrays.asList(VehicleType.BUS, VehicleType.TRUCK));
        parkingSlot_4.setParkingSlotStatus(ParkingSlotStatus.UNOCCUPIED);
        parkingSlot_4.setParkingFloorNumber(parkingFloor);
        saveParkingSlot(parkingSlot_4);

        parkingFloor.setParkingSlots(Arrays.asList(parkingSlot_1, parkingSlot_2, parkingSlot_3, parkingSlot_4));
    }

    public ParkingSlot saveParkingSlot(ParkingSlot parkingSlot) {
        previousId++;
        parkingSlot.setId(previousId);
        parkingSlot.setCreatedAt(new Date());
        parkingSlotMap.put(previousId, parkingSlot);
        return parkingSlot;
    }
}
