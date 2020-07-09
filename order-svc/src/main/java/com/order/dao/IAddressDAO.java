/**
 * 
 */
package com.order.dao;

import com.order.model.Address;

/**
 * @author amake
 *
 */
public interface IAddressDAO {

	/**
	 * store the address.
	 * 
	 * @param address
	 * @return
	 */
	String save(Address address);

	/**
	 * get address by Id.
	 * 
	 * @param addressId
	 * @return
	 */
	Address get(String addressId);

}
