package dtos;

import models.Ticket;

public class IssueBillRequestDTO {
    private Long gateId;
    private Ticket ticket;
    private Double payCash = 0.0;
    private Double payCard = 0.0;
    private Double PayOnline = 0.0;

    public Long getGateId() {
        return gateId;
    }

    public void setGateId(Long gateId) {
        this.gateId = gateId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

	public Double getPayCash() {
		return payCash;
	}

	public void setPayCash(Double payCash) {
		this.payCash = payCash;
	}

	public Double getPayCard() {
		return payCard;
	}

	public void setPayCard(Double payCard) {
		this.payCard = payCard;
	}

	public Double getPayOnline() {
		return PayOnline;
	}

	public void setPayOnline(Double payOnline) {
		PayOnline = payOnline;
	}	
}
