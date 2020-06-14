package com.calpullix.service.client.classification.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MunicipalityRowMapper implements RowMapper<MunicipalityDTO> {

	private static final String NAME_COLUMN = "municipality";
	
	@Override
	public MunicipalityDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MunicipalityDTO result = new MunicipalityDTO();
		result.setName(rs.getString(NAME_COLUMN));
		return result;
	}

}
