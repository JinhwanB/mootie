package com.jinhwanb.mootie.member.model;

import com.jinhwanb.mootie.config.BaseTImeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"memberId", "email"}
        )
    }
)
public class Member extends BaseTImeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId; // 회원 아이디

    @Column(nullable = false)
    private String password; // 회원 비밀번호

    @Column(nullable = false)
    private String email; // 회원 이메일

    @Column
    private LocalDateTime deletedAt; // 삭제 날짜
}
