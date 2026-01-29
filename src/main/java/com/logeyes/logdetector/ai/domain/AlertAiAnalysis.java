package com.logeyes.logdetector.ai.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alert_ai_analysis")
public class AlertAiAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 어떤 Alert에 대한 분석인지 */
    @Column(name = "alert_id", nullable = false)
    private Long alertId;

    /** 사용한 AI 모델 */
    @Column(nullable = false, length = 50)
    private String model;

    /** 분석 상태 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AiAnalysisStatus status;

    /** 요약 결과 */
    @Lob
    private String summary;

    /** 원인 분석 */
    @Column(columnDefinition = "json")
    private String possibleCauses;

    /** 대응 체크리스트 */
    @Column(columnDefinition = "json")
    private String checklist;

    /** 실패 원인 (optional) */
    @Column(length = 300)
    private String errorMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /* =========================
       Factory methods
    ========================== */

    /** 분석 시작 시 */
    public static AlertAiAnalysis pending(
            Long alertId,
            String model
    ) {
        AlertAiAnalysis analysis = new AlertAiAnalysis();
        analysis.alertId = alertId;
        analysis.model = model;
        analysis.status = AiAnalysisStatus.PENDING;
        return analysis;
    }

    /** 분석 성공 */
    public void success(
            String summary,
            String possibleCauses,
            String checklist
    ) {
        this.summary = summary;
        this.possibleCauses = possibleCauses;
        this.checklist = checklist;
        this.status = AiAnalysisStatus.SUCCESS;
    }

    /** 분석 실패 (fallback) */
    public void fail(String errorMessage) {
        this.status = AiAnalysisStatus.FAILED;
        this.errorMessage = errorMessage;
        this.summary = "AI 분석에 실패했습니다. 로그 및 시스템 지표를 직접 확인하세요.";
    }
}
