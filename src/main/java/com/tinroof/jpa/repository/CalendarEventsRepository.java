package com.tinroof.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tinroof.jpa.model.CalendarEvents;

/**
 * Created by Ranga Pasumarti on 06/05/18.
 */
@Repository
public interface CalendarEventsRepository extends JpaRepository<CalendarEvents, Long> {
    List<CalendarEvents> findByCalendarId(Long calendarId);
}
