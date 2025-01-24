package com.devesuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.devesuperior.dslist.dto.GameDTO;
import com.devesuperior.dslist.dto.GameMinDTO;
import com.devesuperior.dslist.entities.Game;
import com.devesuperior.dslist.repositories.GameRepository;
import com.devesuperior.projections.GameMinProjection;

import jakarta.transaction.Transactional;


@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    //Métodos

	@Transactional
	public GameDTO findById(@PathVariable Long listId) {
		Game result = gameRepository.findById(listId).get();
		return new GameDTO(result);
	}

    // Função que retorna uma lista de GameMinDTO
	@Transactional
	public List<GameMinDTO> findAll() {
		List<Game> result = gameRepository.findAll();
		return result.stream().map(GameMinDTO::new).toList();
	}

	@Transactional
	public List<GameMinDTO> findByList(Long listId){
		List<GameMinProjection> result = gameRepository.searchByList(listId);
		return result.stream().map(x -> new GameMinDTO(x)).toList();
	}

}
