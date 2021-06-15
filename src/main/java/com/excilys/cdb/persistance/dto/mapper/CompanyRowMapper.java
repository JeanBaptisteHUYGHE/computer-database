package com.excilys.cdb.persistance.dto.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistance.dto.CompanyPDto;
import com.excilys.cdb.persistance.dto.CompanyPDto.CompanyPDtoBuilder;

@Component
public class CompanyRowMapper implements RowMapper<Company> {
	
	private CompanyPDtoMapper companyPDtoMapper;
	
	public CompanyRowMapper(CompanyPDtoMapper companyPDtoMapper) {
		this.companyPDtoMapper = companyPDtoMapper;
	}

	@Override
	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		CompanyPDtoBuilder companyPDtoBuilder = new CompanyPDtoBuilder()
				.withId(resultSet.getString("id"))
				.withName(resultSet.getString("name"));
		CompanyPDto companyPDto = companyPDtoBuilder.build();

		return companyPDtoMapper.fromCompanyPDtoToCompany(companyPDto);
	}

}
