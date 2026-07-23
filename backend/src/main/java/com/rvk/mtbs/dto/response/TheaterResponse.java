package com.rvk.mtbs.dto.response;

import com.rvk.mtbs.enums.City;
import com.rvk.mtbs.enums.TheaterStatus;

public record TheaterResponse(
	    Long id,
	    String name,
	    Integer noOfRoom,
	    City city,
	    String address,
	    String googleMapUrl,
	    TheaterStatus status,
	    String ownerName
	) {}