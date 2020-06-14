package com.calpullix.service.client.classification.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StateRowMapper implements RowMapper<StateDTO> {

	private static final String NAME_COLUMN = "state";
	
	@Override
	public StateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		StateDTO result = new StateDTO();
		result.setState(rs.getInt(NAME_COLUMN));
		return result;
	}


}
