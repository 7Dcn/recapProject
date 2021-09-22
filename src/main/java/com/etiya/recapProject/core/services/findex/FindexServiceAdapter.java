package com.etiya.recapProject.core.services.findex;

import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CustomerFindexPointCheckService;
import com.etiya.recapProject.entities.concretes.CorporateCustomer;
import com.etiya.recapProject.entities.concretes.IndividualCustomer;

@Service
public class FindexServiceAdapter implements CustomerFindexPointCheckService {

	@Override
	public int checkIndividualCustomerFindexPoint(IndividualCustomer individualCustomer) {

		FindexService findexService = new FindexService();

		return findexService.individualCustomerFindexPoint(individualCustomer.getIdentityNumber());
	}

	@Override
	public int checkCorporateCustomerFindexPoint(CorporateCustomer corporateCustomer) {

		FindexService findexService = new FindexService();

		return findexService.corporateCustomerFindexPoint(corporateCustomer.getTaxNumber());
	}

}
