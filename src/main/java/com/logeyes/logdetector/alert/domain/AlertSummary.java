package com.logeyes.logdetector.alert.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alert_summary")
public class AlertSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alert_id", nullable = false)
    private Long alertId;

    @Column(nullable = false, length = 50)
    private String model;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "json", nullable = false)
    private String possibleCauses;

    @Column(columnDefinition = "json", nullable = false)
    private String checklist;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
