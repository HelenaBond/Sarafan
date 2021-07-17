package com.example.demo.domain;

public final class Views {
    // создаем для каждого вида View свой интерфейс
    public interface Id {}
    //при выборе наследника в @JsonView() в контроллере отобразится и родитель.
    public interface IdName extends Id {}
    public interface IdDate extends Id {}
}
