package com.calpullix.service.client.classification.repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.calpullix.db.process.catalog.model.EducationLevel;
import com.calpullix.service.client.classification.model.ClientDetailResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ProcedureInvoker {

	private static final String LABEL_CLASSIFICATION = "Clasificación 1: ";
	
	private static final String LABEL_CLASSIFICATION_T = ", Clasificación 2: ";
	
	private final EntityManager entityManager;
	
	private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public ProcedureInvoker(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void executeProcedureBranchInformation(
			ClientDetailResponseDTO result,
			Integer idProfile, 
			String nameProcedure) {
    	log.info(":: Executing Procedure {} {} ", nameProcedure, idProfile);
		final StoredProcedureQuery storedProcedureQuery = entityManager.
				createStoredProcedureQuery(nameProcedure);
        storedProcedureQuery.registerStoredProcedureParameter("id_profile", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("startDate", String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("endDate", String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("classificationOne", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("classificationTwo", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("educationLevel", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("job", String.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("monthlyPurchases", BigDecimal.class, ParameterMode.INOUT);
        
        Calendar date = Calendar.getInstance();
        date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 1);
        date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));
        String endDate = formatDate.format(date.getTime());
        date.set(Calendar.YEAR, date.get(Calendar.YEAR) - 1);
        String startDate = formatDate.format(date.getTime());
        log.info(":: Date {} {} ", startDate, endDate);
        storedProcedureQuery.setParameter("id_profile", idProfile);
        storedProcedureQuery.setParameter("startDate", startDate);
        storedProcedureQuery.setParameter("endDate", endDate);
        storedProcedureQuery.setParameter("classificationOne", 0);
        storedProcedureQuery.setParameter("classificationTwo", 0);
        storedProcedureQuery.setParameter("educationLevel", 0);
        storedProcedureQuery.setParameter("job", "");
        storedProcedureQuery.setParameter("monthlyPurchases", BigDecimal.ZERO);
        storedProcedureQuery.execute();

        final Integer classificationOne = (Integer) storedProcedureQuery.getOutputParameterValue("classificationOne");
        final Integer classificationTwo = (Integer) storedProcedureQuery.getOutputParameterValue("classificationTwo");
        final Integer educationLevel = (Integer) storedProcedureQuery.getOutputParameterValue("educationLevel");
        final String job = (String) storedProcedureQuery.getOutputParameterValue("job");  
        final BigDecimal  monthlyPurchases = (BigDecimal) storedProcedureQuery.getOutputParameterValue("monthlyPurchases");      
        
        result.setClassification(LABEL_CLASSIFICATION + (classificationOne == null ? 0 : classificationOne) +
        	LABEL_CLASSIFICATION_T + (classificationTwo == null ? 0 : classificationTwo));
        result.setSchoolingLevel(educationLevel == null ? "" : EducationLevel.of(educationLevel).getDescription());
        result.setEmployment(job == null ? "" : job);
        result.setMonthlyPurchases(monthlyPurchases == null ? BigDecimal.ZERO: monthlyPurchases);
    }


}
