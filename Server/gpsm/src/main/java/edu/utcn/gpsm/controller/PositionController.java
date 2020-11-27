package edu.utcn.gpsm.controller;

import edu.utcn.gpsm.config.ResourceNotFoundException;
import edu.utcn.gpsm.model.Position;
import edu.utcn.gpsm.model.dto.DateDTO;
import edu.utcn.gpsm.model.dto.PositionDTO;
import edu.utcn.gpsm.model.security.CustomUserDetails;
import edu.utcn.gpsm.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/position")
@CrossOrigin(origins = "http://localhost:5500")
public class PositionController {

    @Autowired
    private PositionService positionService;



    @GetMapping("/getAll")
    public ResponseEntity<List<Position>> getAll() throws ResourceNotFoundException{

        return ResponseEntity.ok(positionService.getAll());
    }
    @PostMapping("/addPos")
    public ResponseEntity<Position> addPosition( @RequestBody final PositionDTO positionDTO) throws ResourceNotFoundException{
        final CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(positionService.addPosition(customUserDetails,positionDTO));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Position> getPosition(@PathVariable final Integer id) throws ResourceNotFoundException{
        final CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(positionService.findPositionById(customUserDetails,id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable final Integer id, @RequestBody PositionDTO positionDTO) throws ResourceNotFoundException{
        final CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(positionService.updatePositionById(customUserDetails,id,positionDTO));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deletePosition(@PathVariable final Integer id) throws ResourceNotFoundException{
        final CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        positionService.deleteLocationById(customUserDetails,id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/getAllByDate")
    public ResponseEntity<List<Position>> getAllByDate( @RequestBody DateDTO dateDTO) throws ResourceNotFoundException{
        return ResponseEntity.ok(positionService.getTimedPosition(dateDTO));
    }



}
