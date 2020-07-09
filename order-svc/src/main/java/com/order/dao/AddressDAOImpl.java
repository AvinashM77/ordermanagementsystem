/**
 * 
 */
package com.order.dao;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.order.model.Address;

/**
 * @author amake
 *
 */
@Component
public class AddressDAOImpl implements IAddressDAO {

	private static final Logger log = LogManager.getLogger(AddressDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String save(Address address) {
		Address existingAddress = null;
		try {
			existingAddress = jdbcTemplate.queryForObject(
					"select * from Address where STREET = ? and CITY = ? and STATE = ? and ZIP = ? and COUNTRY = ?",
					new Object[] { address.getStreet(), address.getCity(), address.getState(), address.getZip(),
							address.getCountry() },
					(rs, rowNum) -> new Address(rs.getString("ADDRESS_ID"), rs.getString("STREET"),
							rs.getString("CITY"), rs.getString("STATE"), rs.getString("ZIP"), rs.getString("COUNTRY")));
		} catch (EmptyResultDataAccessException e) {
			log.warn("No existingAddress found");
		}

		if (existingAddress == null) {
			address.setAddressId(UUID.randomUUID().toString());
			int update = jdbcTemplate.update(
					"insert into Address (ADDRESS_ID, STREET, CITY, STATE, ZIP, COUNTRY) values(?,?,?,?,?,?)",
					address.getAddressId(), address.getStreet(), address.getCity(), address.getState(),
					address.getZip(), address.getCountry());
			return update >= 1 ? address.getAddressId() : null;
		} else {
			return existingAddress.getAddressId();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Address get(String addressId) {
		try {
			return jdbcTemplate.queryForObject("select * from Address where ADDRESS_ID = ?", new Object[] { addressId },
					(rs, rowNum) -> new Address(rs.getString("ADDRESS_ID"), rs.getString("STREET"),
							rs.getString("CITY"), rs.getString("STATE"), rs.getString("ZIP"), rs.getString("COUNTRY")));
		} catch (EmptyResultDataAccessException e) {
			log.warn("No existingAddress found");
		}
		return null;

	}

}
