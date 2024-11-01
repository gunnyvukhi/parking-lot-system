package services;

import enums.BillStatus;
import enums.ParkingSlotStatus;
import exceptions.GateNotFoundException;
import exceptions.ParkingLotNotFoundException;
import factories.BillAmountCalculationStrategyFactory;
import models.*;
import repositories.BillRepository;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import strategies.CardPaymentStrategy;
import strategies.CashPaymentStrategy;
import strategies.OnlinePaymentStrategy;
import strategies.PaymentStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BillService {
    private final GateRepository gateRepository;
    private final BillRepository billRepository;
    private final ParkingLotRepository parkingLotRepository;

    public BillService(GateRepository gateRepository, BillRepository billRepository, ParkingLotRepository parkingLotRepository) {
        this.gateRepository = gateRepository;
        this.billRepository = billRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    public Bill issueBill(Long gateId, Ticket ticket, double payCash, double payCard, double payOnline) throws GateNotFoundException, ParkingLotNotFoundException {
        Bill bill = new Bill();

        Date exitTime = new Date();
        bill.setExitTime(exitTime);

        bill.setTicket(ticket);

        Optional<Gate> gateOptional = gateRepository.getGateById(gateId);
        if (gateOptional.isEmpty()) {
            throw new GateNotFoundException();
        }
        Gate gate = gateOptional.get();
        bill.setGateGeneratedAt(gate);

        bill.setGeneratedBy(gate.getGateOperator());

        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.getParkingLotByGateId(gateId);
        if (parkingLotOptional.isEmpty()) {
            throw new ParkingLotNotFoundException();
        }
        ParkingLot parkingLot = parkingLotOptional.get();
        double billAmount = BillAmountCalculationStrategyFactory.getBillAmountCalculationStrategyByType(parkingLot.getBillAmountCalculationStrategyType()).calculateBill(bill);
        bill.setAmount(billAmount);
        
        double remainingBillAmount = billAmount;
        List<Payment> payments = new ArrayList<Payment>();
        
        // Cash payment
        if (payCash > 0.0 && remainingBillAmount > 0.0) {
        	if (remainingBillAmount < payCash ) {
        		payCash = remainingBillAmount;
        	}
        	remainingBillAmount -= payCash;
        	Payment cashPayment = pay(new CashPaymentStrategy(), payCash);
        	payments.add(cashPayment);
        }
        if (remainingBillAmount > 0.0 && payCard > 0.0) {
        	if (remainingBillAmount < payCard ) {
        		payCard = remainingBillAmount;
        	}
        	remainingBillAmount -= payCard;        	
        	Payment cardPayment = pay(new CardPaymentStrategy(), payCard);
        	payments.add(cardPayment);
        }
        if (remainingBillAmount > 0.0 && payOnline > 0.0) {
        	if (remainingBillAmount < payOnline ) {
        		payOnline = remainingBillAmount;
        	}
        	remainingBillAmount -= payOnline;
        	Payment onlinePayment = pay(new OnlinePaymentStrategy(), payOnline);
        	payments.add(onlinePayment);
        }
        	
        bill.setPayments(payments);
        
        if (remainingBillAmount > 0 ) {
        	bill.setBillStatus(BillStatus.PARTIALLY_PAID);
        } else {bill.setBillStatus(BillStatus.PAID);}
        
        ticket.getParkingSlot().setParkingSlotStatus(ParkingSlotStatus.UNOCCUPIED);

        Bill savedBill = billRepository.saveBill(bill);
        savedBill.setBillNumber("BILL - " + savedBill.getId() + " | Gate - " + gateId + " | Ticket - " + ticket.getId());

        return savedBill;
    }

    private Payment pay(PaymentStrategy paymentStrategy, double amount) {
        return paymentStrategy.pay(amount);
    }
}
