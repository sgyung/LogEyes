package com.logeyes.logdetector.ai.repository;

import com.logeyes.logdetector.ai.domain.AlertAiAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertAiAnalysisRepository extends JpaRepository<AlertAiAnalysis, Long> {

    Optional<AlertAiAnalysis> findByAlertId(Long alertId);

}
