package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.repository.CabRepository;
import com.driver.services.CustomerService;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;
	@Autowired
	private CabRepository cabRepository;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer= customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
//		Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
//		Avoid using SQL query
		List<Driver> driverList=driverRepository2.findAll();
		Driver driver=null;
		int lowId=Integer.MAX_VALUE;
		for(Driver driver1:driverList){
			if(driver1.getDriverId()<lowId && driver.getCab().getAvailable()){
				lowId=driver1.getDriverId();
				driver=driver1;
			}
		}


		if(lowId<Integer.MAX_VALUE&&driver!=null){
			TripBooking tripBooking=new TripBooking(toLocation,fromLocation,distanceInKm,TripStatus.CONFIRMED);

			Customer customer=customerRepository2.findById(customerId).get();

			driver.getCab().setAvailable(false);

			tripBooking.setCustomer(customer);
			tripBooking.setDriver(driver);

			customer.getTripBookingList().add(tripBooking);
			driver.getTripBookingList().add(tripBooking);

			tripBooking.setBill(distanceInKm*driver.getCab().getPerKmRate());

			driverRepository2.save(driver);
			customerRepository2.save(customer);

			return tripBooking;
		}
		else {
			throw new Exception("No cab available!");
		}

	}


	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking trip=tripBookingRepository2.findById(tripId).get();
		trip.setBill(0);
		trip.setStatus(TripStatus.CANCELED);
		Driver driver=trip.getDriver();
		driver.getCab().setAvailable(true);
		tripBookingRepository2.save(trip);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking trip= tripBookingRepository2.findById(tripId).get();
		trip.setStatus(TripStatus.COMPLETED);
		Driver driver=trip.getDriver();
		driver.getCab().setAvailable(true);
		tripBookingRepository2.save(trip);


	}
}



