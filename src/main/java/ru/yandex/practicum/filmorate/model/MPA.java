package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MPA {

    int id;
    String name;


    public MPA(int id) {
        this.id = id;
    }

//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        MPA mpa = (MPA) o;
//
//        return Objects.equals(mpa.getId(),this.getId())
//                && Objects.equals(mpa.getName(), this.getName());
//    }

//    @Override
//    public String toString() {
//        return ("id: " + this.id);
//    }
}
