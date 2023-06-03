package binarbej.challenge.chapter7.controller;

import binarbej.challenge.chapter7.model.Schedule;
import binarbej.challenge.chapter7.repository.ScheduleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class SchedulerFilm {
    private final ScheduleRepository scheduleRepository;

    public SchedulerFilm(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Menjadwalkan eksekusi setiap hari pukul 00:00:00
    public void updateFilmStatus() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        List<Schedule> schedulesToUpdate = scheduleRepository.getSchedule(Date.valueOf(nowDate), Time.valueOf(nowTime), "Y");

        for (Schedule schedule : schedulesToUpdate) {
            schedule.setIsactive("N");
        }

        scheduleRepository.saveAll(schedulesToUpdate);

        System.out.println("The schedule was finished");
    }

}