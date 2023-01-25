//package com.driver.services.impl;
//
//import com.driver.model.TripBooking;
//import com.driver.repository.CabRepository;
//import com.driver.services.CustomerService;
//import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.driver.model.Customer;
//import com.driver.model.Driver;
//import com.driver.repository.CustomerRepository;
//import com.driver.repository.DriverRepository;
//import com.driver.repository.TripBookingRepository;
//import com.driver.model.TripStatus;
//
//import java.util.List;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//	@Autowired
//	CustomerRepository customerRepository2;
//
//	@Autowired
//	DriverRepository driverRepository2;
//
//	@Autowired
//	TripBookingRepository tripBookingRepository2;
//	@Autowired
//	private CabRepository cabRepository;
//
//	@Override
//	public void register(Customer customer) {
//		//Save the customer in database
//		customerRepository2.save(customer);
//	}
//
//	@Override
//	public void deleteCustomer(Integer customerId) {
//		// Delete customer without using deleteById function
//		Customer customer= customerRepository2.findById(customerId).get();
//		customerRepository2.delete(customer);
//
//	}
//
//	@Override
//	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
////		Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
////		Avoid using SQL query
//		List<Driver> driverList=driverRepository2.findAll();
//		Driver driver=null;
//		int lowId=Integer.MAX_VALUE;
//		for(Driver driver1:driverList){
//			if(driver1.getDriverId()<lowId && driver.getCab().getAvailable()){
//				lowId=driver1.getDriverId();
//				driver=driver1;
//			}
//		}
//
//		if(driver==null){
//			throw new Exception("No cab available!");
//		}
//
//		if(driver!=null){
//			Customer customer=customerRepository2.findById(customerId).get();
//
//			TripBooking tripBooking=new TripBooking(toLocation,fromLocation,distanceInKm,TripStatus.CONFIRMED);
//
//			customer.getTripBookingList().add(tripBooking);
//			driver.getTripBookingList().add(tripBooking);
//
//			tripBooking.setCustomer(customer);
//			tripBooking.setDriver(driver);
//
//			tripBooking.setBill(distanceInKm*driver.getCab().getPerKmRate());
//			driver.getCab().setAvailable(false);
//
//			driverRepository2.save(driver);
//			customerRepository2.save(customer);
//			return tripBooking;
//		}
//		else {
//			throw new Exception("No cab available!");
//		}
//
//	}
//
//
//	@Override
//	public void cancelTrip(Integer tripId){
//		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
//		TripBooking trip=tripBookingRepository2.findById(tripId).get();
//		trip.setBill(0);
//		trip.setStatus(TripStatus.CANCELED);
//		Driver driver=trip.getDriver();
//		driver.getCab().setAvailable(true);
//		tripBookingRepository2.save(trip);
//
//	}
//
//	@Override
//	public void completeTrip(Integer tripId){
//		//Complete the trip having given trip Id and update TripBooking attributes accordingly
//		TripBooking trip= tripBookingRepository2.findById(tripId).get();
//		trip.setStatus(TripStatus.COMPLETED);
//		Driver driver=trip.getDriver();
//		driver.getCab().setAvailable(true);
//		tripBookingRepository2.save(trip);
//
//
//	}
//}
//


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
		//Customer customer1=new Customer(customer.getMobile(), customer.getPassword());
		//customerRepository2.save(customer1);
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		//	if(customerRepository2.findById(customerId).isPresent()){
		Customer customer=customerRepository2.findById(customerId).get();

		customerRepository2.delete(customer);
		//}
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList=driverRepository2.findAll();
		Driver driver1=null;

//		if(driverList.size()==0){
//			throw new Exception("No cab available!");
//		}

		int min=Integer.MAX_VALUE;
		for(Driver driver:driverList){
			if(driver.getDriverId()<min && driver.getCab().getAvailable()){
				min=driver.getDriverId();
				driver1=driver;
			}
		}

//		if(min==Integer.MAX_VALUE)
//		{
//			throw new Exception("No cab available!");
//		}

//		int driverId=min;

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

			//	tripBookingRepository2.save(tripBooking);


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

		//driverRepository2.save(driver);


		tripBookingRepository2.save(tripBooking);
		//	}


	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		if(tripBookingRepository2.findById(tripId).isPresent()){
			TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
			tripBooking.setStatus(TripStatus.COMPLETED);

			Driver driver=tripBooking.getDriver();
			driver.getCab().setAvailable(true);

			//driverRepository2.save(driver);


			tripBookingRepository2.save(tripBooking);

		}

	}
}