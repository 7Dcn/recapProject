package com.etiya.recapProject.business.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

	public static final String REQUIRED = "required";
	public static final String SIZE = "size must be between {min} and {max}";
	public static final String MIN = "must be greater than {value}";
	public static final String MAX = "must be less than {value}";
	public static final String POSITIVE = "should be positive";
	public static final String FUTURE = "should be in the future";
	public static final String PATTERN = "is not in correct format";

	public static final String BRANDADD = "Brand added.";
	public static final String BRANDUPDATE = "Brand updated.";
	public static final String BRANDDELETE = "Brand deleted.";
	public static final String BRANDLIST = "Brands listed.";
	public static final String BRANDNAMEERROR = "This brand exists.";

	public static final String CARADD = "Car added.";
	public static final String CARUPDATE = "Car updated.";
	public static final String CARDELETE = "Car deleted.";
	public static final String CARLIST = "Cars listed.";

	public static final String COLORADD = "Color added.";
	public static final String COLORUPDATE = "Color updated.";
	public static final String COLORDELETE = "Color deleted.";
	public static final String COLORLIST = "Colors listed.";
	public static final String COLORNAMEERROR = "This color exists.";

	public static final String CUSTOMERADD = "Customer added.";
	public static final String CUSTOMERUPDATE = "Customer updated.";
	public static final String CUSTOMERDELETE = "Customer deleted.";
	public static final String CUSTOMERLIST = "Customers listed.";

	public static final String USERLIST = "Users listed.";

	public static final String EMAILERROR = "This email is used.";

	public static final String RENTALADD = "Rental added.";
	public static final String RENTALUPDATE = "Rental updated.";
	public static final String RENTALDELETE = "Rental deleted.";
	public static final String RENTALLIST = "Rentals listed.";
	public static final String RENTALDATEERROR = "Vehicle can not rented.";
	public static final String RENTALDATESUCCESS = "Vehicle can be rented.";
	public static final String RENTALFINDEXPOINTERROR = "Your findex point score is not enough for this car.";
	public static final String RENTALMAINTENANCEERROR = "The car is currently under maintenance.";

	public static final String CARIMAGEADD = "Car image added.";
	public static final String CARIMAGEUPDATE = "Car image updated.";
	public static final String CARIMAGEDELETE = "Car image deleted.";
	public static final String CARIMAGELIST = "Car images listed.";
	public static final String CARIMAGELIMITERROR = "A car can not have more than 5 pictures.";
	public static final String CARIMAGEDEFAULT = "Showing default image";
	public static final String CARIMAGEEMPTY = "No image selected.";
	public static final String CARIMAGETYPEERROR = "The file you selected is not an image file.";

	public static final String LOGINEMAILERROR = "This email is not registered";
	public static final String LOGINPASSWORDERROR = "Wrong password.";
	public static final String LOGINSUCCESS = "Login successful.";

	public static final String CREDITCARDADD = "Credit card added.";
	public static final String CREDITCARDUPDATE = "Credit card updated.";
	public static final String CREDITCARDELETE = "Credit card deleted.";
	public static final String CREDITCARDLIST = "Credit cards listed.";
	public static final String CREDITCARDNUMBERERROR = "Credit card number is invalid.";
	public static final String CREDITCARDDATEERROR = "Credit card date is invalid.";
	public static final String CREDITCARDCVCERROR = "Credit card CVC is invalid.";

	public static final String PAYMENTADD = "Payment added.";
	public static final String PAYMENTUPDATE = "Payment updated.";
	public static final String PAYMENTDELETE = "Payment deleted.";
	public static final String PAYMENTLIST = "Payments listed.";
	public static final String PAYMENTCARDSAVE = "Credit card saved.";
	public static final String PAYMENTCARDNOTSAVE = "Credit card not saved.";
	public static final String PAYMENTCARDFAIL = "The payment could not be made.";

	public static final String CARMAINTENANCEADD = "Car maintenance added.";
	public static final String CARMAINTENANCEUPDATE = "Car maintenance updated.";
	public static final String CARMAINTENANCEDELETE = "Car maintenance deleted.";
	public static final String CARMAINTENANCELIST = "Cars in maintenance listed.";
	public static final String CARMAINTENANCERENTALERROR = "The car is currently rented.";

	public static final String INVOICEADD = "Invoice added.";
	public static final String INVOICEUPDATE = "Invoice updated.";
	public static final String INVOICEDELETE = "Invoice deleted.";
	public static final String INVOICELIST = "Invoice listed.";
	public static final String INVOICEBYCUSTOMERLIST = "Invoice listed.";
	
	public static final String DAMAGEADD = "Damage added.";
	public static final String DAMAGEUPDATE = "Damage updated.";
	public static final String DAMAGEDELETE = "Damage deleted.";
	public static final String DAMAGELIST = "Damages listed.";
	
	public static final String ADDITIONALSERVICEADD = "Additional service added.";
	public static final String ADDITIONALSERVICEUPDATE = "Additional service updated.";
	public static final String ADDITIONALSERVICEDELETE = "Additional service deleted.";
	public static final String ADDITIONALSERVICELIST = "Additional services listed.";
	

}
