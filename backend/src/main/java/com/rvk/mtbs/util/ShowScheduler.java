package com.rvk.mtbs.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rvk.mtbs.entity.Show;
import com.rvk.mtbs.enums.ShowStatus;
import com.rvk.mtbs.repository.ShowRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ShowScheduler {

    private final ShowRepository showRepository;

    @Scheduled(fixedRate = 60000) // Every 60 seconds
    @Transactional
    public void updateFinishedShows() {

        List<Show> shows = showRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        for (Show show : shows) {

            LocalDateTime endTime =
                    show.getStartTime().plusMinutes(show.getDurationMinutes());

            if (show.getStatus() != ShowStatus.DONE &&
                now.isAfter(endTime)) {

                show.setStatus(ShowStatus.DONE);
            }
        }
    }
}
