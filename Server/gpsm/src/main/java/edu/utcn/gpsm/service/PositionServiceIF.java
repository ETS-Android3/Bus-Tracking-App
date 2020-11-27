package edu.utcn.gpsm.service;

import edu.utcn.gpsm.config.ResourceNotFoundException;
import edu.utcn.gpsm.model.Position;
import edu.utcn.gpsm.model.User;
import edu.utcn.gpsm.model.dto.DateDTO;
import edu.utcn.gpsm.model.dto.PositionDTO;
import edu.utcn.gpsm.model.security.CustomUserDetails;
import io.swagger.models.auth.In;

import java.util.List;

public interface   PositionServiceIF {

    Position addPosition(final CustomUserDetails customUserDetails, final PositionDTO positionDTO) throws ResourceNotFoundException;

    Position findPositionById(final CustomUserDetails customUserDetails,final Integer id) throws ResourceNotFoundException;

    Position updatePositionById(final CustomUserDetails customUserDetails,final Integer id, final PositionDTO positionDTO) throws ResourceNotFoundException;

    void deleteLocationById(final CustomUserDetails customUserDetails,final Integer id) throws ResourceNotFoundException;

    List<Position> getAll() throws ResourceNotFoundException;

    List<Position> getTimedPosition(final DateDTO dateDTO) throws ResourceNotFoundException;


}
