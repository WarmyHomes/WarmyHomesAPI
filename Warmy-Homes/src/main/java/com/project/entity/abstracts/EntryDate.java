package com.project.entity.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class EntryDate {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "US")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime create_at;

    @Nullable
    private LocalDateTime update_at;



    @PrePersist
    private void createAt() {
        create_at = LocalDateTime.now();
    }
    @PreUpdate
    private void updateAt() {
        update_at = LocalDateTime.now();
    }
}
