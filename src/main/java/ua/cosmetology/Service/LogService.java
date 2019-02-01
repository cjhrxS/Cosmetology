package ua.cosmetology.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import ua.cosmetology.DTO.LogDTO;
import ua.cosmetology.Entity.LogEntity;

public interface LogService {
	
	void createLog(LogDTO logDTO);
	
	List<LogDTO> findAll();
	
	LogDTO findById(Long Id);
	
	void updateLogDTO(Long id, LogDTO logDTO);
	
	List<LogDTO> findAllByPage(Pageable pageable);

	
	Optional<LogEntity> findByEmail(String email);
	

}
