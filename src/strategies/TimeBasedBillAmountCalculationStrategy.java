package strategies;
import enums.VehicleType;
import models.Bill;

public class TimeBasedBillAmountCalculationStrategy implements BillAmountCalculationStrategy {
    private final double MINUTE_CHARGE_BIKE = 20d;
    private final double MINUTE_CHARGE_CAR = 50d;
    private final double MINUTE_CHARGE_BUS = 40d;
    private final double MINUTE_CHARGE_TRUCK = 60d;

    @Override
    public double calculateBill(Bill bill) {
    	VehicleType vehicleType = bill.getTicket().getVehicle().getVehicleType();
        long timeDifference = bill.getExitTime().getTime() - bill.getTicket().getEntryTime().getTime();
        long differenceInHours = timeDifference / (60 * 1000) + 1;
        if (vehicleType == VehicleType.TRUCK) {
        	return differenceInHours * (MINUTE_CHARGE_TRUCK);
        } else if (vehicleType == VehicleType.BIKE) {
        	return differenceInHours * (MINUTE_CHARGE_BIKE);
        } else if (vehicleType == VehicleType.BUS) {
        	return differenceInHours * (MINUTE_CHARGE_BUS);
        } return differenceInHours * (MINUTE_CHARGE_CAR);
        
    }
}
