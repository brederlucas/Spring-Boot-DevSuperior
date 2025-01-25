package com.devesuperior.dslist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devesuperior.dslist.dto.GameListDTO;
import com.devesuperior.dslist.entities.GameList;
import com.devesuperior.dslist.repositories.GameListRepository;
import com.devesuperior.dslist.repositories.GameRepository;
import com.devesuperior.projections.GameMinProjection;

import jakarta.transaction.Transactional;

@Service
public class GameListService {

    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public List<GameListDTO> findAll(){
        List<GameList> result = gameListRepository.findAll();
        return result.stream().map(x -> new GameListDTO(x)).toList();
    }

    @Transactional
    public void move(Long ListId, int sourceIndex, int destinationIndex){
        List<GameMinProjection> list = gameRepository.searchByList(ListId);
        GameMinProjection obj = list.remove(sourceIndex);
        list.add(destinationIndex, obj);

        int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;

        int max = sourceIndex < destinationIndex ? destinationIndex : destinationIndex; 

        for (int i=min ; i<=max; i++){
            gameListRepository.updateBelongingPosition(ListId, list.get(i).getId(), i);

        }

    }

}
