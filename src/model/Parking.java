package model;

import java.sql.Time;

public class Parking {

  protected int parkingId;
  protected String facilityName;
  protected String facilityAddress;
  protected Time hrsOpenWeek;
  protected Time hrsOpenSat;
  protected Time hrsOpenSun;
  protected Double rate1HR;
  protected Double rate2HR;
  protected Double rate3HR;
  protected Double rateAllDay;
  protected int capacity;


  // This constructor can be used for reading records from MySQL, where we have all fields,
  // including the parkingId.
  public Parking(int parkingId, String facilityName, String facilityAddress, Time hrsOpenWeek, Time hrsOpenSat, Time hrsOpenSun, Double rate1HR, Double rate2HR, Double rate3HR, Double rateAllDay, int capacity) {
    this.parkingId = parkingId;
    this.facilityName = facilityName;
    this.facilityAddress = facilityAddress;
    this.hrsOpenWeek = hrsOpenWeek;
    this.hrsOpenSat = hrsOpenSat;
    this.hrsOpenSun = hrsOpenSun;
    this.rate1HR = rate1HR;
    this.rate2HR = rate2HR;
    this.rate3HR = rate3HR;
    this.rateAllDay = rateAllDay;
    this.capacity = capacity;
  }


  // This constructor can be used for reading records from MySQL, where we only have the parkingId,
  // such as a foreign key reference to PostId.
  // Given parkingId, we can fetch the full Parking record.
  public Parking(int parkingId) {
    this.parkingId = parkingId;
  }


  // This constructor can be used for creating new records, where the parkingId may not be
  // assigned yet since it is auto-generated by MySQL.
  public Parking(String facilityName, String facilityAddress, Time hrsOpenWeek, Time hrsOpenSat, Time hrsOpenSun, Double rate1HR, Double rate2HR, Double rate3HR, Double rateAllDay, int capacity) {
    this.facilityName = facilityName;
    this.facilityAddress = facilityAddress;
    this.hrsOpenWeek = hrsOpenWeek;
    this.hrsOpenSat = hrsOpenSat;
    this.hrsOpenSun = hrsOpenSun;
    this.rate1HR = rate1HR;
    this.rate2HR = rate2HR;
    this.rate3HR = rate3HR;
    this.rateAllDay = rateAllDay;
    this.capacity = capacity;
  }

  public int getParkingId() {
    return parkingId;
  }

  public void setParkingId(int parkingId) {
    this.parkingId = parkingId;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public String getFacilityAddress() {
    return facilityAddress;
  }

  public void setFacilityAddress(String facilityAddress) {
    this.facilityAddress = facilityAddress;
  }

  public Time getHrsOpenWeek() {
    return hrsOpenWeek;
  }

  public void setHrsOpenWeek(Time hrsOpenWeek) {
    this.hrsOpenWeek = hrsOpenWeek;
  }

  public Time getHrsOpenSat() {
    return hrsOpenSat;
  }

  public void setHrsOpenSat(Time hrsOpenSat) {
    this.hrsOpenSat = hrsOpenSat;
  }

  public Time getHrsOpenSun() {
    return hrsOpenSun;
  }

  public void setHrsOpenSun(Time hrsOpenSun) {
    this.hrsOpenSun = hrsOpenSun;
  }

  public Double getRate1HR() {
    return rate1HR;
  }

  public void setRate1HR(Double rate1HR) {
    this.rate1HR = rate1HR;
  }

  public Double getRate2HR() {
    return rate2HR;
  }

  public void setRate2HR(Double rate2HR) {
    this.rate2HR = rate2HR;
  }

  public Double getRate3HR() {
    return rate3HR;
  }

  public void setRate3HR(Double rate3HR) {
    this.rate3HR = rate3HR;
  }

  public Double getRateAllDay() {
    return rateAllDay;
  }

  public void setRateAllDay(Double rateAllDay) {
    this.rateAllDay = rateAllDay;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
}
