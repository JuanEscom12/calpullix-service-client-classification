package com.calpullix.service.client.classification.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProductRowMapper implements RowMapper<ProductDTO> {

	private static final String NAME_COLUMN = "NAME";
	
	@Override
	public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductDTO result = new ProductDTO();
		result.setName(rs.getString(NAME_COLUMN));
		return result;
	}

}
