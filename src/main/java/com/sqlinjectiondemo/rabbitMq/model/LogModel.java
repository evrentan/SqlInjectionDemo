package com.sqlinjectiondemo.rabbitMq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogModel implements Serializable {

    private String query;

    @Builder.Default
    private LocalDate createDate = LocalDate.now();
}
