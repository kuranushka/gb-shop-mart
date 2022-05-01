package ru.gb.gbapi.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    CREATED("Создан"),
    PROCESSING("В обработке"),
    COMPLETED("Выполнен"),
    CANCELED("Отменен");

    @Getter
    private final String title;
    }
