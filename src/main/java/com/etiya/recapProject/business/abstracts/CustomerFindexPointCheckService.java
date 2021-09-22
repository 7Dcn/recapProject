package com.etiya.recapProject.business.abstracts;

import com.etiya.recapProject.entities.concretes.CorporateCustomer;
import com.etiya.recapProject.entities.concretes.IndividualCustomer;

public interface CustomerFindexPointCheckService {

	int checkIndividualCustomerFindexPoint(IndividualCustomer individualCustomer);
	int checkCorporateCustomerFindexPoint(CorporateCustomer corporateCustomer);
}
