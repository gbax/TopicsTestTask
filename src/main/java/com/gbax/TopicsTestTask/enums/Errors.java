package com.gbax.TopicsTestTask.enums;

public enum Errors {

    TOPIC_NOT_FOUND(0, "Топик не найден"),
    MESSAGE_NOT_FOUND(1, "Сообщение не найдено");

    private Integer id;
    private String message;

    Errors(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public static Errors getById(Integer id) {
        for (Errors errors : values()) {
            if (errors.getId().equals(id)) {
                return errors;
            }
        }
        return null;
    }
}
