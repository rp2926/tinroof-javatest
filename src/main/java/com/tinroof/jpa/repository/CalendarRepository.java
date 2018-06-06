package com.tinroof.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tinroof.jpa.model.Calendar;

/**
 * Created by Ranga Pasumarti on 06/05/18.
 */
@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
