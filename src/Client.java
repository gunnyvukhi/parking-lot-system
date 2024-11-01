

import java.util.concurrent.TimeUnit;

import controllers.BillController;
import controllers.TicketController;
import dtos.IssueBillRequestDTO;
import dtos.IssueBillResponseDTO;
import dtos.IssueTicketRequestDTO;
import dtos.IssueTicketResponseDTO;
import enums.VehicleType;
import models.Bill;
import models.Payment;
import models.Ticket;
import repositories.*;
import services.BillService;
import services.TicketService;

public class Client {
    public static void main(String[] args) {
        try {
            LocationRepository locationRepository = new LocationRepository();

            OperatorRepository operatorRepository = new OperatorRepository(locationRepository);

            GateRepository gateRepository = new GateRepository(operatorRepository);

            ParkingFloorRepository parkingFloorRepository = new ParkingFloorRepository();
            parkingFloorRepository.populateDummyParkingFloors();

            ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
            parkingLotRepository.populateDummyParkingLots(locationRepository, gateRepository, parkingFloorRepository);

            VehicleRepository vehicleRepository = new VehicleRepository();

            TicketRepository ticketRepository = new TicketRepository();

            TicketService ticketService = new TicketService(gateRepository, vehicleRepository, parkingLotRepository, ticketRepository);

            TicketController ticketController = new TicketController(ticketService);
            
            BillRepository billRepository = new BillRepository();

            BillService billService = new BillService(gateRepository, billRepository, parkingLotRepository);

            BillController billController = new BillController(billService);
            
            // In 1
            
            IssueTicketRequestDTO issueTicketRequestDTO = new IssueTicketRequestDTO();
            issueTicketRequestDTO.setGateId(1L);
            issueTicketRequestDTO.setVehicleNumber("LY 1056 XL");
            issueTicketRequestDTO.setOwnerName("Tsuki");
            issueTicketRequestDTO.setVehicleType(VehicleType.CAR);
            IssueTicketResponseDTO issueTicketResponseDTO = ticketController.issueTicket(issueTicketRequestDTO);
            Ticket ticket1 = issueTicketResponseDTO.getTicket();
            System.out.println("IN: " + ticket1.getTicketNumber() + " | Staff - " + ticket1.getGeneratedBy().getName() + " | Entry - " + ticket1.getEntryTime());
            System.out.println("Parking at: " + ticket1.getParkingSlot().getSlotNumber() + "\n");
            TimeUnit.SECONDS.sleep(1);
            // In 2
            IssueTicketRequestDTO issueTicketRequestDTO2 = new IssueTicketRequestDTO();
            issueTicketRequestDTO2.setGateId(1L);
            issueTicketRequestDTO2.setVehicleNumber("HONDA SH 29A2");
            issueTicketRequestDTO2.setOwnerName("YaMaMoTo");
            issueTicketRequestDTO2.setVehicleType(VehicleType.BIKE);
            IssueTicketResponseDTO issueTicketResponseDTO2 = ticketController.issueTicket(issueTicketRequestDTO2);
            Ticket ticket2 = issueTicketResponseDTO2.getTicket();
            System.out.println("IN: " + ticket2.getTicketNumber() + " | Staff - " + ticket2.getGeneratedBy().getName() + " | Entry - " + ticket2.getEntryTime());
            System.out.println("Parking at: " + ticket2.getParkingSlot().getSlotNumber() + "\n");

            // In 3 // Will throw "Slot not found Exception" Because have run out of slot
            
//            IssueTicketRequestDTO issueTicketRequestDTO3 = new IssueTicketRequestDTO();
//            issueTicketRequestDTO3.setGateId(1L);
//            issueTicketRequestDTO3.setVehicleNumber("Rolls Royce 123");
//            issueTicketRequestDTO3.setOwnerName("Tuan Anh");
//            issueTicketRequestDTO3.setVehicleType(VehicleType.CAR);
//            IssueTicketResponseDTO issueTicketResponseDTO3 = ticketController.issueTicket(issueTicketRequestDTO3);
//           
            TimeUnit.SECONDS.sleep(1);
            // Out 1

            IssueBillRequestDTO issueBillRequestDTO = new IssueBillRequestDTO();
            issueBillRequestDTO.setGateId(2L);
            issueBillRequestDTO.setTicket(ticket1);
            issueBillRequestDTO.setPayCash(10.0);
            issueBillRequestDTO.setPayCard(5.0);
            issueBillRequestDTO.setPayOnline(15.0);
            
            IssueBillResponseDTO issueBillResponseDTO = billController.issueBill(issueBillRequestDTO);

            Bill bill = issueBillResponseDTO.getBill();
            
            System.out.println("OUT: " + bill.getBillNumber() + " | Staff - " + bill.getGeneratedBy().getName() + " | Exit - " + bill.getExitTime());
            System.out.println("TOTAL - " + bill.getAmount());
            for (Payment payment : bill.getPayments()) {
                System.out.println("PAID " + payment.getAmount() + " by " + payment.getPaymentMode());
            }
            System.out.println("STATUS - " + bill.getBillStatus() + "\n");
            
            TimeUnit.SECONDS.sleep(1);
            // Out 2
            
            IssueBillRequestDTO issueBillRequestDTO2 = new IssueBillRequestDTO();
            issueBillRequestDTO2.setGateId(2L);
            issueBillRequestDTO2.setTicket(ticket2);
            issueBillRequestDTO2.setPayCash(10.0);
            issueBillRequestDTO2.setPayCard(20.0);
            issueBillRequestDTO2.setPayOnline(5.0);
            
            IssueBillResponseDTO issueBillResponseDTO2 = billController.issueBill(issueBillRequestDTO2);

            Bill bill2 = issueBillResponseDTO2.getBill();
            
            System.out.println("OUT: " + bill2.getBillNumber() + " | Staff - " + bill2.getGeneratedBy().getName() + " | Exit - " + bill2.getExitTime());
            System.out.println("TOTAL - " + bill2.getAmount());
            for (Payment payment : bill2.getPayments()) {
                System.out.println("PAID " + payment.getAmount() + " by " + payment.getPaymentMode());
            }
            System.out.println("STATUS - " + bill2.getBillStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
