package com.calpullix.service.client.classification.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.calpullix.service.client.classification.dao.ClientClassificationDAO;
import com.calpullix.service.client.classification.model.BranchDTO;
import com.calpullix.service.client.classification.model.BranchRowMapper;
import com.calpullix.service.client.classification.model.MunicipalityDTO;
import com.calpullix.service.client.classification.model.MunicipalityRowMapper;
import com.calpullix.service.client.classification.model.ProductDTO;
import com.calpullix.service.client.classification.model.ProductRowMapper;
import com.calpullix.service.client.classification.model.StateDTO;
import com.calpullix.service.client.classification.model.StateRowMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientClassificationDAOImpl implements ClientClassificationDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	@Value("${app.query-branch}")
	private String queryBranch;

	@Value("${app.query-product}")
	private String queryProduct;

	@Value("${app.query-state}")
	private String queryState;

	@Value("${app.query-municipality}")
	private String queryMunicipality;

	
	@Override
	public List<BranchDTO> getBranches(Integer idProfile) {
		List<BranchDTO> result = jdbcTemplate.query(
				queryBranch, new Object[] { idProfile }, new BranchRowMapper());
		log.info(":: Result DAO branches {} ", result);
		return result;
	}
	
	@Override
	public List<ProductDTO> getProducts(Integer idProfile) {
		List<ProductDTO> result = jdbcTemplate.query(
				queryProduct, new Object[] { idProfile }, new ProductRowMapper());
		log.info(":: Result DAO products {} ", result);
		return result;
	}

	@Override
	public List<StateDTO> getStates(Integer idProfile) {
		List<StateDTO> result = jdbcTemplate.query(
				queryState, new Object[] { idProfile }, new StateRowMapper());
		log.info(":: Result DAO states {} ", result);
		return result;
	}

	@Override
	public List<MunicipalityDTO> getMunicipalities(Integer idProfile) {
		List<MunicipalityDTO> result = jdbcTemplate.query(
				queryMunicipality, new Object[] { idProfile }, new MunicipalityRowMapper());
		log.info(":: Result DAO states {} ", result);
		return result;
	}
	

}
