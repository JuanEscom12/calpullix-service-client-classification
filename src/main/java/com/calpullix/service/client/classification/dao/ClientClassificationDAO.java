package com.calpullix.service.client.classification.dao;

import java.util.List;

import com.calpullix.service.client.classification.model.BranchDTO;
import com.calpullix.service.client.classification.model.MunicipalityDTO;
import com.calpullix.service.client.classification.model.ProductDTO;
import com.calpullix.service.client.classification.model.StateDTO;

public interface ClientClassificationDAO {

	List<BranchDTO> getBranches(Integer idProfile);
	
	List<ProductDTO> getProducts(Integer idProfile);
	
	List<StateDTO> getStates(Integer idProfile);
	
	List<MunicipalityDTO> getMunicipalities(Integer idProfile);
	
}
