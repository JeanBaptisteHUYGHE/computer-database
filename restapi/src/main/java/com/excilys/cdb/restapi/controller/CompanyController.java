package com.excilys.cdb.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.persistence.exception.CompanyNotFoundException;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.restapi.dto.CompanyRDto;
import com.excilys.cdb.restapi.dto.mapper.CompanyRDtoMapper;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	private Logger logger;
	private CompanyService companyService;
	private CompanyRDtoMapper companyRDtoMapper;
	
	public CompanyController(CompanyService companyService, CompanyRDtoMapper companyRDtoMapper) {
		logger = LoggerFactory.getLogger(CompanyController.class);
		this.companyService = companyService;
		this.companyRDtoMapper = companyRDtoMapper;
	}

	@GetMapping
    public List<CompanyRDto> getAll() {
		logger.debug("getAll()");
		
		try {
			List<Company> companiesList = companyService.getCompaniesList();
			return companyRDtoMapper.fromCompaniesListToCompaniesRDtoList(companiesList);
			
		} catch (DatabaseConnectionException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
			
		}
    }
	
	@GetMapping(value = "/{id}")
    public CompanyRDto getCompanyById(@PathVariable("id") Integer id) {
		logger.debug("getCompanyById({})", id);
		
		try {
			Company company = companyService.getCompanyById(id);
			return companyRDtoMapper.fromCompanyToCompanyRDto(company);
			
		} catch (DatabaseConnectionException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
			
		} catch (CompanyNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", e);
		}
        
    }
	
	@GetMapping(value = "/count")
    public Integer getCompanyCount() {
		logger.debug("getCompanyCount()");
		
		try {
			Integer companiesCount = companyService.getCompaniesCount();
			return companiesCount;
			
		} catch (DatabaseConnectionException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
		}
    }
	
	@DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompanyById(@PathVariable("id") Integer id) {
		try {
			companyService.deleteCompanyById(id);
		
		} catch (DatabaseConnectionException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
		}
    }
}