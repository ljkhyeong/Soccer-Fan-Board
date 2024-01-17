package jkproject.soccer.common.data.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class BaseTimeEntity {

	@CreatedDate
	@Column(updatable = false, name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "modified_at")
	private LocalDateTime modifiedAt;
}
