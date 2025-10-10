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

    private static final String SELECT_ADDRESS_BY_DETAILS =
        "SELECT * FROM Address WHERE STREET = ? AND CITY = ? AND STATE = ? AND ZIP = ? AND COUNTRY = ?";
    private static final String INSERT_ADDRESS =
        "INSERT INTO Address (ADDRESS_ID, STREET, CITY, STATE, ZIP, COUNTRY) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ADDRESS_BY_ID =
        "SELECT * FROM Address WHERE ADDRESS_ID = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
	 * {@inheritDoc}
	 */
	@Override
	public String save(Address address) {
        Address existingAddress = findExistingAddress(address);
        if (existingAddress != null) {
            return existingAddress.getAddressId();
        }

        address.setAddressId(UUID.randomUUID().toString());
        int update = jdbcTemplate.update(
            INSERT_ADDRESS,
            ps -> {
                ps.setString(1, address.getAddressId());
                ps.setString(2, address.getStreet());
                ps.setString(3, address.getCity());
                ps.setString(4, address.getState());
                ps.setString(5, address.getZip());
                ps.setString(6, address.getCountry());
            });
        return update >= 1 ? address.getAddressId() : null;
    }

    private Address findExistingAddress(Address address) {
        try {
            return jdbcTemplate.query(
                SELECT_ADDRESS_BY_DETAILS,
                ps -> {
                    ps.setString(1, address.getStreet());
                    ps.setString(2, address.getCity());
                    ps.setString(3, address.getState());
                    ps.setString(4, address.getZip());
                    ps.setString(5, address.getCountry());
                },
                (rs, rowNum) -> new Address(
                    rs.getString("ADDRESS_ID"),
                    rs.getString("STREET"),
                    rs.getString("CITY"),
                    rs.getString("STATE"),
                    rs.getString("ZIP"),
                    rs.getString("COUNTRY")
                ))
                .stream()
                .findFirst()
                .orElse(null);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No existing address found");
            return null;
        }
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Address get(String addressId) {
		try {
			return jdbcTemplate.query(
                SELECT_ADDRESS_BY_ID,
                ps -> ps.setString(1, addressId),
                (rs, rowNum) -> new Address(
                    rs.getString("ADDRESS_ID"),
                    rs.getString("STREET"),
                    rs.getString("CITY"),
                    rs.getString("STATE"),
                    rs.getString("ZIP"),
                    rs.getString("COUNTRY")
                ))
                .stream()
                .findFirst()
                .orElse(null);
		} catch (EmptyResultDataAccessException e) {
			log.warn("No existing address found");
			return null;
		}
	}

}
