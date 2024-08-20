package com.example.main.domain.DTO;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class TrainingDTOAndPage {
    private List<TrainingDTO> trainingList;
    private boolean hasNext;

    public TrainingDTOAndPage(Page<TrainingDTO> page) {
        this.trainingList = page.getContent();
        this.hasNext = page.hasNext();
    }
}
