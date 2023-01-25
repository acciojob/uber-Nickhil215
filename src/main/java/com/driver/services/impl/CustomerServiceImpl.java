package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		Customer customer=customerRepository2.findById(customerId).get();

		customerRepository2.delete(customer);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList=driverRepository2.findAll();
		Driver driver1=null;

		int min=Integer.MAX_VALUE;
		for(Driver driver:driverList){
			if(driver.getDriverId()<min && driver.getCab().getAvailable()){
				min=driver.getDriverId();
				driver1=driver;
			}
		}

		if(min<Integer.MAX_VALUE && driver1!=null) {

			TripBooking tripBooking = new TripBooking(fromLocation, toLocation, distanceInKm, TripStatus.CONFIRMED);


			//Driver driver=driverRepository2.findById(driverId).get();
			driver1.getCab().setAvailable(false);
			tripBooking.setBill(driver1.getCab().getPerKmRate() * distanceInKm);
			Customer customer = customerRepository2.findById(customerId).get();

			tripBooking.setDriver(driver1);
			tripBooking.setCustomer(customer);

			driver1.getTripBookingList().add(tripBooking);
			customer.getTripBookingList().add(tripBooking);

			driverRepository2.save(driver1);
			customerRepository2.save(customer);
			return tripBooking;

		}
		else throw new Exception("No cab available!");


	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		//if(tripBookingRepository2.findById(tripId).isPresent()){

		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		Driver driver=tripBooking.getDriver();
		driver.getCab().setAvailable(true);
		tripBookingRepository2.save(tripBooking);



	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		if(tripBookingRepository2.findById(tripId).isPresent()){
			TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
			tripBooking.setStatus(TripStatus.COMPLETED);
			Driver driver=tripBooking.getDriver();
			driver.getCab().setAvailable(true);
			tripBookingRepository2.save(tripBooking);

		}

	}
}
