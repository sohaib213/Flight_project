package com.example.flight_project_1.Base_classes;

public class BusinessSeat extends Seat {
    private double cost=1.3;
    public BusinessSeat(String seatId,boolean seatStatus) {
        super(seatId,"Business",seatStatus);
    }

    @Override
    public String calcSeatPrice(Flight flight) {
        return String.valueOf(Integer.valueOf((int) (cost*flight.getPrice())));
    }

    @Override
    public String toString() {
        return "BusinessSeat{" +
                "cost=" + cost +
                ", seatId='" + seatId + '\'' +
                ", seatClass='" + seatClass + '\'' +
                ", seatStatus=" + seatStatus +
                '}';
    }
}