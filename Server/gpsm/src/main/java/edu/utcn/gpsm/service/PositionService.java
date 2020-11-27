package edu.utcn.gpsm.service;

import edu.utcn.gpsm.config.ResourceNotFoundException;
import edu.utcn.gpsm.model.Position;
import edu.utcn.gpsm.model.User;
import edu.utcn.gpsm.model.dto.DateDTO;
import edu.utcn.gpsm.model.dto.PositionDTO;
import edu.utcn.gpsm.model.security.CustomUserDetails;
import edu.utcn.gpsm.repository.PositionRepository;
import edu.utcn.gpsm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class PositionService implements PositionServiceIF {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Position addPosition(CustomUserDetails customUserDetails, PositionDTO positionDTO) throws ResourceNotFoundException {

        if(Objects.isNull(positionDTO)){
            throw new ResourceNotFoundException("Body is empty");
        }

        if(Objects.isNull(positionDTO.getLatitude())){
            throw new ResourceNotFoundException("Latitude is null");
        }

        if(Objects.isNull(positionDTO.getLongitude())){
            throw new ResourceNotFoundException("Longitude is null");
        }

        Position position = new Position();

        position.setUser(customUserDetails.getUser());
        position.setLatitude(String.valueOf(positionDTO.getLatitude()));
        position.setLongitude(String.valueOf(positionDTO.getLongitude()));
        position.setCreationTime(new Date());
        position.setTerminalId(String.valueOf(positionDTO.getTerminalId()));

        positionRepository.save(position);

        return position;
    }

    @Override
    public Position findPositionById(CustomUserDetails customUserDetails,Integer id) throws ResourceNotFoundException {
        if(Objects.isNull(id)){
            throw new ResourceNotFoundException("Body is null");
        }

        Position position = positionRepository.findById(id).orElse(null);
        if(!position.getUser().getId().equals(customUserDetails.getUser().getId())) {
            throw new ResourceNotFoundException("Position not found !");
        }
        return position;
    }

    @Override
    public Position updatePositionById(CustomUserDetails customUserDetails,Integer id, PositionDTO positionDTO) throws ResourceNotFoundException {
       if(Objects.isNull(id)){
           throw new ResourceNotFoundException("ID can not be null");
       }
       Position position = positionRepository.findById(id).orElse(null);

       if(Objects.isNull(position)){
           throw new ResourceNotFoundException("Location not found");
       }

       position.setLatitude(String.valueOf(positionDTO.getLatitude()));
       position.setLongitude(String.valueOf(positionDTO.getLongitude()));
       position.setCreationTime(new Date());
       position.setTerminalId(String.valueOf(positionDTO.getTerminalId()));

       positionRepository.save(position);

       return position;
    }

    @Override
    public void deleteLocationById(CustomUserDetails customUserDetails,Integer id) throws ResourceNotFoundException {

        if(Objects.isNull(id)){
            throw new ResourceNotFoundException("ID can not be null");
        }

        Position position = positionRepository.findById(id).orElse(null);
        if(Objects.isNull(position)){
            throw new  ResourceNotFoundException("Postion not found");
        }

        positionRepository.deleteById(id);
        return;

    }

    @Override
    public List<Position> getAll() throws ResourceNotFoundException {
        return positionRepository.findAll();
    }

    @Override
    public List<Position> getTimedPosition(DateDTO dateDTO) throws ResourceNotFoundException {
        if(Objects.isNull(dateDTO.getStartDate()) || Objects.isNull(dateDTO.getEndDate()) || Objects.isNull(dateDTO.getTerminalId())){
            throw new ResourceNotFoundException("Dates or Terminal Id can not be null");
        }

        return positionRepository.findPositionByTerminalIdAndCreationTimeBetween(dateDTO.getTerminalId(), dateDTO.getStartDate(),dateDTO.getEndDate());
    }
}
