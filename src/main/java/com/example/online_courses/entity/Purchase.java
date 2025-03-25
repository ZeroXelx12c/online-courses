package com.example.online_courses.entity;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "Purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "purchase_id")
    private UUID purchaseId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "course_id")
    private UUID courseId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "status")
    private String status = "pending";

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters và Setters
    public UUID getPurchaseId() { return purchaseId; }
    public void setPurchaseId(UUID purchaseId) { this.purchaseId = purchaseId; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}