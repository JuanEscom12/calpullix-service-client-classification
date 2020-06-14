package com.calpullix.service.client.classification.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BranchRowMapper implements RowMapper<BranchDTO> {

	private static final String NAME_COLUMN = "NAME";
	
	@Override
	public BranchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BranchDTO result = new BranchDTO();
		result.setName(rs.getString(NAME_COLUMN));
		return result;
	}

}
