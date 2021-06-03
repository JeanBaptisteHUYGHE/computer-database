package com.excilys.cdb.model;

public class Company {
	
	private Integer id;
	private String name;

	private Company() { }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return String.format("<Company n°%s: %s>", this.id, this.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else {
			if (!name.equals(other.name)) {
				return false;
			}
		}
		return true;
	}
	
	public static class CompanyBuilder {
		private Integer id;
		private String name;
		
		public CompanyBuilder withId(Integer id) {
			this.id = id;
			return this;
		}
		
		public CompanyBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Company build() {
			Company company = new Company();
			company.setName(name);
			company.setId(id);
			return company;
		}
	}

	
}
